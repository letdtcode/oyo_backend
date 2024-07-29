package com.mascara.oyo_booking_backend.common.mapper.helper;

import com.mascara.oyo_booking_backend.entities.booking.Booking;
import com.mascara.oyo_booking_backend.entities.order.AdminEarning;
import com.mascara.oyo_booking_backend.entities.order.PartnerEarning;
import com.mascara.oyo_booking_backend.entities.order.Payment;
import com.mascara.oyo_booking_backend.entities.review.Review;
import com.mascara.oyo_booking_backend.repositories.AdminEarningRepository;
import com.mascara.oyo_booking_backend.repositories.BookingRepository;
import com.mascara.oyo_booking_backend.repositories.PartnerEarningRepository;
import com.mascara.oyo_booking_backend.repositories.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 17/06/2024
 * Time      : 9:41 CH
 * Filename  : BookingHelperMapper
 */
@Component
@RequiredArgsConstructor
public class BookingHelperMapper {
    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;

    private final AdminEarningRepository adminEarningRepository;
    private final PartnerEarningRepository partnerEarningRepository;

    //        Covert booking id to origin pay
    @Named("bookingIdToOriginPay")
    public Double bookingIdToOriginPay(Long bookingId) {
        if (bookingId != null) {
            Payment payment = paymentRepository.findById(bookingId).get();
            return payment.getOriginPay();
        }
        return null;
    }

    //        Covert booking id to surcharge pay
    @Named("bookingIdToSurchargePay")
    public Double bookingIdToSurchargePay(Long bookingId) {
        if (bookingId != null) {
            Payment payment = paymentRepository.findById(bookingId).get();
            return payment.getSurchargePay();
        }
        return null;
    }

    //        Covert booking id to total bill
    @Named("bookingIdToTotalBill")
    public Double bookingIdToTotalBill(Long bookingId) {
        if (bookingId != null) {
            Payment payment = paymentRepository.findById(bookingId).get();
            return payment.getTotalBill();
        }
        return null;
    }

    //        Covert booking id to total bill
    @Named("bookingIdToAdminEarning")
    public Double bookingIdToAdminEarning(Long bookingId) {
        if (bookingId != null) {
            AdminEarning adminEarning = adminEarningRepository.findById(bookingId).get();
            return adminEarning.getEarningAmount();
        }
        return null;
    }

    //        Covert booking id to total bill
    @Named("bookingIdToPartnerEarning")
    public Double bookingIdToPartnerEarning(Long bookingId) {
        if (bookingId != null) {
            PartnerEarning partnerEarning = partnerEarningRepository.findById(bookingId).get();
            return partnerEarning.getEarningAmount();
        }
        return null;
    }

    //        Covert booking id to total transfer
    @Named("bookingIdToTotalTransfer")
    public Double bookingIdToTotalTransfer(Long bookingId) {
        if (bookingId != null) {
            Payment payment = paymentRepository.findById(bookingId).get();
            return payment.getTotalTransfer();
        }
        return null;
    }

    //        Covert booking id to payment policy
    @Named("bookingIdToPaymentPolicy")
    public String bookingIdToPaymentPolicy(Long bookingId) {
        if (bookingId != null) {
            Payment payment = paymentRepository.findById(bookingId).get();
            return payment.getPaymentPolicy().toString();
        }
        return null;
    }

    //        Covert booking id to payment method
    @Named("bookingIdToPaymentMethod")
    public String bookingIdToPaymentMethod(Long bookingId) {
        if (bookingId != null) {
            Payment payment = paymentRepository.findById(bookingId).get();
            return payment.getPaymentMethod().toString();
        }
        return null;
    }

    @Named("idBookingToIsReviewed")
    public boolean idBookingToIsReviewed(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).get();
        Review reviewOfBooking = booking.getReview();
        if (reviewOfBooking != null)
            return true;
        return false;
    }
}
