package com.mascara.oyo_booking_backend.dtos.response.booking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 02/12/2023
 * Time      : 2:08 SA
 * Filename  : CheckBookingResponse
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckBookingResponse {
    private String message;
    private Double priceAccom;
//    private Double
}
