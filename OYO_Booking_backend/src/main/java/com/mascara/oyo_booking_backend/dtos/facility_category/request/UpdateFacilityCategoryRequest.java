package com.mascara.oyo_booking_backend.dtos.facility_category.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 25/11/2023
 * Time      : 7:46 CH
 * Filename  : UpdateFacilityCategoryRequest
 */
@Data
@AllArgsConstructor
public class UpdateFacilityCategoryRequest {
    @NotNull
    @NotBlank
    private String faciCateName;

    @NotNull
    @NotBlank
    private String description;

    @NotNull
    @NotBlank
    @Pattern(regexp = "(?i)Enable|Disable", message = "Status must be 'Enable' or 'Disable'")
    private String status;
}
