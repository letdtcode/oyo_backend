package com.mascara.oyo_booking_backend.dtos.statistic.admin.models;

import com.mascara.oyo_booking_backend.dtos.statistic.common.RevenueStatistic;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 28/06/2024
 * Time      : 5:50 CH
 * Filename  : AdminStatisticChartResponse
 */
@Data
@Builder
public class AdminStatisticChartResponse {
    List<RevenueStatistic> revenueStatistics;
}
