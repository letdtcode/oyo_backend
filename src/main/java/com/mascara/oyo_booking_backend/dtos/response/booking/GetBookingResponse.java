package com.mascara.oyo_booking_backend.dtos.response.booking;

import com.mascara.oyo_booking_backend.entities.AccomPlace;
import com.mascara.oyo_booking_backend.entities.BookingList;
import com.mascara.oyo_booking_backend.entities.Revenue;
import com.mascara.oyo_booking_backend.enums.BookingStatusEnum;
import com.mascara.oyo_booking_backend.enums.PaymentMethodEnum;
import com.mascara.oyo_booking_backend.enums.PaymentPolicyEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 29/11/2023
 * Time      : 2:35 SA
 * Filename  : GetOrderResponse
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetBookingResponse {
    private String bookingCode;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private String nameCustomer;
    private String phoneNumberCustomer;
    private BigDecimal originPay;
    private BigDecimal surcharge;
    private BigDecimal totalBill;
    private BigDecimal totalTransfer;
    private PaymentPolicyEnum paymentPolicy;
    private PaymentMethodEnum paymentMethod;
    private Integer numAdult;
    private Integer numChild;
    private Integer numBornChild;
    private BookingStatusEnum bookingStatusEnum;
    private Long accomId;
    private Long bookListId;

    @OneToOne(mappedBy = "booking", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Revenue revenue;
}
