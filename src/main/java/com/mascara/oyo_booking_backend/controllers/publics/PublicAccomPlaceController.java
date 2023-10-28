package com.mascara.oyo_booking_backend.controllers.publics;

import com.mascara.oyo_booking_backend.dtos.response.BaseResponse;
import com.mascara.oyo_booking_backend.dtos.response.accom_category.GetAllAccomCategoryResponse;
import com.mascara.oyo_booking_backend.dtos.response.general.MessageResponse;
import com.mascara.oyo_booking_backend.services.accom_category.AccomCategoryService;
import com.mascara.oyo_booking_backend.services.province.ProvinceService;
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

    @Operation(summary = "Get all data province", description = "Public Api get all data province")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = MessageResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @GetMapping("/cate-info")
    public ResponseEntity<?> getAllAccomCategoryInfo() {
        return ResponseEntity.ok(new BaseResponse<>(accomCategoryService.getAllAccomCategory()));
    }
}
