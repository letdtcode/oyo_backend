package com.mascara.oyo_booking_backend.controllers.admin;

import com.mascara.oyo_booking_backend.dtos.base.BaseMessageData;
import com.mascara.oyo_booking_backend.dtos.type_bed.request.AddTypeBedRequest;
import com.mascara.oyo_booking_backend.dtos.type_bed.request.UpdateTypeBedRequest;
import com.mascara.oyo_booking_backend.dtos.base.BaseResponse;
import com.mascara.oyo_booking_backend.dtos.base.BasePagingData;
import com.mascara.oyo_booking_backend.dtos.type_bed.response.GetTypeBedResponse;
import com.mascara.oyo_booking_backend.services.type_bed.TypeBedService;
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
 * Filename  : CmsTypeBedController
 */
@Tag(name = "Cms Type Bed", description = "Cms Type Bed APIs")
@RestController
@RequestMapping("/api/v1/cms/type-beds")
@RequiredArgsConstructor
@Validated
public class CmsTypeBedController {
    @Autowired
    private TypeBedService typeBedService;

    @PostMapping("/create")
    public ResponseEntity<?> addTypeBed(@RequestBody @Valid AddTypeBedRequest request) {
        GetTypeBedResponse response = typeBedService.addTypeBed(request);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @GetMapping("/pages")
    public ResponseEntity<?> getAllTypeBedWithPaging(@RequestParam("pageNumber")
                                                     @NotNull(message = "Page number must not be null")
                                                     @Min(value = 0, message = "Page number must greater or equal 0")
                                                     Integer pageNumber,
                                                     @RequestParam("pageSize")
                                                     @NotNull(message = "Page size must not be null")
                                                     @Min(value = 1, message = "Page size must greater or equal 1")
                                                     Integer pageSize) {
        String sortType = "DESC";
        String field = "created_date";
        BasePagingData<GetTypeBedResponse> response = typeBedService.getAllTypeBedWithPaging(pageNumber, pageSize, sortType, field);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<?> updateTypeBed(@RequestBody @Valid UpdateTypeBedRequest request,
                                           @PathVariable("id") @NotNull Long id) {
        GetTypeBedResponse response = typeBedService.updateTypeBed(request, id);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @PutMapping("/{id}/change-status")
    public ResponseEntity<?> changeStatusTypeBed(@PathVariable("id")
                                                 @NotNull Long id,
                                                 @RequestParam("status")
                                                 @NotBlank
                                                 @Status String status) {
        BaseMessageData messageReponse = typeBedService.changeStatusTypeBed(id, status);
        return ResponseEntity.ok(new BaseResponse(true, 200, messageReponse));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteTypeBed(@PathVariable("id") @NotNull Long id) {
        BaseMessageData messageReponse = typeBedService.deletedTypeBed(id);
        return ResponseEntity.ok(new BaseResponse(true, 200, messageReponse));
    }
}
