package com.mascara.oyo_booking_backend.dtos.statistic.admin.projections;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 27/06/2024
 * Time      : 10:40 CH
 * Filename  : InfoHomestayStatisticProjection
 */
public interface InfoAccomPlaceStatisticProjection {
    Long getAccomId();

    String getAccomName();

    String getHostFirstName();
    String getHostLastName();

    Long getNumberOfView();

    Long getNumberOfBooking();

    Double getTotalRevenue();

    Long getNumberOfReview();

    Float getAverageGradeRate();
}
