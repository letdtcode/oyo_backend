package com.mascara.oyo_booking_backend.dtos.booking.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 03/12/2023
 * Time      : 8:44 CH
 * Filename  : GetHistoryBookingResponse
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetHistoryBookingResponse {
    private Long accomId;
    private String bookingCode;
    private String fullNameHost;
    private String nameAccom;
    private String generalAddress;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate checkIn;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate checkOut;
    private String imageUrl;
    private Integer numAdult;
    private Integer numChild;
    private Integer numBornChild;
    private Double originPay;
    private Double surcharge;
    private Double totalBill;
    private Double totalTransfer;
    private Double pricePerNight;
    private String paymentPolicy;
    private String paymentMethod;
    private boolean isReviewed;
    private String status;
    private String cancellationPolicy;
    private Integer cancellationFeeRate;
}
