package com.mascara.oyo_booking_backend.dtos.request.facility_category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 25/11/2023
 * Time      : 7:44 CH
 * Filename  : AddFacilityCategoryRequest
 */
@Data
@AllArgsConstructor
public class AddFacilityCategoryRequest {
    @NotNull
    @NotBlank
    private String faciCateName;
    @NotNull
    @NotBlank
    private String faciCateCode;
    @NotNull
    @NotBlank
    @Pattern(regexp = "(?i)Enable|Disable", message = "Status must be 'Enable' or 'Disable'")
    private String status;
}
