package com.mascara.oyo_booking_backend.dtos.statistic.partner.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 02/07/2024
 * Time      : 8:50 CH
 * Filename  : HomeBookingStatistic
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HomeBookingStatistic {
    private Long accomId;
    private String accomName;
    private Long numberOfBooking;
}
