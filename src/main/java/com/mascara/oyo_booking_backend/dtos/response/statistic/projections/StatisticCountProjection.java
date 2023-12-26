package com.mascara.oyo_booking_backend.dtos.response.statistic.projections;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 23/12/2023
 * Time      : 7:11 CH
 * Filename  : StatisticCountProjection
 */
public interface StatisticCountProjection {
    Long getNumberOfGuest();
    Long getNumberOfOwner();
    Long getNumberOfBooking();
    Long getNumberOfRevenue();
}