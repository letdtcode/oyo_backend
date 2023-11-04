package com.mascara.oyo_booking_backend.controllers.partner;

import com.mascara.oyo_booking_backend.dtos.request.accom_place.AddAccomPlaceRequest;
import com.mascara.oyo_booking_backend.dtos.response.BaseResponse;
import com.mascara.oyo_booking_backend.dtos.response.general.MessageResponse;
import com.mascara.oyo_booking_backend.services.accom_place.AccomPlaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 25/10/2023
 * Time      : 8:28 CH
 * Filename  : PartnerManageController
 */
@Tag(name = "Partner Manage", description = "Partner Manage APIs")
@RestController
@RequestMapping("/api/v1/partner")
@RequiredArgsConstructor
public class PartnerManageController {

    @Autowired
    private AccomPlaceService accomPlaceService;

    @Operation(summary = "Add Accom Place For Rent", description = "Partner Api for add accom place")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = MessageResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PostMapping("/add-accom-place")
    @PreAuthorize("hasRole('PARTNER')")
    public ResponseEntity<?> addAccomPlace(@RequestBody @Valid AddAccomPlaceRequest addAccomPlaceRequest,
                                           @RequestParam("mail") String mail) {
        return ResponseEntity.ok(new BaseResponse<>(
                accomPlaceService.addAccomPlace(addAccomPlaceRequest, mail)));
    }

}
