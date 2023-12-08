package com.mascara.oyo_booking_backend.dtos.request.surcharge_category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 08/12/2023
 * Time      : 4:10 CH
 * Filename  : AddSurcharge
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddSurchargeCategoryRequest {

    @NotNull
    @NotBlank
    private String surchargeCateName;

    @NotNull
    @NotBlank
    private String surchargeCode;

    @NotNull
    @NotBlank
    private String status;
}
