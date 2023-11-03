package com.mascara.oyo_booking_backend.dtos.request.accom_place;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 29/10/2023
 * Time      : 6:45 CH
 * Filename  : GetAccomPlaceFilter
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAccomPlaceFilterRequest {
    private String provinceCode;
    private String districtCode;
    private String wardCode;

    @Min(0L)
    private BigDecimal priceFrom;

    @Min(0L)
    private BigDecimal priceTo;
    private List<String> facilityName;

    @Min(0)
    private Integer numBathroom;

    @Min(0)
    private Integer numPeople;

    @Min(0)
    private Integer numBed;
}
