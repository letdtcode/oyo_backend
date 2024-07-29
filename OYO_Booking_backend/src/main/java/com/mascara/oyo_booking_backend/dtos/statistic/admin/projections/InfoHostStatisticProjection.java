package com.mascara.oyo_booking_backend.dtos.statistic.admin.projections;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 26/06/2024
 * Time      : 9:09 CH
 * Filename  : InfoHostStatisticProjection
 */
public interface InfoHostStatisticProjection {
    Long getUserId();

    String getFirstName();

    String getLastName();

    String getEmail();

    Long getNumberOfAccom();

    Long getNumberOfBooking();

    Double getTotalRevenue();
}
