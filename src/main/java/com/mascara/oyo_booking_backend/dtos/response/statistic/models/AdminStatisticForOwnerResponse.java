package com.mascara.oyo_booking_backend.dtos.response.statistic.models;

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
public class AdminStatisticForOwnerResponse {
    private String ownerName;
    private String email;
    private Long numberOfAccomForRent;
    private Long numberOfBooking;
    private Double totalRevenueOfOwner;
}
