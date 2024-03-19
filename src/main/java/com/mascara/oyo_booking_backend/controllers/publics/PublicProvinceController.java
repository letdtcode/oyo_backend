package com.mascara.oyo_booking_backend.controllers.publics;

import com.mascara.oyo_booking_backend.dtos.response.BaseResponse;
import com.mascara.oyo_booking_backend.dtos.response.paging.BasePagingData;
import com.mascara.oyo_booking_backend.entities.address.Province;
import com.mascara.oyo_booking_backend.services.province.ProvinceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 17/10/2023
 * Time      : 10:03 CH
 * Filename  : PublicProvinceController
 */
@Tag(name = "Public Province Data", description = "Get Data Province with Info of district, ward")
@RestController
@RequestMapping("/api/v1/public/provinces")
@RequiredArgsConstructor
public class PublicProvinceController {

    @Autowired
    private ProvinceService provinceService;

    @Operation(summary = "Get all data province", description = "Public Api get all data province")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BaseResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @GetMapping("/getall-details")
    public ResponseEntity<?> getAllProvinceDetails() {
        List<Province> response = provinceService.getAllProvinceDetails();
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @Operation(summary = "Get top province by field", description = "Public Api for get top province by field")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BaseResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @GetMapping("/top")
    public ResponseEntity<?> getTopProvinceByBooking(@RequestParam("pageNumber")
                                                     @NotNull(message = "Page number must not be null")
                                                     @Min(value = 0, message = "Page number must greater or equal 0")
                                                     Integer pageNumber,
                                                     @RequestParam("pageSize")
                                                     @NotNull(message = "Page size must not be null")
                                                     @Min(value = 1, message = "Page size must greater or equal 1")
                                                     Integer pageSize) {
        String sortType = "DESC";
        String fieldSort = "num_booking";
        BasePagingData response = provinceService.getTopProvinceByField(pageNumber, pageSize, sortType, fieldSort);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }
}
