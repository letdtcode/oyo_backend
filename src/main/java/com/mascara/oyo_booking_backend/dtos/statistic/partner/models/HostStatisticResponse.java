package com.mascara.oyo_booking_backend.dtos.statistic.partner.models;

import com.mascara.oyo_booking_backend.dtos.statistic.common.RevenueStatistic;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 02/07/2024
 * Time      : 8:48 CH
 * Filename  : HostStatisticResponse
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HostStatisticResponse {
    private Long totalNumberOfBooking;
    private Long totalNumberOfBookingFinish;
    private List<HomeBookingStatistic> homeStatistic;
    private List<RevenueStatistic> revenueStatistics;
}
