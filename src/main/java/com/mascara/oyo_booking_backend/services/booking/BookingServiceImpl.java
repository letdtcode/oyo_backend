package com.mascara.oyo_booking_backend.services.booking;

import com.mascara.oyo_booking_backend.dtos.BaseMessageData;
import com.mascara.oyo_booking_backend.dtos.request.booking.BookingRequest;
import com.mascara.oyo_booking_backend.dtos.request.booking.CheckBookingRequest;
import com.mascara.oyo_booking_backend.entities.AccomPlace;
import com.mascara.oyo_booking_backend.entities.Booking;
import com.mascara.oyo_booking_backend.entities.BookingList;
import com.mascara.oyo_booking_backend.entities.Province;
import com.mascara.oyo_booking_backend.enums.BookingStatusEnum;
import com.mascara.oyo_booking_backend.enums.PaymentMethodEnum;
import com.mascara.oyo_booking_backend.enums.PaymentPolicyEnum;
import com.mascara.oyo_booking_backend.exceptions.ResourceNotFoundException;
import com.mascara.oyo_booking_backend.repositories.AccomPlaceRepository;
import com.mascara.oyo_booking_backend.repositories.BookingListRepository;
import com.mascara.oyo_booking_backend.repositories.BookingRepository;
import com.mascara.oyo_booking_backend.repositories.ProvinceRepository;
import com.mascara.oyo_booking_backend.utils.AppContants;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

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

    @Override
    @Transactional
    public BaseMessageData createOrderBookingAccom(BookingRequest request, String mailUser) {
        AccomPlace accomPlace = accomPlaceRepository.findById(request.getAccomId())
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("accom place")));
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
        BigDecimal totalBill = request.getOriginPay().add(request.getSurcharge());
        Booking booking = mapper.map(request, Booking.class);
        booking.setAccomPlace(accomPlace);
        booking.setAccomId(accomPlace.getId());
        booking.setTotalBill(totalBill);
        booking.setBookingList(bookingList);
        booking.setBookListId(bookingList.getId());
        booking.setBookingCode(bookingCode);
        booking.setBookingStatusEnum(BookingStatusEnum.AWAIT);
        booking.setPaymentPolicy(PaymentPolicyEnum.valueOf(request.getPaymentPolicy()));
        booking.setPaymentMethod(PaymentMethodEnum.valueOf(request.getPaymentMethod()));
        bookingRepository.save(booking);
        return new BaseMessageData(AppContants.BOOKING_SUCESSFUL);
    }

    public boolean checkAvailableAccom(Long accomId, LocalDate checkIn, LocalDate checkOut) {
        boolean isAvailable = bookingRepository.checkBookingAvailable(accomId, checkIn, checkOut);
        return isAvailable;
    }

    @Override
    @Transactional
    public boolean checkBookingReady(CheckBookingRequest request) {
        AccomPlace accomPlace = accomPlaceRepository.findById(request.getAccomId())
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("Accom place")));
        int maxPeople = accomPlace.getNumPeople();
        boolean accomAvailable = checkAvailableAccom(request.getAccomId(), request.getCheckIn(), request.getCheckOut());
        if (!accomAvailable || request.getNumAdult() > maxPeople) {
            return false;
        }
        return true;
    }
}
