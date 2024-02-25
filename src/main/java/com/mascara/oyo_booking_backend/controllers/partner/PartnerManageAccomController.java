package com.mascara.oyo_booking_backend.controllers.partner;

import com.mascara.oyo_booking_backend.dtos.request.accom_place.*;
import com.mascara.oyo_booking_backend.dtos.response.BaseResponse;
import com.mascara.oyo_booking_backend.dtos.response.accommodation.GetAccomPlaceDetailResponse;
import com.mascara.oyo_booking_backend.dtos.response.accommodation.GetAccomPlaceResponse;
import com.mascara.oyo_booking_backend.dtos.response.paging.BasePagingData;
import com.mascara.oyo_booking_backend.services.accom_place.AccomPlaceService;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 25/10/2023
 * Time      : 8:28 CH
 * Filename  : PartnerManageController
 */
@Tag(name = "Partner Manage", description = "Partner Manage APIs")
@RestController
@RequestMapping("/api/v1/partner/accoms")
@RequiredArgsConstructor
public class PartnerManageAccomController {

    @Autowired
    private AccomPlaceService accomPlaceService;

    @Operation(summary = "Add Accom Place For Rent", description = "Partner Api for add accom place")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BaseResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PostMapping("/create")
    @PreAuthorize("hasRole('PARTNER')")
    public ResponseEntity<?> addAccomPlace(@RequestBody @Valid AddAccomPlaceRequest addAccomPlaceRequest) {
        GetAccomPlaceResponse response = accomPlaceService.addAccomPlace(addAccomPlaceRequest);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @Operation(summary = "Add Image Accom Place For Rent", description = "Partner Api for add image accom place")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BaseResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PostMapping("/{id}/images/create")
    @PreAuthorize("hasRole('PARTNER')")
    public ResponseEntity<?> addImageAccomPlace(@RequestParam("files") List<MultipartFile> files,
                                                @PathVariable("id") Long id) {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        String hostMail = principal.getName();
        GetAccomPlaceResponse response = accomPlaceService.addImageAccomPlace(files, id, hostMail);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @Operation(summary = "Add Accom Place For Rent", description = "Partner Api for add accom place")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BaseResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @GetMapping("/pages")
    @PreAuthorize("hasRole('PARTNER')")
    public ResponseEntity<?> getListAccomPlaceOfPartner(@RequestParam("pageNumber")
                                                        @NotNull(message = "Page number must not be null")
                                                        @Min(value = 0, message = "Page number must greater or equal 0")
                                                        Integer pageNumber,
                                                        @RequestParam("pageSize")
                                                        @NotNull(message = "Page size must not be null")
                                                        @Min(value = 1, message = "Page size must greater or equal 1")
                                                        Integer pageSize) {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        String hostMail = principal.getName();
        String sortType = "DESC";
        String field = "created_date";
        BasePagingData<GetAccomPlaceResponse> response = accomPlaceService.getListAccomPlaceOfPartner(hostMail, pageNumber, pageSize, sortType, field);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @Operation(summary = "Update title accom place", description = "Update title accom place")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BaseResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PutMapping("/title")
    @PreAuthorize("hasRole('PARTNER')")
    public ResponseEntity<?> updateTitleAccom(@RequestBody @Valid UpdateTitleAccomRequest request,
                                              @RequestParam("accomId") Long accomId) {
        GetAccomPlaceDetailResponse response = accomPlaceService.updateTitleAccom(request, accomId);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @Operation(summary = "Update facility accom place", description = "Update facility accom place")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BaseResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PutMapping("/facility")
    @PreAuthorize("hasRole('PARTNER')")
    public ResponseEntity<?> updateFacilityAccom(@RequestBody @Valid UpdateFacilityAccomRequest request,
                                                 @RequestParam("accomId") Long accomId) {
        GetAccomPlaceDetailResponse response = accomPlaceService.updateFacilityAccom(request, accomId);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @Operation(summary = "Update room accom place", description = "Update facility accom place")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BaseResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PutMapping("/room")
    @PreAuthorize("hasRole('PARTNER')")
    public ResponseEntity<?> updateRoomAccom(@RequestBody @Valid UpdateRoomAccomRequest request,
                                             @RequestParam("accomId") Long accomId) {
        GetAccomPlaceDetailResponse response = accomPlaceService.updateRoomAccom(request, accomId);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @Operation(summary = "Update image accom place", description = "Update facility accom place")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BaseResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PutMapping("/images")
    @PreAuthorize("hasRole('PARTNER')")
    public ResponseEntity<?> updateImageAccom(@RequestBody @Valid UpdateImageAccomRequest request,
                                              @RequestParam("accomId") Long accomId) {
        GetAccomPlaceDetailResponse response = accomPlaceService.updateImageAccom(request, accomId);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @Operation(summary = "Update video accom place", description = "Update video accom place")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BaseResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PutMapping("/video")
    @PreAuthorize("hasRole('PARTNER')")
    public ResponseEntity<?> updateVideoAccom(@RequestBody @Valid UpdateVideoAccomRequest request,
                                              @RequestParam("accomId") Long accomId) {
        GetAccomPlaceDetailResponse response = accomPlaceService.updateVideoAccom(request, accomId);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @Operation(summary = "Update address accom place", description = "Update address accom place")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BaseResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PutMapping("/address")
    @PreAuthorize("hasRole('PARTNER')")
    public ResponseEntity<?> updateAddressAccom(@RequestBody @Valid UpdateAddressAccomRequest request,
                                                @RequestParam("accomId") Long accomId) {
        GetAccomPlaceDetailResponse response = accomPlaceService.updateAddressAccom(request, accomId);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @Operation(summary = "Update surcharge accom place", description = "Update surcharge accom place")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BaseResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PutMapping("/surcharge")
    @PreAuthorize("hasRole('PARTNER')")
    public ResponseEntity<?> updateSurchargeAccom(@RequestBody @Valid UpdateSurchargeAccomRequest request,
                                                  @RequestParam("accomId") Long accomId) {
        GetAccomPlaceDetailResponse response = accomPlaceService.updateSurchargeAccom(request, accomId);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @Operation(summary = "Update price per night accom place", description = "Update price per night accom place")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BaseResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PutMapping("/change-price")
    @PreAuthorize("hasRole('PARTNER')")
    public ResponseEntity<?> changePriceAccom(@RequestParam("pricePerNight") Double pricePerNight,
                                              @RequestParam("accomId") Long accomId) {
        GetAccomPlaceDetailResponse response = accomPlaceService.changePriceAccom(pricePerNight, accomId);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @Operation(summary = "Update discount accom place", description = "Update discount accom place")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BaseResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PutMapping("/discount")
    @PreAuthorize("hasRole('PARTNER')")
    public ResponseEntity<?> updateDiscountAccom(@RequestParam("discount") Double discount,
                                                 @RequestParam("accomId") Long accomId) {
        GetAccomPlaceDetailResponse response = accomPlaceService.updateDiscountAccom(discount, accomId);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }
}
