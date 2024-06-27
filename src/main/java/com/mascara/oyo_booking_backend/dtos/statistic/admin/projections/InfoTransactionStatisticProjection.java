package com.mascara.oyo_booking_backend.dtos.statistic.admin.projections;

import java.time.LocalDateTime;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 27/06/2024
 * Time      : 11:25 CH
 * Filename  : InfoTransactionStatisticProjection
 */
public interface InfoTransactionStatisticProjection {
    Long getBookingId();

    String getCustomerName();

    String getOwnerFirstName();
    String getOwnerLastName();

    Double getTotalCost();

    Double getAdminCost();

    String getHomeName();

    LocalDateTime getCreatedDate();
}
