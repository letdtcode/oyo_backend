package com.mascara.oyo_booking_backend.dtos.accom_place.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 05/03/2024
 * Time      : 5:43 CH
 * Filename  : UpdateCancellationPolicyRequest
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePolicyAccomRequest {
    @NotNull
    @NotBlank
    private String cancellationPolicy;

    @NotNull
    @Min(value = 0, message = "Cancellation fee rate must in range 0-50")
    @Max(value = 50, message = "Cancellation fee rate must in range 0-50")
    private Integer cancellationFeeRate;

    @NotNull
    private Boolean allowEvent;

    @NotNull
    private Boolean allowPet;

    @NotNull
    private Boolean allowSmoking;
}
