package com.mascara.oyo_booking_backend.controllers.general;

import com.mascara.oyo_booking_backend.common.constant.MessageConstant;
import com.mascara.oyo_booking_backend.dtos.base.BaseMessageData;
import com.mascara.oyo_booking_backend.dtos.base.BaseResponse;
import com.mascara.oyo_booking_backend.dtos.user.request.ChangePasswordRequest;
import com.mascara.oyo_booking_backend.dtos.user.request.UpdateInfoPersonalRequest;
import com.mascara.oyo_booking_backend.dtos.user.response.InfoUserResponse;
import com.mascara.oyo_booking_backend.services.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 24/10/2023
 * Time      : 3:07 SA
 * Filename  : GeneralPersonalController
 */
@Tag(name = "General Personal", description = "General Personal APIs")
@RestController
@RequestMapping("/api/v1/general")
@RequiredArgsConstructor
public class GeneralPersonalController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Update info user", description = "General Api for update info user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BaseResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PutMapping("/update-info")
    @PreAuthorize("hasRole('CLIENT') or hasRole('PARTNER') or hasRole('ADMIN')")
    public ResponseEntity<?> updateInfoPersonal(@Valid @RequestBody UpdateInfoPersonalRequest updateInfoPersonalRequest) {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        String userMail = principal.getName();
        return ResponseEntity.ok(new BaseResponse<>(true, 200, userService.updateInfoPersonal(updateInfoPersonalRequest, userMail)));
    }

    @Operation(summary = "Change password user", description = "General Api for change password user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BaseResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "403", content = {@Content(schema = @Schema(implementation = BaseResponse.class), mediaType = "application/json")})
    })
    @PutMapping("/change-password")
    @PreAuthorize("hasRole('CLIENT') or hasRole('PARTNER') or hasRole('ADMIN')")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest) {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        String userMail = principal.getName();
        BaseMessageData response = userService.changePassword(changePasswordRequest, userMail);
        if (response.getMessage().equals(MessageConstant.NOT_PERMIT)) {
            return ResponseEntity.status(HttpStatusCode.valueOf(403)).body(new BaseResponse<>(false, 403, response));
        }
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @Operation(summary = "Update avatar user", description = "General Api for update avatar")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BaseResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PutMapping("/update-avatar")
    @PreAuthorize("hasRole('CLIENT') or hasRole('PARTNER') or hasRole('ADMIN')")
    public ResponseEntity<?> updateAvatar(@RequestParam("file") MultipartFile file) {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        String userMail = principal.getName();
        InfoUserResponse response = userService.updateAvatar(file, userMail);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }
}
