package com.mascara.oyo_booking_backend.dtos.surcharge.surcharge_category.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 08/12/2023
 * Time      : 4:31 CH
 * Filename  : UpdateSurchargeCategoryRequest
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateSurchargeCategoryRequest {
    @NotNull
    @NotBlank
    private String surchargeCateName;

    @NotNull
    @NotBlank
    private String status;
}
