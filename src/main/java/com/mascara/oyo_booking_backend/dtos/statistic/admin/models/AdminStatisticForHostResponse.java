package com.mascara.oyo_booking_backend.dtos.statistic.admin.models;

import lombok.Builder;
import lombok.Data;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 26/12/2023
 * Time      : 3:10 SA
 * Filename  : AdminStatisticForOwnerResponse
 */
@Data
@Builder
public class AdminStatisticForHostResponse {
    private Long userId;
    private String fullName;
    private String email;
    private Long numberOfAccom;
    private Long numberOfBooking;
    private Double totalRevenue;
}
