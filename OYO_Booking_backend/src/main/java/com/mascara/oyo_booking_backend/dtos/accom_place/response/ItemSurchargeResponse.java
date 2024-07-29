package com.mascara.oyo_booking_backend.dtos.accom_place.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 11/04/2024
 * Time      : 2:54 CH
 * Filename  : ItemSurchargeResponse
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemSurchargeResponse {
    private String surchargeCode;
    private String surchargeName;
    private Double cost;
}