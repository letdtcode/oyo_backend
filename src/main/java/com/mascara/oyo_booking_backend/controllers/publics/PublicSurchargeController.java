package com.mascara.oyo_booking_backend.controllers.publics;

import com.mascara.oyo_booking_backend.dtos.response.BaseResponse;
import com.mascara.oyo_booking_backend.dtos.response.surcharge.GetSurchargeCategoryResponse;
import com.mascara.oyo_booking_backend.enums.CommonStatusEnum;
import com.mascara.oyo_booking_backend.services.surcharge.SurchargeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 07/12/2023
 * Time      : 8:46 CH
 * Filename  : PublicSurchargeController
 */
@Tag(name = "Public Surcharge Data", description = "Get Data Accom Category with Info")
@RestController
@RequestMapping("/api/v1/public/surcharges")
@RequiredArgsConstructor
public class PublicSurchargeController {

    @Autowired
    private SurchargeService surchargeService;

    @Operation(summary = "Add Accom Place For Rent", description = "Partner Api for add accom place")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BaseResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @GetMapping("")
    public ResponseEntity<?> getAllSurchargeCategoryByStatus() {
        String status = CommonStatusEnum.ENABLE.toString();
        List<GetSurchargeCategoryResponse> response = surchargeService.getAllSurchargeCategoryByStatus(status);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }
}
