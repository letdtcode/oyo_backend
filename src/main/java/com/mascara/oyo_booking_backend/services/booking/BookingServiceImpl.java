package com.mascara.oyo_booking_backend.services.booking;

import com.mascara.oyo_booking_backend.dtos.BaseMessageData;
import com.mascara.oyo_booking_backend.dtos.request.booking.BookingRequest;
import com.mascara.oyo_booking_backend.dtos.request.booking.CheckBookingRequest;
import com.mascara.oyo_booking_backend.dtos.response.booking.CheckBookingResponse;
import com.mascara.oyo_booking_backend.dtos.response.booking.GetBookingResponse;
import com.mascara.oyo_booking_backend.dtos.response.paging.BasePagingData;
import com.mascara.oyo_booking_backend.entities.*;
import com.mascara.oyo_booking_backend.enums.BookingStatusEnum;
import com.mascara.oyo_booking_backend.enums.PaymentMethodEnum;
import com.mascara.oyo_booking_backend.enums.PaymentPolicyEnum;
import com.mascara.oyo_booking_backend.exceptions.ResourceNotFoundException;
import com.mascara.oyo_booking_backend.mapper.BookingMapper;
import com.mascara.oyo_booking_backend.repositories.*;
import com.mascara.oyo_booking_backend.utils.AppContants;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 22/10/2023
 * Time      : 7:44 CH
 * Filename  : BookingServiceImpl
 */
@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private AccomPlaceRepository accomPlaceRepository;

    @Autowired
    private BookingListRepository bookingListRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ProvinceRepository provinceRepository;

    @Autowired
    private RevenueListRepository revenueListRepository;

    @Autowired
    private RevenueRepository revenueRepository;

    @Autowired
    private BookingMapper bookingMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SurchargeOfAccomRepository surchargeOfAccomRepository;

    @Override
    @Transactional
    public BaseMessageData createOrderBookingAccom(BookingRequest request, String mailUser) {
        AccomPlace accomPlace = accomPlaceRepository.findById(request.getAccomId())
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("accom place")));
        RevenueList revenueListUser = revenueListRepository.findByUserId(accomPlace.getUserId()).get();
        boolean isAvailable = checkAvailableAccom(accomPlace.getId(), request.getCheckIn(), request.getCheckOut());

        if (!isAvailable) {
            return new BaseMessageData(AppContants.BOOKING_NOT_AVAILABLE_TIME(
                    request.getAccomId(),
                    request.getCheckIn().toString(),
                    request.getCheckOut().toString()));
        }
        int maxPeople = accomPlace.getNumPeople();
        if (request.getNumAdult() > maxPeople) {
            return new BaseMessageData(AppContants.BOOKING_NOT_AVAILABLE_PEOPLE);
        }

        accomPlace.setNumBooking(accomPlace.getNumBooking() + 1L);
        Province province = provinceRepository.findByProvinceCode(accomPlace.getProvinceCode())
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("province")));
        province.setNumBooking(province.getNumBooking() + 1L);
        accomPlaceRepository.save(accomPlace);
        provinceRepository.save(province);

        BookingList bookingList = bookingListRepository.findByUserMail(mailUser)
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("user")));
        String bookingCode = UUID.randomUUID().toString();
        Double totalBill = request.getOriginPay() + request.getSurcharge();
        Booking booking = mapper.map(request, Booking.class);
        booking.setAccomPlace(accomPlace);
        booking.setAccomId(accomPlace.getId());
        booking.setTotalBill(totalBill);
        booking.setBookingList(bookingList);
        booking.setBookListId(bookingList.getId());
        booking.setBookingCode(bookingCode);
        booking.setBookingStatusEnum(BookingStatusEnum.WAITING);
        booking.setPaymentPolicy(PaymentPolicyEnum.valueOf(request.getPaymentPolicy()));
        booking.setPaymentMethod(PaymentMethodEnum.valueOf(request.getPaymentMethod()));
        booking = bookingRepository.save(booking);

        Double commisionMoney = (revenueListUser.getDiscount() * totalBill) / 100;
        Double totalRevenue = totalBill - commisionMoney;
        Revenue revenue = Revenue.builder()
                .booking(booking)
                .bookingCode(bookingCode)
                .commPay(commisionMoney)
                .totalRevenue(totalRevenue)
                .totalBill(totalBill)
                .revenueList(revenueListUser)
                .revenueListId(revenueListUser.getId())
                .build();
        revenueRepository.save(revenue);
        return new BaseMessageData(AppContants.BOOKING_SUCESSFUL);
    }

    public boolean checkAvailableAccom(Long accomId, LocalDate checkIn, LocalDate checkOut) {
        boolean isAvailable = bookingRepository.checkBookingAvailable(accomId, checkIn, checkOut);
        return isAvailable;
    }

    @Override
    @Transactional
    public CheckBookingResponse checkBookingToGetPrice(CheckBookingRequest request) {
        AccomPlace accomPlace = accomPlaceRepository.findById(request.getAccomId())
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("Accom place")));
        Double costSurcharge = 0D;
        List<SurchargeOfAccom> surchargeOfAccomList = surchargeOfAccomRepository.findByAccomPlaceId(request.getAccomId());
        if (surchargeOfAccomList != null && !surchargeOfAccomList.isEmpty()) {
            for (SurchargeOfAccom surcharge : surchargeOfAccomList) {
                costSurcharge = costSurcharge + surcharge.getCost();
            }
        }
        Period p = Period.between(request.getCheckIn(), request.getCheckOut());
        int numNight = p.getDays();
        Double totalCostAccom = accomPlace.getPricePerNight() * numNight;
        Double totalBill = totalCostAccom + costSurcharge;

        int maxPeople = accomPlace.getNumPeople();
        boolean isCanBooking = true;
        String message = "Booking is ready";
        boolean accomAvailable = checkAvailableAccom(request.getAccomId(), request.getCheckIn(), request.getCheckOut());
        if (!accomAvailable) {
            isCanBooking = false;
            message = "Booking is not available in range time";
            if (request.getNumAdult() > maxPeople) {
                message = message + "\nNum people is out max num people allow";
            }
        }
        if (isCanBooking && request.getNumAdult() > maxPeople) {
            isCanBooking = false;
            message = "Num people is out max num people allow";
        }
        return new CheckBookingResponse(isCanBooking, totalCostAccom, costSurcharge, totalBill, message);
    }

    @Override
    @Transactional
    public BasePagingData<GetBookingResponse> getBookingOfPartnerByStatus(String hostMail,
                                                                          String status,
                                                                          Integer pageNum,
                                                                          Integer pageSize,
                                                                          String sortType,
                                                                          String field) {
        User user = userRepository.findByMail(hostMail).orElseThrow(() -> new ResourceNotFoundException("user"));
        Pageable paging = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.fromString(sortType), field));
        Page<Booking> bookingPage = bookingRepository.getBookingOfPartnerByStatus(user.getId(), status, paging);
        List<Booking> bookingList = bookingPage.stream().toList();
        List<GetBookingResponse> responseList = bookingList.stream().map(booking -> bookingMapper.toGetBookingResponse(booking))
                .collect(Collectors.toList());
        return new BasePagingData<>(responseList,
                bookingPage.getNumber(),
                bookingPage.getSize(),
                bookingPage.getTotalElements());
    }
}
