package com.mascara.oyo_booking_backend.controllers.admin;

import com.mascara.oyo_booking_backend.dtos.request.facility_category.AddFacilityCategoryRequest;
import com.mascara.oyo_booking_backend.dtos.request.facility_category.UpdateFacilityCategoryRequest;
import com.mascara.oyo_booking_backend.dtos.response.BaseResponse;
import com.mascara.oyo_booking_backend.services.facility_category.FacilityCategoryService;
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
 * Filename  : CmsTypeFacilityController
 */
@Tag(name = "Cms Facility Category", description = "Cms Facility Category APIs")
@RestController
@RequestMapping("/api/v1/cms/facility-categories")
@RequiredArgsConstructor
public class CmsFacilityCategoryController {

    @Autowired
    private FacilityCategoryService facilityCategoryService;

    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addFacilityCategory(@RequestBody @Valid AddFacilityCategoryRequest request) {
        String response = facilityCategoryService.addFacilityCategory(request);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateFacilityCategory(@RequestBody UpdateFacilityCategoryRequest request,
                                                    @PathVariable("id") Long id) {
        String response = facilityCategoryService.updateFacilityCategory(request, id);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @PutMapping("/{id}/{status}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> changeStatusTypeBed(@PathVariable("id") Long id, @RequestParam("status") String status) {
        String messageReponse = facilityCategoryService.changeStatusFacilityCategory(id, status);
        return ResponseEntity.ok(new BaseResponse(true, 200, messageReponse));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteTypeBed(@PathVariable("id") Long id) {
        String messageReponse = facilityCategoryService.deletedFacilityCategory(id);
        return ResponseEntity.ok(new BaseResponse(true, 200, messageReponse));
    }
}
