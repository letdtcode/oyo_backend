package com.mascara.oyo_booking_backend.controllers.admin;

import com.mascara.oyo_booking_backend.dtos.BaseMessageData;
import com.mascara.oyo_booking_backend.dtos.request.facility_category.AddFacilityCategoryRequest;
import com.mascara.oyo_booking_backend.dtos.request.facility_category.UpdateFacilityCategoryRequest;
import com.mascara.oyo_booking_backend.dtos.response.BaseResponse;
import com.mascara.oyo_booking_backend.services.facility_category.FacilityCategoryService;
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
 * Filename  : CmsTypeFacilityController
 */
@Tag(name = "Cms Facility Category", description = "Cms Facility Category APIs")
@RestController
@RequestMapping("/api/v1/cms/facility-categories")
@RequiredArgsConstructor
@Validated
public class CmsFacilityCategoryController {

    @Autowired
    private FacilityCategoryService facilityCategoryService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addFacilityCategory(@RequestBody @Valid AddFacilityCategoryRequest request) {
        BaseMessageData response = facilityCategoryService.addFacilityCategory(request);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @PutMapping("/{id}/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateFacilityCategory(@RequestBody @Valid UpdateFacilityCategoryRequest request,
                                                    @PathVariable("id") @NotNull Long id) {
        BaseMessageData response = facilityCategoryService.updateFacilityCategory(request, id);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @PutMapping("/{id}/change-status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> changeStatusFacilityCategory(@PathVariable("id") @NotNull Long id,
                                                          @RequestParam("status") @NotBlank @Status String status) {
        BaseMessageData messageReponse = facilityCategoryService.changeStatusFacilityCategory(id, status);
        return ResponseEntity.ok(new BaseResponse(true, 200, messageReponse));
    }

    @DeleteMapping("/{id}/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteFacilityCategory(@PathVariable("id") @NotNull Long id) {
        BaseMessageData messageReponse = facilityCategoryService.deletedFacilityCategory(id);
        return ResponseEntity.ok(new BaseResponse(true, 200, messageReponse));
    }
}
