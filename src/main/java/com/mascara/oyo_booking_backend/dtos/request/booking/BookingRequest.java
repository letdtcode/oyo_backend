package com.mascara.oyo_booking_backend.dtos.request.booking;

import com.mascara.oyo_booking_backend.entities.AccomPlace;
import com.mascara.oyo_booking_backend.entities.BookingList;
import com.mascara.oyo_booking_backend.entities.Revenue;
import com.mascara.oyo_booking_backend.enums.BookingStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 04/11/2023
 * Time      : 1:59 SA
 * Filename  : BookingRequest
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequest {
    private LocalDate checkIn;
    private LocalDate checkOut;
    private String mailCustomer;
    private BigDecimal originPay;
    private BigDecimal surcharge;
    private BigDecimal totalBill;
    private BigDecimal totalTransfer;
    private Integer paymentMethod;
    private Integer paymentBy;
    private Integer numAdult;
    private Integer numChild;
    private Integer numBornChild;
    private Long accomId;
    private Long userId;
}
