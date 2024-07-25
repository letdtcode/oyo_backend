package com.mascara.oyo_booking_backend.services.booking;

import com.mascara.oyo_booking_backend.constant.BookingConstant;
import com.mascara.oyo_booking_backend.constant.FeeRateOfAdminConstant;
import com.mascara.oyo_booking_backend.dtos.accom_place.response.PriceCustomForAccom;
import com.mascara.oyo_booking_backend.dtos.base.BaseMessageData;
import com.mascara.oyo_booking_backend.dtos.base.BasePagingData;
import com.mascara.oyo_booking_backend.dtos.booking.request.BookingRequest;
import com.mascara.oyo_booking_backend.dtos.booking.request.CancelBookingRequest;
import com.mascara.oyo_booking_backend.dtos.booking.request.CheckBookingRequest;
import com.mascara.oyo_booking_backend.dtos.booking.response.CheckBookingResponse;
import com.mascara.oyo_booking_backend.dtos.booking.response.GetBookingResponse;
import com.mascara.oyo_booking_backend.dtos.booking.response.GetHistoryBookingResponse;
import com.mascara.oyo_booking_backend.entities.accommodation.AccomPlace;
import com.mascara.oyo_booking_backend.entities.accommodation.PriceCustom;
import com.mascara.oyo_booking_backend.entities.accommodation.SurchargeOfAccom;
import com.mascara.oyo_booking_backend.entities.address.Province;
import com.mascara.oyo_booking_backend.entities.authentication.User;
import com.mascara.oyo_booking_backend.entities.booking.Booking;
import com.mascara.oyo_booking_backend.entities.booking.BookingList;
import com.mascara.oyo_booking_backend.entities.notification.Notification;
import com.mascara.oyo_booking_backend.entities.order.AdminEarning;
import com.mascara.oyo_booking_backend.entities.order.PartnerEarning;
import com.mascara.oyo_booking_backend.entities.order.Payment;
import com.mascara.oyo_booking_backend.enums.booking.BookingStatusEnum;
import com.mascara.oyo_booking_backend.enums.homestay.CancellationPolicyEnum;
import com.mascara.oyo_booking_backend.enums.order.PaymentMethodEnum;
import com.mascara.oyo_booking_backend.enums.order.PaymentPolicyEnum;
import com.mascara.oyo_booking_backend.exceptions.ResourceNotFoundException;
import com.mascara.oyo_booking_backend.external_modules.mail.EmailDetails;
import com.mascara.oyo_booking_backend.external_modules.mail.service.EmailService;
import com.mascara.oyo_booking_backend.mapper.booking.BookingMapper;
import com.mascara.oyo_booking_backend.repositories.*;
import com.mascara.oyo_booking_backend.utils.AppContants;
import com.mascara.oyo_booking_backend.utils.Utilities;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 22/10/2023
 * Time      : 7:44 CH
 * Filename  : BookingServiceImpl
 */
@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final AccomPlaceRepository accomPlaceRepository;


    private final BookingListRepository bookingListRepository;


    private final BookingRepository bookingRepository;


    private final PaymentRepository paymentRepository;


    private final PartnerEarningRepository partnerEarningRepository;


    private final AdminEarningRepository adminEarningRepository;


    private final ProvinceRepository provinceRepository;
    private final BookingMapper bookingMapper;

    private final UserRepository userRepository;

    private final NotificationRepository notificationRepository;


    private final SurchargeOfAccomRepository surchargeOfAccomRepository;

    private final SimpMessagingTemplate messagingTemplate;

    private final EmailService emailService;

    private final PriceCustomRepository priceCustomRepository;

    @Override
    @Transactional
    public BaseMessageData createOrderBookingAccom(BookingRequest request, String mailUser) {
        AccomPlace accomPlace = accomPlaceRepository.findById(request.getAccomId())
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("accom place")));
        Long hostId = accomPlace.getUserId();
        User host = userRepository.findByUserId(hostId).get();
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

        List<PriceCustom> priceCustoms = priceCustomRepository.findByAccomId(accomPlace.getId());


        Period p = Period.between(request.getCheckIn(), request.getCheckOut());
        int numNight = p.getDays() + 1;

        Double costSurcharge = 0D;
        List<SurchargeOfAccom> surchargeOfAccomList = surchargeOfAccomRepository.findByAccomPlaceId(request.getAccomId());
        if (surchargeOfAccomList != null && !surchargeOfAccomList.isEmpty()) {
            for (SurchargeOfAccom surcharge : surchargeOfAccomList) {
                costSurcharge = costSurcharge + surcharge.getCost();
            }
        }

        Double originPay = 0D;
        for (PriceCustom priceCustom : priceCustoms) {
            boolean priceCustomApply = Utilities.getInstance()
                    .isWithinRange(priceCustom.getDateApply(), request.getCheckIn(), request.getCheckOut());
            if (priceCustomApply) {
                numNight--;
                originPay += priceCustom.getPriceApply();
            }
        }

        Double pricePerNightAfterPromotion = accomPlace.getPricePerNight() - (accomPlace.getPricePerNight() * accomPlace.getDiscount());
        originPay = originPay + pricePerNightAfterPromotion * numNight;
        Double totalBill = originPay + costSurcharge;
        Double totalTrasfer = 0D;

        switch (PaymentMethodEnum.valueOf(request.getPaymentMethod())) {
            case PAYPAL -> totalBill = (totalBill * PaymentMethodEnum.PAYPAL.getPercent()) / 100;
            default -> totalBill = (totalBill * PaymentMethodEnum.DIRECT.getPercent()) / 100;
        }
        switch (PaymentPolicyEnum.valueOf(request.getPaymentPolicy())) {
            case PAYMENT_HALF -> totalTrasfer = (totalBill * 50) / 100;
            default -> totalTrasfer = totalBill;
        }

        Booking booking = bookingMapper.toEntity(request);
        booking.setAccomPlace(accomPlace);
        booking.setAccomId(accomPlace.getId());
        booking.setBookingList(bookingList);
        booking.setBookListId(bookingList.getId());
        booking.setBookingCode(bookingCode);
        booking.setStatus(BookingStatusEnum.WAITING);
        booking = bookingRepository.save(booking);

        Payment payment = Payment.builder()
                .originPay(originPay)
                .totalBill(totalBill)
                .totalTransfer(totalTrasfer)
                .surchargePay(costSurcharge)
                .paymentPolicy(PaymentPolicyEnum.valueOf(request.getPaymentPolicy()))
                .paymentMethod(PaymentMethodEnum.valueOf(request.getPaymentMethod()))
                .booking(booking)
                .build();
        paymentRepository.save(payment);

        Double moneyForAdmin = (FeeRateOfAdminConstant.FEE_RATE_OF_ADMIN * totalBill) / 100;
        Double moneyForPartner = totalBill - moneyForAdmin;

        PartnerEarning partnerEarning = PartnerEarning.builder()
//                .partner(host)
                .partnerId(hostId)
                .earningAmount(moneyForPartner)
                .payment(payment)
                .build();

        partnerEarningRepository.save(partnerEarning);

        AdminEarning adminEarning = AdminEarning.builder()
                .payment(payment)
                .earningAmount(moneyForAdmin)
                .build();
        adminEarningRepository.save(adminEarning);

        //        Send mail booking success
        String fullHostName = host.getFirstName() + " " + host.getLastName();
        Map<String, Object> model = new HashMap<>();
        model.put("billId", bookingCode);
        model.put("homeName", accomPlace.getAccomName());
        model.put("ownerName", fullHostName);
        model.put("dateStart", request.getCheckIn());
        model.put("dateEnd", request.getCheckOut());
        model.put("baseCost", originPay);
        model.put("surchargeCost", costSurcharge);
        model.put("totalCost", totalBill);
        model.put("moneyPay", totalTrasfer);
        model.put("createdDate", booking.getCreatedDate());
        model.put("fullNameCustomer", booking.getNameCustomer());
        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(mailUser)
                .subject("Đặt phòng thành công").build();
        emailService.sendMailWithTemplate(emailDetails, "Email_Booking_Success.ftl", model);

//        Send queue message socket
        StringBuilder content = new StringBuilder("Khách hàng ");
        content.append(booking.getNameCustomer());
        content.append(" vừa mới đặt chỗ ở " + accomPlace.getAccomName() + " của bạn");
        Notification notification = Notification.builder()
                .senderMail(mailUser)
                .recipient(host)
                .recipientMail(host.getMail())
                .title("Bạn có đơn đặt phòng mới")
                .content(content.toString())
                .imageUrl(null)
                .build();

        notification = notificationRepository.save(notification);

        int numNotiUnviewOfUser = notificationRepository.countByRecipientMailAndViewAndDeleted(host.getMail(), false, false);
        messagingTemplate.convertAndSendToUser(
                host.getMail(), "/queue/messages",
                numNotiUnviewOfUser
        );
        return new BaseMessageData(BookingConstant.BOOKING_SUCESS);
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

//        Period p = Period.between(request.getCheckIn(), request.getCheckOut());
//        int numNight = p.getDays() + 1;
//        Double totalCostAccom = accomPlace.getPricePerNight() * numNight;
//        Double totalBill = totalCostAccom + costSurcharge;
        List<PriceCustomForAccom> priceCustomForAccoms = new LinkedList<>();
        List<PriceCustom> priceCustoms = priceCustomRepository.findByAccomId(accomPlace.getId());

        for (PriceCustom priceCustom : priceCustoms) {
            boolean priceCustomApply = Utilities.getInstance()
                    .isWithinRange(priceCustom.getDateApply(),
                            request.getCheckIn(),
                            request.getCheckOut());
            if (priceCustomApply) {
//                numNight--;
                priceCustomForAccoms.add(
                        PriceCustomForAccom.builder().priceApply(priceCustom.getPriceApply())
                                .dateApply(priceCustom.getDateApply())
                                .build()
                );
            }
        }

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
//        return new CheckBookingResponse(isCanBooking, totalCostAccom, costSurcharge, totalBill, message);
        return new CheckBookingResponse(isCanBooking, priceCustomForAccoms, message);
    }

    @Override
    @Transactional
    public BasePagingData<GetBookingResponse> getListBookingOfPartner(String hostMail,
                                                                      String status,
                                                                      Integer pageNum,
                                                                      Integer pageSize,
                                                                      String sortType,
                                                                      String field) {
        User user = userRepository.findByMail(hostMail).orElseThrow(() -> new ResourceNotFoundException("user"));
        Pageable paging = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.fromString(sortType), field));

        LocalDate dateNow = LocalDate.now();
        Page<Booking> bookingPage = bookingRepository.getListBookingOfPartner(user.getId(), status, dateNow, paging);
        List<Booking> bookingList = bookingPage.stream().toList();
        List<GetBookingResponse> responseList = bookingList.stream().map(booking -> bookingMapper.toGetBookingResponse(booking))
                .collect(Collectors.toList());
        return new BasePagingData<>(responseList,
                bookingPage.getNumber(),
                bookingPage.getSize(),
                bookingPage.getTotalElements());
    }

    @Override
    @Transactional
    public BasePagingData<GetHistoryBookingResponse> getHistoryBookingUser(String userMail,
                                                                           Integer pageNum,
                                                                           Integer pageSize,
                                                                           String sortType,
                                                                           String field) {
        User user = userRepository.findByMail(userMail).get();
        Pageable paging = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.fromString(sortType), field));
        Page<Booking> bookingPage = bookingRepository.getHistoryBookingUser(user.getId(), paging);
        List<Booking> bookingList = bookingPage.stream().toList();
        List<GetHistoryBookingResponse> responseList = bookingList.stream().map(booking -> bookingMapper.toGetHistoryBookingResponse(booking))
                .collect(Collectors.toList());
        return new BasePagingData<>(responseList,
                bookingPage.getNumber(),
                bookingPage.getSize(),
                bookingPage.getTotalElements());
    }

    @Override
    @Transactional
    public BaseMessageData changeStatusBookingByHost(String hostMail, String bookingCode, String status) {
        User host = userRepository.findHostOfAccomByBookingCode(bookingCode).get();
        if (!host.getMail().equals(hostMail)) {
            return new BaseMessageData(AppContants.NOT_PERMIT);
        }
        bookingRepository.changeStatusBooking(bookingCode, status);
        return new BaseMessageData(AppContants.CHANGE_STATUS_BOOKING_SUCCESS);
    }

    @Override
    @Transactional
    public BaseMessageData cancelBooking(String userMail, CancelBookingRequest request) {
        User user = userRepository.findUserByBookingCode(request.getBookingCode()).get();
        if (!user.getMail().equals(userMail)) {
            return new BaseMessageData(AppContants.NOT_PERMIT);
        }
        Booking booking = bookingRepository.findBookingByCode(request.getBookingCode())
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("Booking code")));
        Payment payment = paymentRepository.findById(booking.getId()).get();
        if (!booking.getStatus().equals(BookingStatusEnum.WAITING)) {
            return new BaseMessageData(AppContants.NOT_PERMIT);
        }

        AccomPlace accomHost = accomPlaceRepository.findById(booking.getAccomId()).get();
        PartnerEarning partnerEarning = partnerEarningRepository.findById(booking.getId()).get();
        AdminEarning adminEarning = adminEarningRepository.findById(booking.getId()).get();
        CancellationPolicyEnum cancellationPolicy = accomHost.getCancellationPolicy();

        LocalDate today = LocalDate.now();
        long days = ChronoUnit.DAYS.between(today, booking.getCheckIn());
        double cancellationFee = (accomHost.getCancellationFeeRate() * payment.getTotalBill()) / 100;
        double adminEarn = (cancellationFee * FeeRateOfAdminConstant.FEE_RATE_OF_ADMIN) / 100;
        double partnerEarn = cancellationFee - adminEarn;

        boolean canCancel = true;

        switch (cancellationPolicy) {
            case NO_CANCEL:
                canCancel = false;
                break;
            case CANCEL_24H:
                if (days < 1)
                    canCancel = false;
                break;
            case CANCEL_5D:
                if (days < 5)
                    canCancel = false;
                break;
            case CANCEL_7D:
                if (days < 7)
                    canCancel = false;
                break;
            case CANCEL_15D:
                if (days < 15)
                    canCancel = false;
                break;
            case CANCEL_30D:
                if (days < 30)
                    canCancel = false;
                break;
        }

        if (!canCancel) {
            return new BaseMessageData(BookingConstant.CANCEL_BOOKING_UNSUCCESS);
        }
        bookingRepository.changeStatusBooking(request.getBookingCode(), BookingStatusEnum.CANCELED.toString());
        payment.setCancellationFee(cancellationFee);
        payment.setCancelReason(request.getCancelReason());
        payment.setCancelPeriod(LocalDateTime.now());

        adminEarning.setEarningAmount(adminEarn);
        partnerEarning.setEarningAmount(partnerEarn);
        adminEarningRepository.save(adminEarning);
        partnerEarningRepository.save(partnerEarning);
        return new BaseMessageData(BookingConstant.CANCEL_BOOKING_SUCCESS);
    }
}
