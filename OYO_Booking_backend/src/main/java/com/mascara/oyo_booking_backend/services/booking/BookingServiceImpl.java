package com.mascara.oyo_booking_backend.services.booking;

import com.mascara.oyo_booking_backend.common.constant.AppConstant;
import com.mascara.oyo_booking_backend.common.constant.BookingConstant;
import com.mascara.oyo_booking_backend.common.constant.FeeRateOfAdminConstant;
import com.mascara.oyo_booking_backend.common.constant.MessageConstant;
import com.mascara.oyo_booking_backend.common.enums.booking.BookingStatusEnum;
import com.mascara.oyo_booking_backend.common.enums.homestay.CancellationPolicyEnum;
import com.mascara.oyo_booking_backend.common.enums.payment.PaymentMethodEnum;
import com.mascara.oyo_booking_backend.common.enums.payment.PaymentPolicyEnum;
import com.mascara.oyo_booking_backend.common.enums.payment.PaymentStatusEnum;
import com.mascara.oyo_booking_backend.common.exceptions.ResourceNotFoundException;
import com.mascara.oyo_booking_backend.common.mapper.booking.BookingMapper;
import com.mascara.oyo_booking_backend.config.payment.paypal.PayPalHttpClient;
import com.mascara.oyo_booking_backend.config.payment.vnpay.VnpayConfig;
import com.mascara.oyo_booking_backend.dtos.accom_place.response.PriceCustomForAccom;
import com.mascara.oyo_booking_backend.dtos.base.BaseMessageData;
import com.mascara.oyo_booking_backend.dtos.base.BasePagingData;
import com.mascara.oyo_booking_backend.dtos.booking.request.BookingRequest;
import com.mascara.oyo_booking_backend.dtos.booking.request.CancelBookingRequest;
import com.mascara.oyo_booking_backend.dtos.booking.request.CheckBookingRequest;
import com.mascara.oyo_booking_backend.dtos.booking.response.CheckBookingResponse;
import com.mascara.oyo_booking_backend.dtos.booking.response.ClientConfirmBookingResponse;
import com.mascara.oyo_booking_backend.dtos.booking.response.GetBookingResponse;
import com.mascara.oyo_booking_backend.dtos.booking.response.GetHistoryBookingResponse;
import com.mascara.oyo_booking_backend.dtos.payment.PaypalRequest;
import com.mascara.oyo_booking_backend.dtos.payment.PaypalResponse;
import com.mascara.oyo_booking_backend.dtos.payment.enums.OrderIntent;
import com.mascara.oyo_booking_backend.dtos.payment.enums.PaymentLandingPage;
import com.mascara.oyo_booking_backend.dtos.payment.enums.PaypalOrderStatus;
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
import com.mascara.oyo_booking_backend.external_modules.mail.EmailDetails;
import com.mascara.oyo_booking_backend.external_modules.mail.service.EmailService;
import com.mascara.oyo_booking_backend.repositories.*;
import com.mascara.oyo_booking_backend.utils.Utilities;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

    private final VnpayConfig vnpayConfig;

    private final PayPalHttpClient payPalHttpClient;

    private static final int USD_VND_RATE = 23_000;

    @Override
    @Transactional
    public ClientConfirmBookingResponse createOrderBookingAccom(BookingRequest request, String mailUser) {
        AccomPlace accomPlace = accomPlaceRepository.findById(request.getAccomId())
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstant.NOT_FOUND_MESSAGE("accom place")));
        Long hostId = accomPlace.getUserId();
        boolean isAvailable = checkAvailableAccom(accomPlace.getId(), request.getCheckIn(), request.getCheckOut());

        if (!isAvailable) {
            throw new RuntimeException("Booking not available on range time");
        }

        int maxPeople = accomPlace.getNumPeople();
        if (request.getNumAdult() > maxPeople) {
            throw new RuntimeException("Num people over range of homestay");
        }

        accomPlace.setNumBooking(accomPlace.getNumBooking() + 1L);
        Province province = provinceRepository.findByProvinceCode(accomPlace.getProvinceCode())
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstant.NOT_FOUND_MESSAGE("province")));
        province.setNumBooking(province.getNumBooking() + 1L);
        accomPlaceRepository.save(accomPlace);
        provinceRepository.save(province);

        BookingList bookingList = bookingListRepository.findByUserMail(mailUser)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstant.NOT_FOUND_MESSAGE("user")));
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

        // (1) Tạo thông tin hóa đơn
        Payment payment = Payment.builder()
                .originPay(originPay)
                .totalBill(totalBill)
                .totalTransfer(totalTrasfer)
                .surchargePay(costSurcharge)
                .paymentPolicy(PaymentPolicyEnum.valueOf(request.getPaymentPolicy()))
                .paymentMethod(PaymentMethodEnum.valueOf(request.getPaymentMethod()))
                .booking(booking)
                .paymentStatus(PaymentStatusEnum.UNPAID)
                .build();

        // (2) Tạo response
        ClientConfirmBookingResponse response = ClientConfirmBookingResponse.builder()
                .bookingCode(bookingCode)
                .paymentMethod(PaymentMethodEnum.valueOf(request.getPaymentMethod()))
                .build();

        // (3) Kiểm tra hình thức thanh toán
        if (request.getPaymentMethod().equals(PaymentMethodEnum.PAYPAL.name())) {
            try {
                // (3.1.1) Đổi tiền sang USD
                BigDecimal totalPayUSD = BigDecimal.valueOf(totalTrasfer)
                        .divide(BigDecimal.valueOf(USD_VND_RATE), 0, RoundingMode.HALF_UP);

                // (3.1.2) Tạo một yêu cầu giao dịch Paypal
                PaypalRequest paypalRequest = new PaypalRequest();
                paypalRequest.setIntent(OrderIntent.CAPTURE);
                paypalRequest.setPurchaseUnits(List.of(
                        new PaypalRequest.PurchaseUnit(
                                new PaypalRequest.PurchaseUnit.Money("USD", totalPayUSD.toString())
                        )
                ));

                paypalRequest.setApplicationContext(new PaypalRequest.PayPalAppContext()
                        .setBrandName("OYO")
                        .setLandingPage(PaymentLandingPage.BILLING)
                        .setReturnUrl(AppConstant.BACKEND_HOST + "/api/v1/client/booking/success")
                        .setCancelUrl(AppConstant.BACKEND_HOST + "/api/v1/client/booking/cancel"));

                PaypalResponse paypalResponse = payPalHttpClient.createPaypalTransaction(paypalRequest);

                // (3.1.3) Lưu thông tin thanh toán paypal
                payment.setPaypalOrderId(paypalResponse.getId());
                payment.setPaypalOrderStatus(paypalResponse.getStatus().toString());
                paymentRepository.save(payment);

                for (PaypalResponse.Link link : paypalResponse.getLinks()) {
                    if ("approve".equals(link.getRel())) {
                        response.setBookingPaypalCheckoutLink(link.getHref());
                    }
                }
            } catch (Exception ex) {
                throw new RuntimeException("Can not create Paypal transaction request !" + ex);
            }
        } else if (request.getPaymentMethod().equals(PaymentMethodEnum.VNPAY.name())) {

        } else {
            throw new RuntimeException("Can not identify payment method");
        }
        Double moneyForAdmin = (FeeRateOfAdminConstant.FEE_RATE_OF_ADMIN * totalBill) / 100;
        Double moneyForPartner = totalBill - moneyForAdmin;

        PartnerEarning partnerEarning = PartnerEarning.builder()
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
        return response;
    }

    public boolean checkAvailableAccom(Long accomId, LocalDate checkIn, LocalDate checkOut) {
        boolean isAvailable = bookingRepository.checkBookingAvailable(accomId, checkIn, checkOut);
        return isAvailable;
    }

    @Override
    @Transactional
    public CheckBookingResponse checkBookingToGetPrice(CheckBookingRequest request) {
        AccomPlace accomPlace = accomPlaceRepository.findById(request.getAccomId())
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstant.NOT_FOUND_MESSAGE("Accom place")));
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
        return new CheckBookingResponse(isCanBooking, priceCustomForAccoms, message);
    }

    @Override
    public void captureTransactionBooking(String paypalOrderId, String payerId) {
        Payment payment = paymentRepository.findByPaypalOrderId(paypalOrderId)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstant.NOT_FOUND_MESSAGE("Paypal order")));
        payment.setPaypalOrderStatus(PaypalOrderStatus.APPROVED.toString());

        // (1) Lấy thông tin chủ nhà
        Booking booking = bookingRepository.findById(payment.getId()).get();
        AccomPlace accomPlace = accomPlaceRepository.findById(booking.getAccomId()).get();
        User host = userRepository.findHostOfAccomByBookingCode(booking.getBookingCode()).get();

        // (2) Lấy thông tin khách hàng
        User customer = userRepository.findById(booking.getBookListId()).get();
        try {
            // (3.1) Capture thanh toán
            payPalHttpClient.capturePaypalTransaction(paypalOrderId, payerId);

            // (3.2) Cập nhật payment
            payment.setPaypalOrderStatus(PaypalOrderStatus.COMPLETED.toString());
            payment.setPaymentStatus(PaymentStatusEnum.PAID); // Đã thanh toán

            // (4) Gửi mail thông tin đặt phòng cho khách hàng
            String fullHostName = host.getFirstName() + " " + host.getLastName();
            Map<String, Object> model = new HashMap<>();
            model.put("billId", booking.getBookingCode());
            model.put("homeName", accomPlace.getAccomName());
            model.put("ownerName", fullHostName);
            model.put("dateStart", booking.getCheckIn());
            model.put("dateEnd", booking.getCheckOut());
            model.put("baseCost", payment.getOriginPay());
            model.put("surchargeCost", payment.getSurchargePay());
            model.put("totalCost", payment.getTotalBill());
            model.put("moneyPay", payment.getTotalTransfer());
            model.put("createdDate", booking.getCreatedDate());
            model.put("fullNameCustomer", booking.getNameCustomer());
            EmailDetails emailDetails = EmailDetails.builder()
                    .recipient(customer.getMail())
                    .subject("Đặt phòng thành công").build();
            emailService.sendMailWithTemplate(emailDetails, "Email_Booking_Success.ftl", model);

            // (5) Lưu thông tin thông báo
            StringBuilder content = new StringBuilder("Khách hàng ");
            content.append(booking.getNameCustomer());
            content.append(" vừa mới đặt chỗ ở " + accomPlace.getAccomName() + " của bạn");
            Notification notification = Notification.builder()
                    .senderMail(customer.getMail())
                    .recipient(host)
                    .recipientMail(host.getMail())
                    .title("Bạn có đơn đặt phòng mới")
                    .content(content.toString())
                    .imageUrl(null)
                    .build();

            notificationRepository.save(notification);

            // (6) Bắn message thông báo notification cho chủ nhà
            int numNotiUnviewOfUser = notificationRepository.countByRecipientMailAndViewAndDeleted(
                    host.getMail(),
                    false, false);
            messagingTemplate.convertAndSendToUser(
                    host.getMail(), "/queue/messages",
                    numNotiUnviewOfUser
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // (7) Cập nhật payment
        paymentRepository.save(payment);
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
            return new BaseMessageData(MessageConstant.NOT_PERMIT);
        }
        bookingRepository.changeStatusBooking(bookingCode, status);
        return new BaseMessageData(MessageConstant.CHANGE_STATUS_BOOKING_SUCCESS);
    }

    @Override
    @Transactional
    public BaseMessageData cancelBooking(String userMail, CancelBookingRequest request) {
        User user = userRepository.findUserByBookingCode(request.getBookingCode()).get();
        if (!user.getMail().equals(userMail)) {
            return new BaseMessageData(MessageConstant.NOT_PERMIT);
        }
        Booking booking = bookingRepository.findBookingByCode(request.getBookingCode())
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstant.NOT_FOUND_MESSAGE("Booking code")));
        Payment payment = paymentRepository.findById(booking.getId()).get();
        if (!booking.getStatus().equals(BookingStatusEnum.WAITING)) {
            return new BaseMessageData(MessageConstant.NOT_PERMIT);
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
