package com.mascara.oyo_booking_backend.dtos.accom_place.request;

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
    @NotBlank
    @NotNull
    private String numHouseAndStreetName;

    @NotNull
    @NotBlank
    private String districtCode;

    @NotNull
    @NotBlank
    private String provinceCode;

    @NotNull
    @NotBlank
    private String wardCode;

    @NotNull
    private Double longitude;

    @NotNull
    private Double latitude;
}
