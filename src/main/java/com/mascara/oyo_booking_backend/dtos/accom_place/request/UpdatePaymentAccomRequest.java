package com.mascara.oyo_booking_backend.dtos.accom_place.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 28/03/2024
 * Time      : 8:11 CH
 * Filename  : UpdatePaymentAccomRequest
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePaymentAccomRequest {

    @NotNull
    private Long bankId;

    @NotNull
    @NotBlank
    private String accountNumber;

    @NotNull
    @NotBlank
    private String accountNameHost;

    @NotNull
    @NotBlank
    private String swiftCode;
}
