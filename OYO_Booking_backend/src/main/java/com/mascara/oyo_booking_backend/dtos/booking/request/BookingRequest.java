package com.mascara.oyo_booking_backend.dtos.booking.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @NotNull
    @NotBlank
    private String nameCustomer;
    @NotNull
    @NotBlank
    private String phoneNumberCustomer;

    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate checkIn;

    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate checkOut;

    @NotNull
    @Min(0)
    private Integer numAdult;

    @NotNull
    @Min(0)
    private Integer numChild;

    @NotNull
    @Min(0)
    private Integer numBornChild;

    @NotNull
    @Pattern(regexp = "PAYMENT_FULL|PAYMENT_HALF", message = "Payment policy must be 'PAYMENT_FULL' or 'PAYMENT_HALF'")
    private String paymentPolicy;

    @NotNull
    @Pattern(regexp = "DIRECT|PAYPAL|VNPAY", message = "Payment method must be 'DIRECT' or 'PAYPAL' or 'VNPAY'")
    private String paymentMethod;

    @NotNull
    @Positive
    private Long accomId;
}
