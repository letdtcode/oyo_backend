package com.mascara.oyo_booking_backend.controllers.publics;

import com.mascara.oyo_booking_backend.dtos.request.accommodation.GetAccomPlaceFilterRequest;
import com.mascara.oyo_booking_backend.dtos.response.BaseResponse;
import com.mascara.oyo_booking_backend.dtos.response.general.MessageResponse;
import com.mascara.oyo_booking_backend.services.accom_category.AccomCategoryService;
import com.mascara.oyo_booking_backend.services.accom_place.AccomPlaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 27/10/2023
 * Time      : 4:48 CH
 * Filename  : PublicAccomPlaceController
 */
@Tag(name = "Public AccomPlace Category Data", description = "Get Data Accom Category with Info")
@RestController
@RequestMapping("/api/v1/public/accom")
@RequiredArgsConstructor
public class PublicAccomPlaceController {

    @Autowired
    private AccomCategoryService accomCategoryService;

    @Autowired
    private AccomPlaceService accomPlaceService;

    @Operation(summary = "Get all data province", description = "Public Api get all data province")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = MessageResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @GetMapping("/cate-info")
    public ResponseEntity<?> getAllAccomCategoryInfo() {
        return ResponseEntity.ok(new BaseResponse<>(accomCategoryService.getAllAccomCategory()));
    }

    @Operation(summary = "Filter Accom Place", description = "Public Api filter accom place")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = MessageResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @GetMapping("/filter")
    public ResponseEntity<?> getAllAccomCategoryInfo(@ParameterObject @Valid GetAccomPlaceFilterRequest filter,
                                                     @RequestParam(value = "pageNum", defaultValue = "0") Integer pageNum,
                                                     @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize) {
        return ResponseEntity.ok(new BaseResponse<>(accomPlaceService.getAccomPlaceFilterWithPaging(filter, pageNum, pageSize)));
    }
}
