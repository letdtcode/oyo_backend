package com.mascara.oyo_booking_backend.controllers.admin;

import com.mascara.oyo_booking_backend.dtos.BaseMessageData;
import com.mascara.oyo_booking_backend.dtos.request.facility.UpdateFacilityRequest;
import com.mascara.oyo_booking_backend.dtos.request.surcharge_category.AddSurchargeCategoryRequest;
import com.mascara.oyo_booking_backend.dtos.request.surcharge_category.UpdateSurchargeCategoryRequest;
import com.mascara.oyo_booking_backend.dtos.response.BaseResponse;
import com.mascara.oyo_booking_backend.dtos.response.surcharge.GetSurchargeCategoryResponse;
import com.mascara.oyo_booking_backend.services.surcharge.SurchargeService;
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
 * Date      : 08/12/2023
 * Time      : 5:01 CH
 * Filename  : CmsSurchargeCatefgoryController
 */

@Tag(name = "Cms surcharge category", description = "Cms surcharge category")
@RestController
@RequestMapping("/api/v1/cms/surcharge-cate")
@RequiredArgsConstructor
@Validated
public class CmsSurchargeCatefgoryController {

    @Autowired
    private SurchargeService surchargeService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addFacility(@RequestBody @Valid AddSurchargeCategoryRequest request) {
        GetSurchargeCategoryResponse response = surchargeService.addSurchargeCategory(request);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @PutMapping("/{surchargeCateCode}/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateFacility(@RequestBody @Valid UpdateSurchargeCategoryRequest request,
                                            @PathVariable("surchargeCateCode") @NotNull String surchargeCateCode) {
        GetSurchargeCategoryResponse response = surchargeService.updateSurchargeCategory(request, surchargeCateCode);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @PutMapping("/{surchargeCateCode}/change-status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> changeStatusFacility(@PathVariable("surchargeCateCode") @NotNull String surchargeCateCode,
                                                  @RequestParam("status") @NotBlank @Status String status) {
        BaseMessageData messageReponse = surchargeService.changeStatusSurchargeCategory(surchargeCateCode, status);
        return ResponseEntity.ok(new BaseResponse(true, 200, messageReponse));
    }

    @DeleteMapping("/{surchargeCateCode}/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteFacility(@PathVariable("surchargeCateCode") @NotNull String surchargeCateCode) {
        BaseMessageData messageReponse = surchargeService.deletedSurchargeCategory(surchargeCateCode);
        return ResponseEntity.ok(new BaseResponse(true, 200, messageReponse));
    }
}
