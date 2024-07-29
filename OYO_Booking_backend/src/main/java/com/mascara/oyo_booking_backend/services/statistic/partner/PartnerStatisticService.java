package com.mascara.oyo_booking_backend.services.statistic.partner;

import com.mascara.oyo_booking_backend.dtos.base.BasePagingData;
import com.mascara.oyo_booking_backend.dtos.statistic.partner.filter.HostHomeStatisticFilter;
import com.mascara.oyo_booking_backend.dtos.statistic.partner.filter.HostHomeStatisticMonthFilter;
import com.mascara.oyo_booking_backend.dtos.statistic.partner.models.HostHomeStatisticMonthResponse;
import com.mascara.oyo_booking_backend.dtos.statistic.partner.models.HostStatisticResponse;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 26/12/2023
 * Time      : 2:45 SA
 * Filename  : PartnerStatistic
 */
public interface PartnerStatisticService {
    @Transactional
    HostStatisticResponse getStatisticOfHost(HostHomeStatisticFilter request, String hostMail);

    @Transactional
    BasePagingData<HostHomeStatisticMonthResponse> getStatisticHomeByMonthAndYearOfHost(HostHomeStatisticMonthFilter request,
                                                                                        Integer pageNumber,
                                                                                        Integer pageSize,
                                                                                        String hostMail);
}
