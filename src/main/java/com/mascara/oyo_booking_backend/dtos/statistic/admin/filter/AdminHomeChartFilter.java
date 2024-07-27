package com.mascara.oyo_booking_backend.dtos.statistic.admin.filter;

import com.mascara.oyo_booking_backend.common.enums.AdminChartTypeEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 28/06/2024
 * Time      : 5:47 CH
 * Filename  : AdminHomeChartFilter
 */
@Data
public class AdminHomeChartFilter {
    @NotNull
    private Integer year;

    @NotNull
    private AdminChartTypeEnum type;
}
