package com.mascara.oyo_booking_backend.dtos.statistic.common;

import lombok.Builder;
import lombok.Data;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 28/06/2024
 * Time      : 6:12 CH
 * Filename  : RevenueStatistic
 */
@Data
@Builder
public class RevenueStatistic {
    private String month;
    private Double amount;
}
