package com.mascara.oyo_booking_backend.controllers.partner;

import com.mascara.oyo_booking_backend.dtos.accom_place.request.*;
import com.mascara.oyo_booking_backend.dtos.accom_place.response.*;
import com.mascara.oyo_booking_backend.dtos.base.BaseMessageData;
import com.mascara.oyo_booking_backend.dtos.base.BasePagingData;
import com.mascara.oyo_booking_backend.dtos.base.BaseResponse;
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
    @PostMapping("/registration")
    @PreAuthorize("hasRole('PARTNER')")
    public ResponseEntity<?> registrationAccomPlace(@RequestBody @Valid RegisterAccomPlaceRequest request) {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        String mailPartner = principal.getName();
        Long response = accomPlaceService.registerAccomPlace(request.getAccomCateName(), mailPartner);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @Operation(summary = "Add Accom Place For Rent", description = "Partner Api for add accom place")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BaseResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PostMapping("/request-approval")
    @PreAuthorize("hasRole('PARTNER')")
    public ResponseEntity<?> requestApproval(@RequestParam("accomId")
                                             @NotNull(message = "Accom id must not be null")
                                             Long accomId) {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        String mailPartner = principal.getName();
        BaseMessageData response = accomPlaceService.requestApproval(accomId, mailPartner);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @Operation(summary = "Get list accom place approved", description = "Partner Api for get list accom place approved")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BaseResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @GetMapping("/approved")
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
        BasePagingData<GetAccomPlaceResponse> response = accomPlaceService.getListAccomPlaceApprovedOfPartner(hostMail, pageNumber, pageSize, sortType, field);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @Operation(summary = "Get list accom place waiting", description = "Partner Api for get list accom place waiting")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BaseResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @GetMapping("/waiting")
    @PreAuthorize("hasRole('PARTNER')")
    public ResponseEntity<?> getListAccomPlaceWaitingOfPartner() {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        String hostMail = principal.getName();
        String sortType = "DESC";
        String field = "created_date";
        BasePagingData<AccomPlaceGeneralResponse> response = accomPlaceService.getListAccomPlaceWaitingOfPartner(hostMail, 0, 20, sortType, field);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @Operation(summary = "Get list accom place waiting", description = "Partner Api for get list accom place waiting")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BaseResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @GetMapping("/percent-create-accom")
    @PreAuthorize("hasRole('PARTNER')")
    public ResponseEntity<?> getPercentCreateAccom(@RequestParam("accomId") Long accomId) {
        PercentCreateAccomResponse response = accomPlaceService.getPercentCreateAccom(accomId);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @Operation(summary = "Update general info accom place", description = "Update general info accom place")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BaseResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PutMapping("/general-info")
    @PreAuthorize("hasRole('PARTNER')")
    public ResponseEntity<?> updateGeneralInfoAccom(@RequestBody @Valid UpdateGeneralInfoRequest request,
                                                    @RequestParam("accomId") Long accomId) {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        String hostMail = principal.getName();
        accomPlaceService.checkPermission(hostMail, accomId);
        BaseMessageData response = accomPlaceService.updateGeneralInfo(request, accomId);
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
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        String hostMail = principal.getName();
        accomPlaceService.checkPermission(hostMail, accomId);
        BaseMessageData response = accomPlaceService.updateAddress(request, accomId);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @Operation(summary = "Update facilities accom place", description = "Update facilities accom place")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BaseResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PutMapping("/facilities")
    @PreAuthorize("hasRole('PARTNER')")
    public ResponseEntity<?> updateFacilitiesAccom(@RequestBody @Valid UpdateFacilityAccomRequest request,
                                                   @RequestParam("accomId") Long accomId) {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        String hostMail = principal.getName();
        accomPlaceService.checkPermission(hostMail, accomId);
        BaseMessageData response = accomPlaceService.updateFacilities(request, accomId);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @Operation(summary = "Update images accom place", description = "Update images accom place")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BaseResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PutMapping("/gallery")
    @PreAuthorize("hasRole('PARTNER')")
    public ResponseEntity<?> updateImagesAccom(@RequestBody @Valid UpdateGalleryAccomRequest request,
                                               @RequestParam("accomId") Long accomId) {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        String hostMail = principal.getName();
        accomPlaceService.checkPermission(hostMail, accomId);
        BaseMessageData response = accomPlaceService.updateGallery(request, accomId);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @Operation(summary = "Update rooms accom place", description = "Update rooms accom place")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BaseResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PutMapping("/rooms")
    @PreAuthorize("hasRole('PARTNER')")
    public ResponseEntity<?> updateRoomsAccom(@RequestBody @Valid UpdateRoomAccomRequest request,
                                              @RequestParam("accomId") Long accomId) {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        String hostMail = principal.getName();
        accomPlaceService.checkPermission(hostMail, accomId);
        BaseMessageData response = accomPlaceService.updateRooms(request, accomId);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @Operation(summary = "Update policy accom place", description = "Update policy accom place")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BaseResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PutMapping("/policies")
    @PreAuthorize("hasRole('PARTNER')")
    public ResponseEntity<?> updatePoliciesAccom(@RequestBody @Valid UpdatePolicyAccomRequest request,
                                                 @RequestParam("accomId") Long accomId) {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        String hostMail = principal.getName();
        accomPlaceService.checkPermission(hostMail, accomId);
        BaseMessageData response = accomPlaceService.updatePolicy(request, accomId);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @Operation(summary = "Update payment accom place", description = "Update payment accom place")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BaseResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PutMapping("/payment")
    @PreAuthorize("hasRole('PARTNER')")
    public ResponseEntity<?> updatePaymentAccom(@RequestBody @Valid UpdatePaymentAccomRequest request,
                                                @RequestParam("accomId") Long accomId) {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        String hostMail = principal.getName();
        accomPlaceService.checkPermission(hostMail, accomId);
        BaseMessageData response = accomPlaceService.updatePayment(request, accomId);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @Operation(summary = "Update payment accom place", description = "Update payment accom place")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BaseResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @GetMapping("/general-info")
    @PreAuthorize("hasRole('PARTNER')")
    public ResponseEntity<?> getGeneralInfoAccom(@RequestParam("accomId") Long accomId) {
        GetGeneralInfoAccomResponse response = accomPlaceService.getGeneralInfoAccom(accomId);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @Operation(summary = "Update payment accom place", description = "Update payment accom place")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BaseResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @GetMapping("/address")
    @PreAuthorize("hasRole('PARTNER')")
    public ResponseEntity<?> getAddressAccom(@RequestParam("accomId") Long accomId) {
        GetAddressAccomResponse response = accomPlaceService.getAddressAccom(accomId);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @Operation(summary = "Update payment accom place", description = "Update payment accom place")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BaseResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @GetMapping("/facilities")
    @PreAuthorize("hasRole('PARTNER')")
    public ResponseEntity<?> getFacilityAccom(@RequestParam("accomId") Long accomId) {
        GetFacilityAccomResponse response = accomPlaceService.getFacilityAccom(accomId);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @Operation(summary = "Update payment accom place", description = "Update payment accom place")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BaseResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @GetMapping("/gallery")
    @PreAuthorize("hasRole('PARTNER')")
    public ResponseEntity<?> getGalleryAccom(@RequestParam("accomId") Long accomId) {
        GetGalleryAccomResponse response = accomPlaceService.getGalleryAccom(accomId);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @Operation(summary = "Update payment accom place", description = "Update payment accom place")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BaseResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @GetMapping("/room-setting")
    @PreAuthorize("hasRole('PARTNER')")
    public ResponseEntity<?> getRoomSettingAccom(@RequestParam("accomId") Long accomId) {
        GetRoomSettingAccomResponse response = accomPlaceService.getRoomSettingAccom(accomId);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @Operation(summary = "Update payment accom place", description = "Update payment accom place")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BaseResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @GetMapping("/policies")
    @PreAuthorize("hasRole('PARTNER')")
    public ResponseEntity<?> getPolicyAccom(@RequestParam("accomId") Long accomId) {
        GetPolicyAccomResponse response = accomPlaceService.getPolicyAccom(accomId);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @Operation(summary = "Update payment accom place", description = "Update payment accom place")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BaseResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @GetMapping("/payment")
    @PreAuthorize("hasRole('PARTNER')")
    public ResponseEntity<?> getPaymentAccom(@RequestParam("accomId") Long accomId) {
        GetPaymentAccomResponse response = accomPlaceService.getPaymentAccom(accomId);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @Operation(summary = "Update discount accom place", description = "Update discount accom place")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BaseResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PutMapping("/price-custom")
    @PreAuthorize("hasRole('PARTNER')")
    public ResponseEntity<?> updatePriceCustomAccom(@RequestBody @Valid List<UpdatePriceCustomAccomPlaceRequest> request) {
        BaseMessageData response = accomPlaceService.updatePriceCustom(request);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @Operation(summary = "Get list price custom of accom", description = "Partner Api for get list accom place approved")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BaseResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @GetMapping("/price-custom")
    @PreAuthorize("hasRole('PARTNER')")
    public ResponseEntity<?> getListPriceCustomAccom(@RequestParam("pageNumber")
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
        BasePagingData<GetPriceCustomAccomResponse> response = accomPlaceService.getListPriceCustomAccomOfPartner(hostMail, pageNumber, pageSize, sortType, field);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @Operation(summary = "Get list range date booking of accom place", description = "Partner Api for get list accom place approved")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BaseResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @GetMapping("/range-date-booking")
    @PreAuthorize("hasRole('PARTNER')")
    public ResponseEntity<?> getListRangeDateBookingAccom(@RequestParam("pageNumber")
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
        BasePagingData<GetRangeDateBookingAccomResponse> response = accomPlaceService.getListRangeDateAccomOfPartner(hostMail, pageNumber, pageSize, sortType, field);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }
}
