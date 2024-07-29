package com.mascara.oyo_booking_backend.dtos.statistic.admin.filter;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 26/06/2024
 * Time      : 7:15 CH
 * Filename  : AdminStatisticDateFilter
 */

@Data
public class AdminStatisticDateFilter {
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateStart;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateEnd;
}
