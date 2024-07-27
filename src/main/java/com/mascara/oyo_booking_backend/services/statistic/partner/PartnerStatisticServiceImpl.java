package com.mascara.oyo_booking_backend.services.statistic.partner;

import com.mascara.oyo_booking_backend.dtos.base.BasePagingData;
import com.mascara.oyo_booking_backend.dtos.statistic.common.RevenueStatistic;
import com.mascara.oyo_booking_backend.dtos.statistic.partner.filter.HostHomeStatisticFilter;
import com.mascara.oyo_booking_backend.dtos.statistic.partner.filter.HostHomeStatisticMonthFilter;
import com.mascara.oyo_booking_backend.dtos.statistic.partner.models.HomeBookingStatistic;
import com.mascara.oyo_booking_backend.dtos.statistic.partner.models.HostHomeStatisticMonthResponse;
import com.mascara.oyo_booking_backend.dtos.statistic.partner.models.HostStatisticResponse;
import com.mascara.oyo_booking_backend.dtos.statistic.partner.projections.HostHomeStatisticProjection;
import com.mascara.oyo_booking_backend.common.enums.booking.BookingStatusEnum;
import com.mascara.oyo_booking_backend.common.enums.MonthEnum;
import com.mascara.oyo_booking_backend.repositories.AccomPlaceRepository;
import com.mascara.oyo_booking_backend.repositories.BookingRepository;
import com.mascara.oyo_booking_backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 26/12/2023
 * Time      : 2:45 SA
 * Filename  : PartnerStatisticImpl
 */
@Service
@RequiredArgsConstructor
public class PartnerStatisticServiceImpl implements PartnerStatisticService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final AccomPlaceRepository accomPlaceRepository;

    @Override
    @Transactional
    public HostStatisticResponse getStatisticOfHost(HostHomeStatisticFilter request, String hostMail) {
        Long hostId = userRepository.findByMail(hostMail).get().getId();

        Long totalBookingOfHost = bookingRepository.countNumberBookingOfHost(request.getYear(),
                hostId,
                null);
        Long totalBookingFinishOfHost = bookingRepository.countNumberBookingOfHost(request.getYear(),
                hostId,
                BookingStatusEnum.CHECK_OUT.name());
        List<HomeBookingStatistic> homeBookingStatisticOfHost = bookingRepository
                .getHomeBookingStatisticOfHost(request.getYear(), hostId
                ).stream().map(projection -> HomeBookingStatistic.builder()
                        .accomId(projection.getAccomId())
                        .accomName(projection.getAccomName())
                        .numberOfBooking(projection.getNumberOfBooking())
                        .build()
                ).collect(Collectors.toList());
        List<RevenueStatistic> revenueStatisticOfHost = getRevenueOfHostWithYear(hostId, request.getYear());
        return HostStatisticResponse.builder()
                .totalNumberOfBooking(totalBookingOfHost)
                .totalNumberOfBookingFinish(totalBookingFinishOfHost)
                .homeStatistic(homeBookingStatisticOfHost)
                .revenueStatistics(revenueStatisticOfHost)
                .build();
    }

    private List<RevenueStatistic> getRevenueOfHostWithYear(Long hostId, Integer year) {
        List<RevenueStatistic> result = new LinkedList<>();
        for (MonthEnum item : MonthEnum.values()) {
            Double revenue = bookingRepository.getRevenueOfHostWithYearAndMonth(year,
                    item.getMonthValue(),
                    BookingStatusEnum.CHECK_OUT.name(),
                    hostId);
            result.add(RevenueStatistic.builder()
                    .month(item.getMonthName())
                    .amount(revenue).build());
        }
        return result;
    }

    @Override
    @Transactional
    public BasePagingData<HostHomeStatisticMonthResponse> getStatisticHomeByMonthAndYearOfHost(HostHomeStatisticMonthFilter request,
                                                                                               Integer pageNumber,
                                                                                               Integer pageSize,
                                                                                               String hostMail) {
        Long hostId = userRepository.findByMail(hostMail).get().getId();
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<HostHomeStatisticProjection> projections = accomPlaceRepository.getStatisticHomeByMonthAndYearOfHost(hostId,
                request.getMonth(),
                request.getYear(),
                pageable);
        List<HostHomeStatisticMonthResponse> responsesList = projections.stream()
                .map(item -> HostHomeStatisticMonthResponse.builder()
                        .accomId(item.getAccomId())
                        .accomName(item.getAccomName())
                        .numberOfView(item.getNumberOfView())
                        .numberOfBooking(item.getNumberOfBooking())
                        .revenue(item.getRevenue())
                        .numberOfReview(item.getNumberOfReview())
                        .averageRate(item.getAverageRate())
                        .reservationRate(item.getReservationRate())
                        .build()).collect(Collectors.toList());
        return new BasePagingData<>(responsesList,
                projections.getNumber(),
                projections.getSize(),
                projections.getTotalElements());
    }
}
