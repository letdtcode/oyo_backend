package com.mascara.oyo_booking_backend.controllers.admin;

import com.mascara.oyo_booking_backend.dtos.base.BaseMessageData;
import com.mascara.oyo_booking_backend.dtos.surcharge.surcharge_category.request.AddSurchargeCategoryRequest;
import com.mascara.oyo_booking_backend.dtos.surcharge.surcharge_category.request.UpdateSurchargeCategoryRequest;
import com.mascara.oyo_booking_backend.dtos.base.BaseResponse;
import com.mascara.oyo_booking_backend.dtos.base.BasePagingData;
import com.mascara.oyo_booking_backend.dtos.surcharge.surcharge_category.response.GetSurchargeCategoryResponse;
import com.mascara.oyo_booking_backend.services.surcharge.SurchargeService;
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
 * Date      : 08/12/2023
 * Time      : 5:01 CH
 * Filename  : CmsSurchargeCatefgoryController
 */

@Tag(name = "Cms surcharge category", description = "Cms surcharge category")
@RestController
@RequestMapping("/api/v1/cms/surcharge-categories")
@RequiredArgsConstructor
@Validated
public class CmsSurchargeCatefgoryController {

    @Autowired
    private SurchargeService surchargeService;

    @GetMapping("/pages")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllSurchargeCategoryWithPaging(@RequestParam("pageNumber")
                                                              @NotNull(message = "Page number must not be null")
                                                              @Min(value = 0, message = "Page number must greater or equal 0")
                                                              Integer pageNumber,
                                                              @RequestParam("pageSize")
                                                              @NotNull(message = "Page size must not be null")
                                                              @Min(value = 1, message = "Page size must greater or equal 1")
                                                              Integer pageSize) {
        String sortType = "DESC";
        String field = "created_date";
        BasePagingData<GetSurchargeCategoryResponse> response = surchargeService.getAllSurchargeCategoryWithPaging(pageNumber, pageSize, sortType, field);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addFacility(@RequestBody @Valid AddSurchargeCategoryRequest request) {
        GetSurchargeCategoryResponse response = surchargeService.addSurchargeCategory(request);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @PutMapping("/{id}/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateFacility(@RequestBody @Valid UpdateSurchargeCategoryRequest request,
                                            @PathVariable("id") @NotNull Long id) {
        GetSurchargeCategoryResponse response = surchargeService.updateSurchargeCategory(request, id);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @PutMapping("/{id}/change-status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> changeStatusFacility(@PathVariable("surchargeCateCode") @NotNull Long id,
                                                  @RequestParam("status") @NotBlank @Status String status) {
        BaseMessageData messageReponse = surchargeService.changeStatusSurchargeCategory(id, status);
        return ResponseEntity.ok(new BaseResponse(true, 200, messageReponse));
    }

    @DeleteMapping("/{id}/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteFacility(@PathVariable("id") @NotNull Long id) {
        BaseMessageData messageReponse = surchargeService.deletedSurchargeCategory(id);
        return ResponseEntity.ok(new BaseResponse(true, 200, messageReponse));
    }
}
