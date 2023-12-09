package com.mascara.oyo_booking_backend.controllers.publics;

import com.mascara.oyo_booking_backend.dtos.response.BaseResponse;
import com.mascara.oyo_booking_backend.dtos.response.facility_category.GetFacilityCategorWithFacilityListResponse;
import com.mascara.oyo_booking_backend.services.facility_category.FacilityCategoryService;
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
 * Date      : 31/10/2023
 * Time      : 5:55 CH
 * Filename  : PublicFacilityController
 */
@Tag(name = "Public Facility", description = "Public Facility APIs")
@RestController
@RequestMapping("/api/v1/public/facilities")
@RequiredArgsConstructor
public class PublicFacilityController {
    @Autowired
    private FacilityCategoryService facilityCategoryService;

    @Operation(summary = "Check Mail Exist", description = "Public Api check mail exist for Sign Up")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BaseResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @GetMapping("/get-all")
    public ResponseEntity<?> getAllDataFacility() {
        List<GetFacilityCategorWithFacilityListResponse> response = facilityCategoryService.getAllDataFacility();
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }
}
