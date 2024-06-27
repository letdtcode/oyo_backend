package com.mascara.oyo_booking_backend.dtos.statistic.admin.filter;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 26/12/2023
 * Time      : 3:01 SA
 * Filename  : AdminStatisticRequest
 */
@Data
public class AdminHomeStatisticFilter {
    @NotNull
    private Integer year;
}
