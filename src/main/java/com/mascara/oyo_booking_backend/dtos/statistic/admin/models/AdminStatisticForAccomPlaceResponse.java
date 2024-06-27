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
public class AdminStatisticForAccomPlaceResponse {
    private Long accomId;
    private String accomName;
    private String hostName;
    private Long numberOfView;
    private Long numberOfBooking;
    private Double totalRevenue;
    private Long numberOfReview;
    private Float averageGradeRate;
}
