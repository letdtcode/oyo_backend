package com.mascara.oyo_booking_backend.dtos.request.facility;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 25/11/2023
 * Time      : 6:57 CH
 * Filename  : UpdateFacilityRequest
 */
@Data
@AllArgsConstructor
public class UpdateFacilityRequest {

    @NotNull
    @NotBlank
    private String facilityName;

    @NotNull
    @NotBlank
    private String facilityCateCode;

    @NotNull
    @NotBlank
    private String imageUrl;

    @NotNull
    @NotBlank
    @Pattern(regexp = "(?i)Enable|Disable", message = "Status must be 'Enable' or 'Disable'")
    private String status;
}
