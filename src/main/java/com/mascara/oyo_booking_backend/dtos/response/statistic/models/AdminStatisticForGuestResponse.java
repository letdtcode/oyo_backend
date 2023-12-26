package com.mascara.oyo_booking_backend.dtos.response.statistic.models;

import lombok.Builder;
import lombok.Data;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 26/12/2023
 * Time      : 3:10 SA
 * Filename  : AdminStatisticForGuestResponse
 */
@Data
@Builder
public class AdminStatisticForGuestResponse {
    private Long userId;
    private String fullName;
    private String email;
    private Float rate;
    private Double totalExpense;
    private Long numberOfBooking;
}
