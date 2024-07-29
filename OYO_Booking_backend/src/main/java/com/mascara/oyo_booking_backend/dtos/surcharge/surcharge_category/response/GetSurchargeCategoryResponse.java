package com.mascara.oyo_booking_backend.dtos.surcharge.surcharge_category.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 07/12/2023
 * Time      : 8:56 CH
 * Filename  : SurchargeCategoryResponse
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetSurchargeCategoryResponse {
    private Long id;
    private String surchargeCateName;
    private String surchargeCode;
    private String status;
}
