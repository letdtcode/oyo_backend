package com.mascara.oyo_booking_backend.controllers.admin;

import com.mascara.oyo_booking_backend.dtos.base.BaseMessageData;
import com.mascara.oyo_booking_backend.dtos.province.request.AddProvinceRequest;
import com.mascara.oyo_booking_backend.dtos.province.request.UpdateProvinceRequest;
import com.mascara.oyo_booking_backend.dtos.base.BaseResponse;
import com.mascara.oyo_booking_backend.dtos.location.response.locationDTO.UpdateProvinceResponse;
import com.mascara.oyo_booking_backend.services.province.ProvinceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
 * Date      : 22/10/2023
 * Time      : 7:24 CH
 * Filename  : CmsProvinceController
 */
@Tag(name = "Cms Province", description = "Cms Province APIs")
@RestController
@RequestMapping("/api/v1/cms/provinces")
@RequiredArgsConstructor
@Validated
public class CmsProvinceController {

    @Autowired
    private ProvinceService provinceService;

    @PostMapping("/create")
    public ResponseEntity<?> addProvince(@RequestBody @Valid AddProvinceRequest request) {
        BaseMessageData response = provinceService.addProvince(request);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<?> updateProvince(@RequestBody @Valid UpdateProvinceRequest request,
                                            @PathVariable("id") @NotNull Long id) {
        UpdateProvinceResponse response = provinceService.updateProvince(request, id);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteProvince(@PathVariable("id") @NotNull Long id) {
        BaseMessageData response = provinceService.deleteProvince(id);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }
}
