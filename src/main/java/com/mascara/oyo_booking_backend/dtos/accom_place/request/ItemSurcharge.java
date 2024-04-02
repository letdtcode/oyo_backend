package com.mascara.oyo_booking_backend.dtos.accom_place.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 07/12/2023
 * Time      : 3:14 CH
 * Filename  : ItemSurcharge
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemSurcharge {
    @NotNull
    @NotBlank
    private String surchargeCode;

    @NotNull
    private Double cost;
}
