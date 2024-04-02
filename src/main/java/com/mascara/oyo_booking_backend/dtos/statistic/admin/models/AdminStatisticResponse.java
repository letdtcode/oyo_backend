package com.mascara.oyo_booking_backend.dtos.statistic.admin.models;

import lombok.Builder;
import lombok.Data;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 26/12/2023
 * Time      : 2:27 SA
 * Filename  : AdminStatistic
 */
@Data
@Builder
public class AdminStatisticResponse {
    private Long totalNumberOfGuests;
    private Long totalNumberOfOwner;
    private Long totalNumberOfBooking;
    private Double totalNumberOfRevenue;
}
