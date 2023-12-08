package com.mascara.oyo_booking_backend.controllers.admin;

import com.mascara.oyo_booking_backend.dtos.BaseMessageData;
import com.mascara.oyo_booking_backend.dtos.request.facility.AddFacilityRequest;
import com.mascara.oyo_booking_backend.dtos.request.facility.UpdateFacilityRequest;
import com.mascara.oyo_booking_backend.dtos.response.BaseResponse;
import com.mascara.oyo_booking_backend.dtos.response.facility.GetFacilityResponse;
import com.mascara.oyo_booking_backend.services.facility.FacilityService;
import com.mascara.oyo_booking_backend.utils.validation.Status;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 25/11/2023
 * Time      : 2:25 SA
 * Filename  : CmsFacilityController
 */
@Tag(name = "Cms Facility", description = "Cms Facility APIs")
@RestController
@RequestMapping("/api/v1/cms/facilities")
@RequiredArgsConstructor
@Validated
public class CmsFacilityController {
    @Autowired
    private FacilityService facilityService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addFacility(@RequestBody @Valid AddFacilityRequest request) {
        GetFacilityResponse response = facilityService.addFacility(request);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @PutMapping("/{facilityCode}/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateFacility(@RequestBody @Valid UpdateFacilityRequest request,
                                            @PathVariable("facilityCode") @NotNull String facilityCode) {
        GetFacilityResponse response = facilityService.updateFacility(request, facilityCode);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @PutMapping("/{facilityCode}/change-status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> changeStatusFacility(@PathVariable("facilityCode") @NotNull String facilityCode,
                                                  @RequestParam("status") @NotBlank @Status String status) {
        BaseMessageData messageReponse = facilityService.changeStatusFacility(facilityCode, status);
        return ResponseEntity.ok(new BaseResponse(true, 200, messageReponse));
    }

    @DeleteMapping("/{facilityCode}/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteFacility(@PathVariable("facilityCode") @NotNull String facilityCode) {
        BaseMessageData messageReponse = facilityService.deletedFacility(facilityCode);
        return ResponseEntity.ok(new BaseResponse(true, 200, messageReponse));
    }
}
