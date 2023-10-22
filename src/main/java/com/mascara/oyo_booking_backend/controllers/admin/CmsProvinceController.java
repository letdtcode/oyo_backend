package com.mascara.oyo_booking_backend.controllers.admin;

import com.mascara.oyo_booking_backend.dtos.request.province.AddProvinceRequest;
import com.mascara.oyo_booking_backend.dtos.request.province.UpdateProvinceRequest;
import com.mascara.oyo_booking_backend.entities.Role;
import com.mascara.oyo_booking_backend.enums.RoleEnum;
import com.mascara.oyo_booking_backend.services.province.ProvinceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 22/10/2023
 * Time      : 7:24 CH
 * Filename  : CmsProvinceController
 */
@Tag(name = "AuthController", description = "Authentication APIs")
@RestController
@RequestMapping("/api/v1/cms")
@RequiredArgsConstructor
public class CmsProvinceController {

    @Autowired
    private ProvinceService provinceService;

    @GetMapping("/add")
    public ResponseEntity<?> addProvince(@RequestBody AddProvinceRequest request) {
        return ResponseEntity.ok(provinceService.addProvince(request));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateProvince(@RequestBody UpdateProvinceRequest request, String provinceName) {
        return ResponseEntity.ok(provinceService.updateProvince(request,provinceName));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteProvince(@Param("provinceName") String provinceName) {
        return ResponseEntity.ok(provinceService.deleteProvince(provinceName));
    }
}
