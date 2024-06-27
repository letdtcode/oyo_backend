package com.mascara.oyo_booking_backend.services.statistic.admin;

import com.mascara.oyo_booking_backend.dtos.base.BasePagingData;
import com.mascara.oyo_booking_backend.dtos.statistic.admin.filter.AdminHomeStatisticFilter;
import com.mascara.oyo_booking_backend.dtos.statistic.admin.filter.AdminStatisticDateFilter;
import com.mascara.oyo_booking_backend.dtos.statistic.admin.models.*;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 26/12/2023
 * Time      : 2:44 SA
 * Filename  : AdminStatistic
 */
public interface AdminStatisticService {
    AdminStatisticResponse getStatisticOfAdmin(AdminHomeStatisticFilter request);

    BasePagingData<AdminStatisticForGuestResponse> getStatisticForGuestOfAdmin(AdminStatisticDateFilter request,
                                                                               Integer pageNumber,
                                                                               Integer pageSize);

    BasePagingData<AdminStatisticForHostResponse> getStatisticForHostOfAdmin(AdminStatisticDateFilter request,
                                                                             Integer pageNumber,
                                                                             Integer pageSize);

    BasePagingData<AdminStatisticForAccomPlaceResponse> getStatisticForAccomPlaceOfAdmin(AdminStatisticDateFilter request, Integer pageNumber, Integer pageSize);

    BasePagingData<AdminStatisticTransactionResponse> getStatisticForTransactionOfAdmin(AdminStatisticDateFilter request, Integer pageNumber, Integer pageSize);
}
