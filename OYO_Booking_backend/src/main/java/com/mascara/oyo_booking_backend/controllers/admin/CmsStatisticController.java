package com.mascara.oyo_booking_backend.controllers.admin;

import com.mascara.oyo_booking_backend.dtos.base.BasePagingData;
import com.mascara.oyo_booking_backend.dtos.base.BaseResponse;
import com.mascara.oyo_booking_backend.dtos.statistic.admin.filter.AdminHomeChartFilter;
import com.mascara.oyo_booking_backend.dtos.statistic.admin.filter.AdminHomeStatisticFilter;
import com.mascara.oyo_booking_backend.dtos.statistic.admin.filter.AdminStatisticDateFilter;
import com.mascara.oyo_booking_backend.dtos.statistic.admin.models.*;
import com.mascara.oyo_booking_backend.services.statistic.admin.AdminStatisticService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 26/06/2024
 * Time      : 6:29 CH
 * Filename  : CmsStatisticController
 */

@Tag(name = "Cms Accom Place", description = "Cms Accom Place APIs")
@RestController
@RequestMapping("/api/v1/cms/statistic")
@RequiredArgsConstructor
@Validated
public class CmsStatisticController {

    private final AdminStatisticService adminStatisticService;

    @GetMapping("")
    public ResponseEntity<?> getStatisticOfAdmin(@ParameterObject @Valid AdminHomeStatisticFilter request) {
        AdminStatisticResponse response = adminStatisticService.getStatisticOfAdmin(request);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @GetMapping("/guest")
    public ResponseEntity<?> getStatisticForGuestOfAdmin(@ParameterObject @Valid AdminStatisticDateFilter request) {
        BasePagingData<AdminStatisticForGuestResponse> response = adminStatisticService.getStatisticForGuestOfAdmin(request, 0, 200);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @GetMapping("/host")
    public ResponseEntity<?> getStatisticForHostOfAdmin(@ParameterObject @Valid AdminStatisticDateFilter request) {
        BasePagingData<AdminStatisticForHostResponse> response = adminStatisticService.getStatisticForHostOfAdmin(request, 0, 200);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @GetMapping("/accom-place")
    public ResponseEntity<?> getStatisticForAccomPlaceOfAdmin(@ParameterObject @Valid AdminStatisticDateFilter request) {
        BasePagingData<AdminStatisticForAccomPlaceResponse> response = adminStatisticService.getStatisticForAccomPlaceOfAdmin(request, 0, 200);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @GetMapping("/transaction")
    public ResponseEntity<?> getStatisticForTransactionOfAdmin(@ParameterObject @Valid AdminStatisticDateFilter request) {
        BasePagingData<AdminStatisticTransactionResponse> response = adminStatisticService.getStatisticForTransactionOfAdmin(request, 0, 200);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @GetMapping("/chart")
    public ResponseEntity<?> getStatisticChart(@ParameterObject @Valid AdminHomeChartFilter request) {
        AdminStatisticChartResponse response = adminStatisticService.getStatistiChartAdmin(request);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }
}
