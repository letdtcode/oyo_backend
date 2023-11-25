package com.mascara.oyo_booking_backend.controllers.admin;

import com.mascara.oyo_booking_backend.dtos.request.facility.AddFacilityRequest;
import com.mascara.oyo_booking_backend.dtos.request.facility.UpdateFacilityRequest;
import com.mascara.oyo_booking_backend.dtos.response.BaseResponse;
import com.mascara.oyo_booking_backend.services.facility.FacilityService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
public class CmsFacilityController {
    @Autowired
    private FacilityService facilityService;

    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addFacility(@RequestBody @Valid AddFacilityRequest request) {
        String response = facilityService.addFacility(request);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateFacility(@RequestBody UpdateFacilityRequest request,
                                            @PathVariable("id") Long id) {
        String response = facilityService.updateFacility(request, id);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @PutMapping("/{id}/{status}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> changeStatusTypeBed(@PathVariable("id") Long id, @RequestParam("status") String status) {
        String messageReponse = facilityService.changeStatusFacility(id, status);
        return ResponseEntity.ok(new BaseResponse(true, 200, messageReponse));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteTypeBed(@PathVariable("id") Long id) {
        String messageReponse = facilityService.deletedFacility(id);
        return ResponseEntity.ok(new BaseResponse(true, 200, messageReponse));
    }
}
