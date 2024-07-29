package com.mascara.oyo_booking_backend.dtos.accom_place.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 21/05/2024
 * Time      : 11:28 CH
 * Filename  : GetRangeDateBookingAccom
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetRangeDateBookingAccomResponse {
    private Long accomId;
    private List<RangeDateBooking> rangeDateBookingList;
}
