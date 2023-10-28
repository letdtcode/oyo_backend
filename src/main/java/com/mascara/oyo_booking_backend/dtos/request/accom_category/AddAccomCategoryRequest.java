package com.mascara.oyo_booking_backend.dtos.request.accom_category;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 25/10/2023
 * Time      : 4:35 CH
 * Filename  : AddAccomCategoryRequest
 */
@Data
@AllArgsConstructor
public class AddAccomCategoryRequest {

    @NotNull
    @NotBlank
    private String accomCateName;

    @NotNull
    @NotBlank
    private String description;
}
