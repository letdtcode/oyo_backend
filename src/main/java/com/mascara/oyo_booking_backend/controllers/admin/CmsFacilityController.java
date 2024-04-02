package com.mascara.oyo_booking_backend.controllers.admin;

import com.mascara.oyo_booking_backend.dtos.base.BaseMessageData;
import com.mascara.oyo_booking_backend.dtos.facility.request.AddFacilityRequest;
import com.mascara.oyo_booking_backend.dtos.facility.request.UpdateFacilityRequest;
import com.mascara.oyo_booking_backend.dtos.base.BaseResponse;
import com.mascara.oyo_booking_backend.dtos.facility.response.GetFacilityResponse;
import com.mascara.oyo_booking_backend.dtos.base.BasePagingData;
import com.mascara.oyo_booking_backend.services.facility.FacilityService;
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

    @GetMapping("/pages")
    @PreAuthorize("hasRole('ADMIN')")
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
        BasePagingData<GetFacilityResponse> response = facilityService.getAllFacilityWithPaging(pageNumber, pageSize, sortType, field);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addFacility(@RequestBody @Valid AddFacilityRequest request) {
        GetFacilityResponse response = facilityService.addFacility(request);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @PutMapping("/{id}/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateFacility(@RequestBody @Valid UpdateFacilityRequest request,
                                            @PathVariable("id") @NotNull Long id) {
        GetFacilityResponse response = facilityService.updateFacility(request, id);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @PutMapping("/{id}/change-status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> changeStatusFacility(@PathVariable("id") @NotNull Long id,
                                                  @RequestParam("status") @NotBlank @Status String status) {
        BaseMessageData messageReponse = facilityService.changeStatusFacility(id, status);
        return ResponseEntity.ok(new BaseResponse(true, 200, messageReponse));
    }

    @DeleteMapping("/{id}/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteFacility(@PathVariable("id") @NotNull Long id) {
        BaseMessageData messageReponse = facilityService.deletedFacility(id);
        return ResponseEntity.ok(new BaseResponse(true, 200, messageReponse));
    }
}
