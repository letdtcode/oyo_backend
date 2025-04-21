package com.mascara.oyo_booking_backend.dtos.accom_category.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 25/10/2023
 * Time      : 4:40 CH
 * Filename  : UpdateAccomCategoryRequest
 */
@Data
@AllArgsConstructor
public class UpdateAccomCategoryRequest {

    @NotNull(message = "Can not be null")
    @NotBlank(message = "Can not be blank")
    private String accomCateName;

    @NotNull
    @NotBlank
    private String description;

    @NotNull
    @NotBlank
    private String icon;

    @NotNull
    @NotBlank
    @Pattern(regexp = "(?i)Enable|Disable", message = "Status must be 'Enable' or 'Disable'")
    private String status;
}
