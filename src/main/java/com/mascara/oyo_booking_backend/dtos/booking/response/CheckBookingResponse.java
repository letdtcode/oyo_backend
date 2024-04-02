package com.mascara.oyo_booking_backend.dtos.booking.response;

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
    private boolean isCanBooking;
    private Double totalCostAccom;
    private Double costSurcharge;
    private Double totalBill;
    private String message;
}
