package com.mascara.oyo_booking_backend.controllers.admin;

import com.mascara.oyo_booking_backend.dtos.request.accom_category.AddAccomCategoryRequest;
import com.mascara.oyo_booking_backend.dtos.request.accom_category.UpdateAccomCategoryRequest;
import com.mascara.oyo_booking_backend.dtos.response.BaseResponse;
import com.mascara.oyo_booking_backend.services.accom_category.AccomCategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 25/11/2023
 * Time      : 2:24 SA
 * Filename  : CmsAccomCateController
 */
@Tag(name = "Cms Accom Place", description = "Cms Accom Place APIs")
@RestController
@RequestMapping("/api/v1/cms/accom-categories")
@RequiredArgsConstructor
public class CmsAccomCategoryController {

    @Autowired
    private AccomCategoryService accomCategoryService;

    @PostMapping("/")
    public ResponseEntity<?> addAccomCategory(@RequestBody AddAccomCategoryRequest request) {
        String response = accomCategoryService.addAccomCategory(request);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAccomCategory(@RequestBody UpdateAccomCategoryRequest request,
                                                 @PathVariable("id") Long id) {
        String response = accomCategoryService.updateAccomCategory(request, id);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProvince(@PathVariable("id") Long id) {
        String response = accomCategoryService.deleteAccomCategory(id);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }
}
