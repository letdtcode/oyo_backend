package com.mascara.oyo_booking_backend.controllers.admin;

import com.mascara.oyo_booking_backend.dtos.accom_place.response.GetAccomPlaceDetailResponse;
import com.mascara.oyo_booking_backend.dtos.base.BaseMessageData;
import com.mascara.oyo_booking_backend.dtos.base.BasePagingData;
import com.mascara.oyo_booking_backend.dtos.base.BaseResponse;
import com.mascara.oyo_booking_backend.common.enums.homestay.AccomStatusEnum;
import com.mascara.oyo_booking_backend.services.accom_place.AccomPlaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
 * Time      : 2:24 SA
 * Filename  : CmsAccomPlaceController
 */
@Tag(name = "Cms Accom Place", description = "Cms Accom Place APIs")
@RestController
@RequestMapping("/api/v1/cms/accoms")
@RequiredArgsConstructor
@Validated
public class CmsAccomPlaceController {
    @Autowired
    private AccomPlaceService accomPlaceService;

    @GetMapping("/pages")
    public ResponseEntity<?> getAllAcommPlaceWithPaging(@RequestParam("pageNumber")
                                                        @NotNull(message = "Page number must not be null")
                                                        @Min(value = 0, message = "Page number must greater or equal 0")
                                                        Integer pageNumber,
                                                        @RequestParam("pageSize")
                                                        @NotNull(message = "Page size must not be null")
                                                        @Min(value = 1, message = "Page size must greater or equal 1")
                                                        Integer pageSize,
                                                        @RequestParam("status") AccomStatusEnum status) {
        String sortType = "DESC";
        String field = "created_date";
        BasePagingData<?> response = null;
        switch (status) {
            case APPROVED ->
                    response = accomPlaceService.getAllAccomPlaceWithPaging(pageNumber, pageSize, sortType, field);
            case WAITING_FOR_APPROVAL ->
                    response = accomPlaceService.getAllAcommPlaceWaitingApprovalWithPaging(pageNumber, pageSize, sortType, field);
        }
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @PutMapping("/{id}/change-status")
    public ResponseEntity<?> changeStatusAccomPlace(@PathVariable("id") @NotNull Long id,
                                                    @RequestParam("status") @NotBlank @Pattern(regexp = "(?i)APPROVED|BANNED|WAITING_FOR_COMPLETE|WAITING_FOR_APPROVAL", message = "Status not be accepted") String status) {
        BaseMessageData messageReponse = accomPlaceService.changeStatusAccomPlace(id, status);
        return ResponseEntity.ok(new BaseResponse(true, 200, messageReponse));
    }

    @PostMapping("/{id}/approve-accom")
    public ResponseEntity<?> approveAccom(@PathVariable("id") @NotNull Long id) {
        BaseMessageData messageReponse = accomPlaceService.approveAccomPlace(id);
        return ResponseEntity.ok(new BaseResponse(true, 200, messageReponse));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAccomPlace(@PathVariable("id") @NotNull Long id) {
        BaseMessageData messageReponse = accomPlaceService.deleteAccomPlaceForAdmin(id);
        return ResponseEntity.ok(new BaseResponse(true, 200, messageReponse));
    }

    @Operation(summary = "Info Detail Accom Place", description = "Public Api detail of accom place")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BaseResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @GetMapping("/{id}/detail")
    public ResponseEntity<?> getInfoAccomPlaceDetails(@PathVariable("id") Long id) {
        GetAccomPlaceDetailResponse response = accomPlaceService.getAccomPlaceDetails(id);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }
}
