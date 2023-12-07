package com.mascara.oyo_booking_backend.services.surcharge;

import com.mascara.oyo_booking_backend.dtos.response.surcharge.SurchargeCategoryResponse;

import java.util.List;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 07/12/2023
 * Time      : 8:48 CH
 * Filename  : SurchargeService
 */
public interface SurchargeService {
    List<SurchargeCategoryResponse> getAllSurchargeCategoryByStatus(String status);
}
