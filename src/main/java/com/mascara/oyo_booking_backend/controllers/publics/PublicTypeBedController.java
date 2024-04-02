package com.mascara.oyo_booking_backend.controllers.publics;

import com.mascara.oyo_booking_backend.dtos.base.BaseResponse;
import com.mascara.oyo_booking_backend.dtos.base.BasePagingData;
import com.mascara.oyo_booking_backend.dtos.type_bed.response.GetTypeBedResponse;
import com.mascara.oyo_booking_backend.enums.CommonStatusEnum;
import com.mascara.oyo_booking_backend.services.type_bed.TypeBedService;
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

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 07/12/2023
 * Time      : 8:32 CH
 * Filename  : PublicTypeBedController
 */
@Tag(name = "Public Type Bed Data", description = "Get Data Type Bed with Info")
@RestController
@RequestMapping("/api/v1/public/type-beds")
@RequiredArgsConstructor
public class PublicTypeBedController {

    @Autowired
    private TypeBedService typeBedService;

    @Operation(summary = "Get all data type bed", description = "Public Api get all data province")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BaseResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @GetMapping("")
    public ResponseEntity<?> getAllTypeBed(@RequestParam("pageNumber")
                                           @NotNull(message = "Page number must not be null")
                                           @Min(value = 0, message = "Page number must greater or equal 0")
                                           Integer pageNumber,
                                           @RequestParam("pageSize")
                                           @NotNull(message = "Page size must not be null")
                                           @Min(value = 1, message = "Page size must greater or equal 1")
                                           Integer pageSize) {
        String sortType = "DESC";
        String field = "created_date";
        String status = CommonStatusEnum.ENABLE.toString();
        BasePagingData<GetTypeBedResponse> response = typeBedService.getAllTypeBedWithPagingByStatus(status, pageNumber, pageSize, sortType, field);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }
}
