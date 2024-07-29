package com.mascara.oyo_booking_backend.dtos.statistic.partner.projections;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 03/07/2024
 * Time      : 6:28 CH
 * Filename  : HostHomeStatisticProjection
 */
public interface HostHomeStatisticProjection {
    Long getAccomId();

    String getAccomName();

    Long getNumberOfView();

    Long getNumberOfBooking();

    Double getRevenue();

    Long getNumberOfReview();

    Double getAverageRate();

    Double getReservationRate();
}
