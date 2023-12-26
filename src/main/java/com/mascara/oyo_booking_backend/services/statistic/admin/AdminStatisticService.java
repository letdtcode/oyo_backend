package com.mascara.oyo_booking_backend.services.statistic.admin;

import com.mascara.oyo_booking_backend.dtos.request.statistic.admin.AdminStatisticRequest;
import com.mascara.oyo_booking_backend.dtos.response.statistic.models.AdminStatisticResponse;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 26/12/2023
 * Time      : 2:44 SA
 * Filename  : AdminStatistic
 */
public interface AdminStatisticService {
    AdminStatisticResponse getStatisticOfAdmin(AdminStatisticRequest request);
}
