package com.mascara.oyo_booking_backend.dtos.accom_place.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 04/04/2024
 * Time      : 6:48 CH
 * Filename  : GetPaymentAccomReponse
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetPaymentAccomResponse {
    private Long bankId;
    private String accountNumber;
    private String accountNameHost;
    private String swiftCode;
}
