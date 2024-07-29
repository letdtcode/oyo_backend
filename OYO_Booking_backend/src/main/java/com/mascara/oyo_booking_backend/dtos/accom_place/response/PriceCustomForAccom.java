package com.mascara.oyo_booking_backend.dtos.accom_place.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 21/05/2024
 * Time      : 11:15 CH
 * Filename  : PriceCustomForAccom
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PriceCustomForAccom {
    private LocalDate dateApply;
    private Double priceApply;
}
