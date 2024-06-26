package com.mascara.oyo_booking_backend.dtos.accom_place.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 21/05/2024
 * Time      : 11:26 CH
 * Filename  : GetPriceCustomAccom
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetPriceCustomAccomResponse {
    private Long accomId;
    private String accomName;
    private Double pricePerNight;
    private List<PriceCustomForAccom> priceCustomForAccomList;
    private List<RangeDateBooking> rangeDateBookingList;
}
