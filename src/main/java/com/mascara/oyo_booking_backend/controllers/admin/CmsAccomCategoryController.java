package com.mascara.oyo_booking_backend.controllers.admin;

import com.mascara.oyo_booking_backend.dtos.request.accom_category.AddAccomCategoryRequest;
import com.mascara.oyo_booking_backend.dtos.request.accom_category.UpdateAccomCategoryRequest;
import com.mascara.oyo_booking_backend.dtos.response.BaseResponse;
import com.mascara.oyo_booking_backend.dtos.response.accom_category.GetAccomCategoryResponse;
import com.mascara.oyo_booking_backend.dtos.response.accommodation.GetAccomPlaceResponse;
import com.mascara.oyo_booking_backend.dtos.response.paging.BasePagingData;
import com.mascara.oyo_booking_backend.services.accom_category.AccomCategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 25/11/2023
 * Time      : 2:24 SA
 * Filename  : CmsAccomCateController
 */
@Tag(name = "Cms Accom Category", description = "Cms Accom Category APIs")
@RestController
@RequestMapping("/api/v1/cms/accom-categories")
@RequiredArgsConstructor
public class CmsAccomCategoryController {

    @Autowired
    private AccomCategoryService accomCategoryService;

    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllAcommCategoryWithPaging(@RequestParam("pageNumber") Integer pageNumber,
                                                           @RequestParam("pageSize") Integer pageSize) {
        BasePagingData<GetAccomCategoryResponse> response = accomCategoryService.getAllAccomCategoryWithPaging(pageNumber,pageSize);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addAccomCategory(@RequestBody AddAccomCategoryRequest request) {
        String response = accomCategoryService.addAccomCategory(request);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateAccomCategory(@RequestBody UpdateAccomCategoryRequest request,
                                                 @PathVariable("id") Long id) {
        String response = accomCategoryService.updateAccomCategory(request, id);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }
    @PutMapping("/{id}/{status}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> changeStatusAccomCategory(@PathVariable("id") Long id,
                                                       @PathVariable("status") @NotNull @NotBlank String status) {
        String response = accomCategoryService.changeStatusAccomCategory(id, status);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteAccomCategory(@PathVariable("id") Long id) {
        String response = accomCategoryService.deleteAccomCategory(id);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }
}
