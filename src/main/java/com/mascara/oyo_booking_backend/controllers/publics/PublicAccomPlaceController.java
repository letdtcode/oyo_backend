package com.mascara.oyo_booking_backend.controllers.publics;

import com.mascara.oyo_booking_backend.dtos.accom_place.request.GetAccomPlaceFilterRequest;
import com.mascara.oyo_booking_backend.dtos.booking.request.CheckBookingRequest;
import com.mascara.oyo_booking_backend.dtos.base.BaseResponse;
import com.mascara.oyo_booking_backend.dtos.accom_category.response.GetAccomCategoryResponse;
import com.mascara.oyo_booking_backend.dtos.accom_place.response.GetAccomPlaceDetailResponse;
import com.mascara.oyo_booking_backend.dtos.accom_place.response.GetAccomPlaceResponse;
import com.mascara.oyo_booking_backend.dtos.booking.response.CheckBookingResponse;
import com.mascara.oyo_booking_backend.dtos.base.BasePagingData;
import com.mascara.oyo_booking_backend.dtos.review.response.GetReviewResponse;
import com.mascara.oyo_booking_backend.services.accom_category.AccomCategoryService;
import com.mascara.oyo_booking_backend.services.accom_place.AccomPlaceService;
import com.mascara.oyo_booking_backend.services.booking.BookingService;
import com.mascara.oyo_booking_backend.services.review.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/api/v1/public/accoms")
@RequiredArgsConstructor
@Slf4j
public class PublicAccomPlaceController {

    @Autowired
    private AccomCategoryService accomCategoryService;

    @Autowired
    private AccomPlaceService accomPlaceService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private BookingService bookingService;

    @Operation(summary = "Get all data province", description = "Public Api get all data province")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BaseResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @GetMapping("/cate-info")
    public ResponseEntity<?> getAllAccomCategoryInfo() {
        List<GetAccomCategoryResponse> response = accomCategoryService.getAllAccomCategory();
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @Operation(summary = "Filter Accom Place", description = "Public Api filter accom place")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BaseResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @GetMapping("/filters")
    public ResponseEntity<?> getAccomPlaceFilterWithPaging(@ParameterObject @Valid GetAccomPlaceFilterRequest filter,
                                                           @RequestParam(value = "pageNum", defaultValue = "0") Integer pageNum,
                                                           @RequestParam(value = "pageSize", defaultValue = "0") Integer pageSize) {
        String sortType = "DESC";
        String field = "created_date";
        log.error(filter.toString());
        BasePagingData<GetAccomPlaceResponse> response = accomPlaceService.getAccomPlaceFilterWithPaging(filter, pageNum, pageSize, sortType, field);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @Operation(summary = "Search Accom Place", description = "Public Api search accom place")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BaseResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @GetMapping("/search")
    public ResponseEntity<?> getAccomPlaceFilterWithPaging(@RequestParam("keyword") String keyword,
                                                           @RequestParam(value = "pageNum", defaultValue = "0") Integer pageNum,
                                                           @RequestParam(value = "pageSize", defaultValue = "0") Integer pageSize) {
        String sortType = "DESC";
        String field = "num_view";
        log.error("keyword: " + keyword);
        BasePagingData<GetAccomPlaceResponse> response = accomPlaceService.getFilterByKeyWord(keyword, pageNum, pageSize, sortType, field);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @Operation(summary = "Info Detail Accom Place", description = "Public Api detail of accom place")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BaseResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @GetMapping("/{id}/detail")
    public ResponseEntity<?> getInfoAccomPlaceDetails(@PathVariable("id") Long id) {
        GetAccomPlaceDetailResponse response = accomPlaceService.getAccomPlaceApprovedDetails(id);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @Operation(summary = "Reviews Accom Place", description = "Public Api reviews of accom place")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BaseResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @GetMapping("/{id}/reviews")
    public ResponseEntity<?> getReviewsAccomPlaceDetails(@PathVariable("id") Long id) {
        List<GetReviewResponse> response = reviewService.getReviewListOfAccomPlace(id);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @Operation(summary = "Info top accom place by views", description = "Public Api get info top accom place by views")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    content = {@Content(schema = @Schema(implementation = BaseResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
                    content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500",
                    content = {@Content(schema = @Schema())})})
    @GetMapping("/top")
    public ResponseEntity<?> getTopAccomPlaceByViews(@RequestParam("pageNumber")
                                                     @NotNull(message = "Page number must not be null")
                                                     @Min(value = 0, message = "Page number must greater or equal 0")
                                                     Integer pageNumber,
                                                     @RequestParam("pageSize")
                                                     @NotNull(message = "Page size must not be null")
                                                     @Min(value = 1, message = "Page size must greater or equal 1")
                                                     Integer pageSize) {
        String sortType = "DESC";
        String fieldSort = "num_view";
        BasePagingData response = accomPlaceService.getTopAccomPlaceByField(pageNumber, pageSize, sortType, fieldSort);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @Operation(summary = "Check accom place is ready for booking", description = "Public Api for check accom place is ready for booking")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    content = {@Content(schema = @Schema(implementation = BaseResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "410",
                    content = {@Content(schema = @Schema(implementation = BaseResponse.class), mediaType = "application/json")})})
    @PostMapping("/check-booking")
    public ResponseEntity<?> checkBookingToGetPrice(@RequestBody @Valid CheckBookingRequest request) {
        CheckBookingResponse checkBookingResponse = bookingService.checkBookingToGetPrice(request);
        if (!checkBookingResponse.isCanBooking()) {
            return ResponseEntity.status(210).body(new BaseResponse<>(true, 210, checkBookingResponse));
        }
        return ResponseEntity.ok(new BaseResponse<>(true, 200, checkBookingResponse));
    }
}
