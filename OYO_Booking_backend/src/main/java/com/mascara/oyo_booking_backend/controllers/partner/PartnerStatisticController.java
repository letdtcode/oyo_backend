package com.mascara.oyo_booking_backend.controllers.partner;

import com.mascara.oyo_booking_backend.dtos.base.BasePagingData;
import com.mascara.oyo_booking_backend.dtos.base.BaseResponse;
import com.mascara.oyo_booking_backend.dtos.statistic.partner.filter.HostHomeStatisticFilter;
import com.mascara.oyo_booking_backend.dtos.statistic.partner.filter.HostHomeStatisticMonthFilter;
import com.mascara.oyo_booking_backend.dtos.statistic.partner.models.HostHomeStatisticMonthResponse;
import com.mascara.oyo_booking_backend.dtos.statistic.partner.models.HostStatisticResponse;
import com.mascara.oyo_booking_backend.services.statistic.partner.PartnerStatisticService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 02/07/2024
 * Time      : 8:54 CH
 * Filename  : PartnerStatistcController
 */
@Tag(name = "Partner Manage", description = "Partner Manage APIs")
@RestController
@RequestMapping("/api/v1/partner/statistic")
@RequiredArgsConstructor
@Validated
public class PartnerStatisticController {

    private final PartnerStatisticService partnerStatisticService;

    @GetMapping("")
    public ResponseEntity<?> getStatisticOfHost(@ParameterObject @Valid HostHomeStatisticFilter request) {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        String hostMail = principal.getName();
        HostStatisticResponse response = partnerStatisticService.getStatisticOfHost(request, hostMail);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @GetMapping("/month")
    public ResponseEntity<?> getStatisticHomeByMonthAndYearOfHost(@ParameterObject @Valid HostHomeStatisticMonthFilter request,
                                                                  @RequestParam("pageNumber")
                                                                  @NotNull(message = "Page number must not be null")
                                                                  @Min(value = 0, message = "Page number must greater or equal 0")
                                                                  Integer pageNumber,
                                                                  @RequestParam("pageSize")
                                                                  @NotNull(message = "Page size must not be null")
                                                                  @Min(value = 1, message = "Page size must greater or equal 1")
                                                                  Integer pageSize) {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        String hostMail = principal.getName();
        BasePagingData<HostHomeStatisticMonthResponse> response =
                partnerStatisticService.getStatisticHomeByMonthAndYearOfHost(request,
                        pageNumber,
                        pageSize,
                        hostMail);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }
}
