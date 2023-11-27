package com.mascara.oyo_booking_backend.controllers.admin;

import com.mascara.oyo_booking_backend.dtos.BaseMessageData;
import com.mascara.oyo_booking_backend.dtos.response.BaseResponse;
import com.mascara.oyo_booking_backend.dtos.response.paging.BasePagingData;
import com.mascara.oyo_booking_backend.dtos.response.user.InfoUserResponse;
import com.mascara.oyo_booking_backend.services.user.UserService;
import com.mascara.oyo_booking_backend.utils.validation.Status;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Email;
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
 * Time      : 2:24 SA
 * Filename  : CmsUserController
 */
@Tag(name = "Cms User", description = "Cms User APIs")
@RestController
@RequestMapping("/api/v1/cms/users")
@RequiredArgsConstructor
@Validated
public class CmsUserController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllUserWithPaging(@RequestParam("pageNumber")
                                                  @NotNull(message = "Page number must not be null")
                                                  @Min(value = 0, message = "Page number must greater or equal 0")
                                                  Integer pageNumber,
                                                  @RequestParam("pageSize")
                                                  @NotNull(message = "Page size must not be null")
                                                  @Min(value = 1, message = "Page size must greater or equal 1")
                                                  Integer pageSize) {
        String field = "created_date";
        String sortType = "DESC";
        BasePagingData<InfoUserResponse> response = userService.getAllUserWithPaging(pageNumber, pageSize, sortType, field);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @PutMapping("/{mail}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> changeStatusUser(@PathVariable("mail")
                                              @Email String mail,
                                              @RequestParam("status")
                                              @NotNull
                                              @NotBlank
                                              @Status String status) {
        BaseMessageData messageReponse = userService.changeStatusUser(mail, status);
        return ResponseEntity.ok(new BaseResponse(true, 200, messageReponse));
    }

    @DeleteMapping("/{mail}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable("mail") @Email String mail) {
        BaseMessageData messageReponse = userService.deleteUser(mail);
        return ResponseEntity.ok(new BaseResponse(true, 200, messageReponse));
    }
}
