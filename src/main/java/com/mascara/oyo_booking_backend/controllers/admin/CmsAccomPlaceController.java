package com.mascara.oyo_booking_backend.controllers.admin;

import com.mascara.oyo_booking_backend.dtos.BaseMessageData;
import com.mascara.oyo_booking_backend.dtos.response.BaseResponse;
import com.mascara.oyo_booking_backend.dtos.response.accommodation.GetAccomPlaceResponse;
import com.mascara.oyo_booking_backend.dtos.response.paging.BasePagingData;
import com.mascara.oyo_booking_backend.services.accom_place.AccomPlaceService;
import com.mascara.oyo_booking_backend.utils.validation.Status;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("")
    public ResponseEntity<?> getAllAcommPlaceWithPaging(@RequestParam("pageNumber")
                                                        @NotNull(message = "Page number must not be null")
                                                        @Min(value = 0, message = "Page number must greater or equal 0")
                                                        Integer pageNumber,
                                                        @RequestParam("pageSize")
                                                        @NotNull(message = "Page size must not be null")
                                                        @Min(value = 1, message = "Page size must greater or equal 1")
                                                        Integer pageSize) {
        String sortType = "DESC";
        String field = "created_date";
        BasePagingData<GetAccomPlaceResponse> response = accomPlaceService.getAllAccomPlaceWithPaging(pageNumber, pageSize, sortType, field);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @PutMapping("/{id}/{status}")
    public ResponseEntity<?> changeStatusAccomPlace(@PathVariable("id") @NotNull Long id,
                                                    @PathVariable("status") @NotBlank @Status String status) {
        BaseMessageData messageReponse = accomPlaceService.changeStatusAccomPlace(id, status);
        return ResponseEntity.ok(new BaseResponse(true, 200, messageReponse));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAccomPlace(@PathVariable("id") @NotNull Long id) {
        BaseMessageData messageReponse = accomPlaceService.deleteAccomPlace(id);
        return ResponseEntity.ok(new BaseResponse(true, 200, messageReponse));
    }
}
