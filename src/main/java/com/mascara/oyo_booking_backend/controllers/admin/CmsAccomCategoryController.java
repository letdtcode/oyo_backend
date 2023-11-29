package com.mascara.oyo_booking_backend.controllers.admin;

import com.mascara.oyo_booking_backend.dtos.BaseMessageData;
import com.mascara.oyo_booking_backend.dtos.request.accom_category.AddAccomCategoryRequest;
import com.mascara.oyo_booking_backend.dtos.request.accom_category.UpdateAccomCategoryRequest;
import com.mascara.oyo_booking_backend.dtos.response.BaseResponse;
import com.mascara.oyo_booking_backend.dtos.response.accom_category.GetAccomCategoryResponse;
import com.mascara.oyo_booking_backend.dtos.response.paging.BasePagingData;
import com.mascara.oyo_booking_backend.services.accom_category.AccomCategoryService;
import com.mascara.oyo_booking_backend.utils.validation.Status;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
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
@Slf4j
@Validated
public class CmsAccomCategoryController {

    @Autowired
    private AccomCategoryService accomCategoryService;

    @Operation(summary = "Add Accom Place For Rent", description = "Partner Api for add accom place")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BaseResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @GetMapping("/pages")
    @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<?> getAllAcommCategoryWithPaging(@RequestParam("pageNumber")
                                                           @NotNull(message = "Page number must not be null")
                                                           @Min(value = 0, message = "Page number must greater or equal 0")
                                                           Integer pageNumber,
                                                           @RequestParam("pageSize")
                                                           @NotNull(message = "Page size must not be null")
                                                           @Min(value = 1, message = "Page size must greater or equal 1")
                                                           Integer pageSize) {
        String sortType = "DESC";
        String field = "created_date";
        BasePagingData<GetAccomCategoryResponse> response = accomCategoryService.getAllAccomCategoryWithPaging(pageNumber, pageSize,sortType,field);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @Operation(summary = "Add Accom Place For Rent", description = "Partner Api for add accom place")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BaseResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addAccomCategory(@RequestBody @Valid AddAccomCategoryRequest request) {
        BaseMessageData<String> response = accomCategoryService.addAccomCategory(request);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @Operation(summary = "Add Accom Place For Rent", description = "Partner Api for add accom place")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BaseResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PutMapping("/{id}/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateAccomCategory(@RequestBody @Valid UpdateAccomCategoryRequest request,
                                                 @PathVariable("id") @NotNull Long id) {
        BaseMessageData<String> response = accomCategoryService.updateAccomCategory(request, id);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @Operation(summary = "Add Accom Place For Rent", description = "Partner Api for add accom place")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BaseResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PutMapping("/{id}/change-status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> changeStatusAccomCategory(@PathVariable("id") @NotNull Long id,
                                                       @RequestParam("status") @NotBlank @Status  String status) {
        log.error(status);
        BaseMessageData response = accomCategoryService.changeStatusAccomCategory(id, status);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @Operation(summary = "Add Accom Place For Rent", description = "Partner Api for add accom place")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BaseResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @DeleteMapping("/{id}/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteAccomCategory(@PathVariable("id") @NotNull Long id) {
        BaseMessageData response = accomCategoryService.deleteAccomCategory(id);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }
}
