package com.mascara.oyo_booking_backend.dtos.request.accom_place;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 07/12/2023
 * Time      : 2:50 CH
 * Filename  : UpdateAddressAccomRequest
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAddressAccomRequest {
    @NotNull
    @NotBlank
    private String addressDetail;

    @NotNull
    @NotBlank
    private String districtCode;

    @NotNull
    @NotBlank
    private String provinceCode;

    @NotNull
    @NotBlank
    private String wardCode;
}
