package com.mascara.oyo_booking_backend.dtos.request.booking;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.EnumNaming;
import com.mascara.oyo_booking_backend.enums.PaymentMethodEnum;
import com.mascara.oyo_booking_backend.enums.PaymentPolicyEnum;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

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
    @Min(0)
    private BigDecimal originPay;

    @NotNull
    @Min(0)
    private BigDecimal surcharge;

    @NotNull
    @Min(0)
    private BigDecimal totalTransfer;

    @NotNull
    @Pattern(regexp = "PAYMENT_FULL|PAYMENT_HALF", message = "Payment policy must be 'PAYMENT_FULL' or 'PAYMENT_HALF'")
    private String paymentPolicy;

    @NotNull
    @Pattern(regexp = "DIRECT|PAYPAL", message = "Payment method must be 'DIRECT' or 'PAYPAL'")
    private String paymentMethod;

    @NotNull
    @Positive
    private Long accomId;
}
