package com.mascara.oyo_booking_backend.dtos.statistic.partner.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 03/07/2024
 * Time      : 4:25 CH
 * Filename  : HostHomeStatisticMonth
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HostHomeStatisticMonthResponse {
    private Long accomId;
    private String accomName;
    private Long numberOfView;
    private Long numberOfBooking;
    private Double revenue;
    private Long numberOfReview;
    private Double averageRate;
    private Double reservationRate;
}
