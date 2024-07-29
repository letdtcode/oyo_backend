package com.mascara.oyo_booking_backend.dtos.statistic.partner.filter;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 02/07/2024
 * Time      : 8:53 CH
 * Filename  : HostHomeStatisticMonthFilter
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HostHomeStatisticMonthFilter {
    @NotNull
    private Integer year;
    @NotNull
    private Integer month;
}
