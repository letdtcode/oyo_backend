package com.mascara.oyo_booking_backend.dtos.statistic.admin.projections;

import java.util.UUID;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 26/06/2024
 * Time      : 7:23 CH
 * Filename  : InfoUserBookingProjection
 */
public interface InfoGuestBookingProjection {
    Long getUserId();
    String getFirstName();
    String getLastName();
    String getEmail();
    String getPhoneNumber();
    Long getNumberOfBooking();
    Double getTotalCost();
}
