package com.mascara.oyo_booking_backend.controllers.admin;

import com.mascara.oyo_booking_backend.dtos.base.BaseMessageData;
import com.mascara.oyo_booking_backend.dtos.facility_category.request.AddFacilityCategoryRequest;
import com.mascara.oyo_booking_backend.dtos.facility_category.request.UpdateFacilityCategoryRequest;
import com.mascara.oyo_booking_backend.dtos.base.BaseResponse;
import com.mascara.oyo_booking_backend.dtos.facility_category.response.GetFacilityCategoryResponse;
import com.mascara.oyo_booking_backend.dtos.base.BasePagingData;
import com.mascara.oyo_booking_backend.services.facility_category.FacilityCategoryService;
import com.mascara.oyo_booking_backend.utils.validation.Status;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
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

    @GetMapping("/pages")
    public ResponseEntity<?> getAllFacilityCategoryWithPaging(@RequestParam("pageNumber")
                                                        @NotNull(message = "Page number must not be null")
                                                        @Min(value = 0, message = "Page number must greater or equal 0")
                                                        Integer pageNumber,
                                                        @RequestParam("pageSize")
                                                        @NotNull(message = "Page size must not be null")
                                                        @Min(value = 1, message = "Page size must greater or equal 1")
                                                        Integer pageSize) {
        String sortType = "DESC";
        String field = "created_date";
        BasePagingData<GetFacilityCategoryResponse> response = facilityCategoryService.getAllFacilityCategoryWithPaging(pageNumber, pageSize, sortType, field);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @PostMapping("/create")
    public ResponseEntity<?> addFacilityCategory(@RequestBody @Valid AddFacilityCategoryRequest request) {
        GetFacilityCategoryResponse response = facilityCategoryService.addFacilityCategory(request);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<?> updateFacilityCategory(@RequestBody @Valid UpdateFacilityCategoryRequest request,
                                                    @PathVariable("id") @NotNull Long id) {
        GetFacilityCategoryResponse response = facilityCategoryService.updateFacilityCategory(request, id);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @PutMapping("/{id}/change-status")
    public ResponseEntity<?> changeStatusFacilityCategory(@PathVariable("id") @NotNull Long id,
                                                          @RequestParam("status") @NotBlank @Status String status) {
        BaseMessageData messageReponse = facilityCategoryService.changeStatusFacilityCategory(id, status);
        return ResponseEntity.ok(new BaseResponse(true, 200, messageReponse));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteFacilityCategory(@PathVariable("id") @NotNull Long id) {
        BaseMessageData messageReponse = facilityCategoryService.deletedFacilityCategory(id);
        return ResponseEntity.ok(new BaseResponse(true, 200, messageReponse));
    }
}
