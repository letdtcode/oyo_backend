package com.mascara.oyo_booking_backend.controllers.admin;

import com.mascara.oyo_booking_backend.dtos.request.province.AddProvinceRequest;
import com.mascara.oyo_booking_backend.dtos.request.province.UpdateProvinceRequest;
import com.mascara.oyo_booking_backend.dtos.response.BaseResponse;
import com.mascara.oyo_booking_backend.dtos.response.location.UpdateProvinceResponse;
import com.mascara.oyo_booking_backend.services.province.ProvinceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
public class CmsProvinceController {

    @Autowired
    private ProvinceService provinceService;

    @PostMapping("/")
    public ResponseEntity<?> addProvince(@RequestBody AddProvinceRequest request) {
        String response = provinceService.addProvince(request);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @PutMapping("/{province-slug}")
    public ResponseEntity<?> updateProvince(@RequestBody UpdateProvinceRequest request,
                                            @PathVariable("province-slug") String provinceSlug) {
        UpdateProvinceResponse response = provinceService.updateProvince(request, provinceSlug);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @DeleteMapping("/{province-slug}")
    public ResponseEntity<?> deleteProvince(@RequestParam("provinceName") String provinceName) {
        String response = provinceService.deleteProvince(provinceName);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }
}
