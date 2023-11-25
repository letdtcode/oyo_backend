package com.mascara.oyo_booking_backend.controllers.admin;

import com.mascara.oyo_booking_backend.dtos.response.BaseResponse;
import com.mascara.oyo_booking_backend.dtos.response.paging.BasePagingData;
import com.mascara.oyo_booking_backend.dtos.response.user.InfoUserResponse;
import com.mascara.oyo_booking_backend.services.user.UserService;
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
 * Filename  : CmsUserController
 */
@Tag(name = "Cms User", description = "Cms User APIs")
@RestController
@RequestMapping("/api/v1/cms/users")
@RequiredArgsConstructor
public class CmsUserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ResponseEntity<?> getAllUserWithPaging(@RequestParam("pageNumber") Integer pageNumber) {
        BasePagingData<InfoUserResponse> response = userService.getAllUserWithPaging(pageNumber);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @PutMapping("/{mail}")
    public ResponseEntity<?> changeStatusUser(@PathVariable("mail") String mail, @RequestParam("status") String status) {
        String messageReponse = userService.changeStatusUser(mail, status);
        return ResponseEntity.ok(new BaseResponse(true, 200, messageReponse));
    }

    @DeleteMapping("/{mail}")
    public ResponseEntity<?> deleteUser(@PathVariable("mail") String mail) {
        String messageReponse = userService.deleteUser(mail);
        return ResponseEntity.ok(new BaseResponse(true, 200, messageReponse));
    }
}
