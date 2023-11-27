package com.mascara.oyo_booking_backend.controllers.admin;

import com.mascara.oyo_booking_backend.dtos.BaseMessageData;
import com.mascara.oyo_booking_backend.dtos.request.province.AddProvinceRequest;
import com.mascara.oyo_booking_backend.dtos.request.province.UpdateProvinceRequest;
import com.mascara.oyo_booking_backend.dtos.response.BaseResponse;
import com.mascara.oyo_booking_backend.dtos.response.location.UpdateProvinceResponse;
import com.mascara.oyo_booking_backend.services.province.ProvinceService;
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
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addProvince(@RequestBody @Valid AddProvinceRequest request) {
        BaseMessageData response = provinceService.addProvince(request);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @PutMapping("/{province-slug}/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateProvince(@RequestBody @Valid UpdateProvinceRequest request,
                                            @PathVariable("province-slug") @NotNull @NotBlank String provinceSlug) {
        UpdateProvinceResponse response = provinceService.updateProvince(request, provinceSlug);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @DeleteMapping("/{province-slug}/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteProvince(@PathVariable("province-slug") @NotNull @NotBlank String provinceSlug) {
        BaseMessageData response = provinceService.deleteProvince(provinceSlug);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }
}
