package com.mascara.oyo_booking_backend.controllers.admin;

import com.mascara.oyo_booking_backend.dtos.response.BaseResponse;
import com.mascara.oyo_booking_backend.dtos.response.accommodation.GetAccomPlaceResponse;
import com.mascara.oyo_booking_backend.dtos.response.paging.BasePagingData;
import com.mascara.oyo_booking_backend.services.accom_place.AccomPlaceService;
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
 * Filename  : CmsAccomPlaceController
 */
@Tag(name = "Cms Accom Place", description = "Cms Accom Place APIs")
@RestController
@RequestMapping("/api/v1/cms/accom")
@RequiredArgsConstructor
public class CmsAccomPlaceController {
    @Autowired
    private AccomPlaceService accomPlaceService;

    @GetMapping("/")
    public ResponseEntity<?> getAllAcommPlaceWithPaging(@RequestParam("pageNumber") Integer pageNumber) {
        BasePagingData<GetAccomPlaceResponse> response = accomPlaceService.getAllAccomPlaceWithPaging(pageNumber);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> changeStatusAccomPlace(@PathVariable("id") Long id, @RequestParam("status") String status) {
        String messageReponse = accomPlaceService.changeStatusAccomPlace(id, status);
        return ResponseEntity.ok(new BaseResponse(true, 200, messageReponse));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAccomPlace(@PathVariable("id") Long id) {
        String messageReponse = accomPlaceService.deleteAccomPlace(id);
        return ResponseEntity.ok(new BaseResponse(true, 200, messageReponse));
    }
}
