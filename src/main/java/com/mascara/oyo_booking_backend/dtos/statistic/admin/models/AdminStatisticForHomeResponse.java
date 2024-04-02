package com.mascara.oyo_booking_backend.dtos.statistic.admin.models;

import lombok.Builder;
import lombok.Data;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 26/12/2023
 * Time      : 3:10 SA
 * Filename  : AdminStatisticForHomeResponse
 */
@Data
@Builder
public class AdminStatisticForHomeResponse {
    private String ownerName;
    private String titleAccom;
    private Long numBooking;
    private Long numView;
    private Long numReview;
    private Float averageRate;
    private Float bookingSuccessPercent;
    private Double totalRevenue;
}
