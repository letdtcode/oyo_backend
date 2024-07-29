package com.mascara.oyo_booking_backend.services.statistic.admin;

import com.mascara.oyo_booking_backend.dtos.base.BasePagingData;
import com.mascara.oyo_booking_backend.dtos.statistic.admin.filter.AdminHomeChartFilter;
import com.mascara.oyo_booking_backend.dtos.statistic.admin.filter.AdminHomeStatisticFilter;
import com.mascara.oyo_booking_backend.dtos.statistic.admin.filter.AdminStatisticDateFilter;
import com.mascara.oyo_booking_backend.dtos.statistic.admin.models.*;
import com.mascara.oyo_booking_backend.dtos.statistic.admin.projections.*;
import com.mascara.oyo_booking_backend.dtos.statistic.common.RevenueStatistic;
import com.mascara.oyo_booking_backend.common.enums.statistic.AdminChartTypeEnum;
import com.mascara.oyo_booking_backend.common.enums.booking.BookingStatusEnum;
import com.mascara.oyo_booking_backend.common.enums.statistic.MonthEnum;
import com.mascara.oyo_booking_backend.repositories.AccomPlaceRepository;
import com.mascara.oyo_booking_backend.repositories.BookingRepository;
import com.mascara.oyo_booking_backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 26/12/2023
 * Time      : 2:45 SA
 * Filename  : AdminStatisticImpl
 */
@Service
@RequiredArgsConstructor
public class AdminStatisticServiceImpl implements AdminStatisticService {

    private final UserRepository userRepository;
    private final AccomPlaceRepository accomPlaceRepository;

    private final BookingRepository bookingRepository;

    @Override
    @Transactional
    public AdminStatisticResponse getStatisticOfAdmin(AdminHomeStatisticFilter request) {
        StatisticCountProjection statisticCountProjection = userRepository.getStatisticCountOfAdmin(request.getYear());
        return AdminStatisticResponse.builder()
                .numberOfGuest(statisticCountProjection.getNumberOfGuest())
                .numberOfOwner(statisticCountProjection.getNumberOfOwner())
                .numberOfBooking(statisticCountProjection.getNumberOfBooking())
                .totalOfRevenue(statisticCountProjection.getTotalRevenue())
                .build();
    }

    @Override
    @Transactional
    public BasePagingData<AdminStatisticForGuestResponse> getStatisticForGuestOfAdmin(AdminStatisticDateFilter request, Integer pageNumber, Integer pageSize) {
        Pageable paging = PageRequest.of(pageNumber, pageSize);
        Page<InfoGuestBookingProjection> projections = userRepository
                .getStatisticForGuestOfAdmin(request.getDateStart(), request.getDateEnd(), paging);
        List<AdminStatisticForGuestResponse> responses = projections.getContent().stream()
                .map(item ->
                        AdminStatisticForGuestResponse.builder()
                                .userId(item.getUserId())
                                .fullName(item.getFirstName() + " " + item.getLastName())
                                .email(item.getEmail())
                                .phoneNumber(item.getPhoneNumber())
                                .numberOfBooking(item.getNumberOfBooking())
                                .totalCost(item.getTotalCost())
                                .build()).collect(Collectors.toList());
        return new BasePagingData<>(responses,
                pageNumber,
                pageSize,
                projections.getTotalElements());
    }

    @Override
    @Transactional
    public BasePagingData<AdminStatisticForHostResponse> getStatisticForHostOfAdmin(AdminStatisticDateFilter request, Integer pageNumber, Integer pageSize) {
        Pageable paging = PageRequest.of(pageNumber, pageSize);
        Page<InfoHostStatisticProjection> projections = userRepository
                .getStatisticForHostOfAdmin(request.getDateStart(), request.getDateEnd(), paging);
        List<AdminStatisticForHostResponse> responses = projections.getContent().stream()
                .map(item ->
                        AdminStatisticForHostResponse.builder()
                                .userId(item.getUserId())
                                .fullName(item.getFirstName() + " " + item.getLastName())
                                .email(item.getEmail())
                                .numberOfAccom(item.getNumberOfAccom())
                                .numberOfBooking(item.getNumberOfBooking())
                                .totalRevenue(item.getTotalRevenue())
                                .build()).collect(Collectors.toList());
        return new BasePagingData<>(responses,
                pageNumber,
                pageSize,
                projections.getTotalElements());
    }

    @Override
    @Transactional
    public BasePagingData<AdminStatisticForAccomPlaceResponse> getStatisticForAccomPlaceOfAdmin(AdminStatisticDateFilter request, Integer pageNumber, Integer pageSize) {
        Pageable paging = PageRequest.of(pageNumber, pageSize);
        Page<InfoAccomPlaceStatisticProjection> projections = accomPlaceRepository
                .getStatisticForAccomPlaceOfAdmin(request.getDateStart(), request.getDateEnd(), paging);
        List<AdminStatisticForAccomPlaceResponse> responses = projections.getContent().stream()
                .map(item ->
                        AdminStatisticForAccomPlaceResponse.builder()
                                .accomId(item.getAccomId())
                                .accomName(item.getAccomName())
                                .hostName(item.getHostFirstName() + " " + item.getHostLastName())
                                .numberOfView(item.getNumberOfView())
                                .numberOfBooking(item.getNumberOfBooking())
                                .totalRevenue(item.getTotalRevenue())
                                .numberOfReview(item.getNumberOfReview())
                                .averageGradeRate(item.getAverageGradeRate())
                                .build()).collect(Collectors.toList());
        return new BasePagingData<>(responses,
                pageNumber,
                pageSize,
                projections.getTotalElements());
    }

    @Override
    @Transactional
    public BasePagingData<AdminStatisticTransactionResponse> getStatisticForTransactionOfAdmin(AdminStatisticDateFilter request, Integer pageNumber, Integer pageSize) {
        Pageable paging = PageRequest.of(pageNumber, pageSize);
        Page<InfoTransactionStatisticProjection> projections = bookingRepository
                .getStatisticForTransactionOfAdmin(request.getDateStart(), request.getDateEnd(), paging);
        List<AdminStatisticTransactionResponse> responses = projections.getContent().stream()
                .map(item ->
                        AdminStatisticTransactionResponse.builder()
                                .transactionId(item.getBookingId())
                                .customerName(item.getCustomerName())
                                .ownerName(item.getOwnerFirstName() + " " + item.getOwnerLastName())
                                .totalCost(item.getTotalCost())
                                .adminCost(item.getAdminCost())
                                .homeName(item.getHomeName())
                                .createdDate(item.getCreatedDate())
                                .build()).collect(Collectors.toList());
        return new BasePagingData<>(responses,
                pageNumber,
                pageSize,
                projections.getTotalElements());
    }

    private List<RevenueStatistic> getStatisticBookingChartOfAdmin(Integer year) {
        if (year == null)
            year = LocalDate.now().getYear();

        List<RevenueStatistic> result = new LinkedList<>();
        List<AdminChartProjection> projections = bookingRepository.getStatisticBookingChartOfAdmin(year);

        for (MonthEnum item : MonthEnum.values()) {
            result.add(RevenueStatistic.builder()
                    .amount(projections.get(item.getMonthValue() - 1).getValue())
                    .month(item.getMonthName())
                    .build());
        }
        return result;
    }

    private List<RevenueStatistic> getStatisticRevenueChartOfAdmin(Integer year) {
        if (year == null)
            year = LocalDate.now().getYear();

        List<RevenueStatistic> result = new LinkedList<>();
        List<AdminChartProjection> projections = bookingRepository.getStatisticRevenueChartOfAdmin(year,
                BookingStatusEnum.CHECK_OUT.name());
        for (MonthEnum item : MonthEnum.values()) {
            result.add(RevenueStatistic.builder()
                    .amount(projections.get(item.getMonthValue() - 1).getValue())
                    .month(item.getMonthName())
                    .build());
        }
        return result;
    }

    @Override
    @Transactional
    public AdminStatisticChartResponse getStatistiChartAdmin(AdminHomeChartFilter filter) {
        if (filter.getType().equals(AdminChartTypeEnum.BOOKING))
            return AdminStatisticChartResponse.builder()
                    .revenueStatistics(getStatisticBookingChartOfAdmin(filter.getYear()))
                    .build();
        return AdminStatisticChartResponse.builder()
                .revenueStatistics(getStatisticRevenueChartOfAdmin(filter.getYear())).build();
    }
}
