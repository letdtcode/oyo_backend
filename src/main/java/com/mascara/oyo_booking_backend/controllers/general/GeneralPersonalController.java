package com.mascara.oyo_booking_backend.controllers.general;

import com.mascara.oyo_booking_backend.dtos.BaseMessageData;
import com.mascara.oyo_booking_backend.dtos.request.user.ChangePasswordRequest;
import com.mascara.oyo_booking_backend.dtos.request.user.UpdateInfoPersonalRequest;
import com.mascara.oyo_booking_backend.dtos.response.BaseResponse;
import com.mascara.oyo_booking_backend.dtos.response.user.InfoUserResponse;
import com.mascara.oyo_booking_backend.services.user.UserService;
import com.mascara.oyo_booking_backend.utils.AppContants;
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
@Tag(name = "Public Check Mail", description = "Authentication APIs")
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
        if (response.getMessage().equals(AppContants.NOT_PERMI_CHANGE_PASSWORD)) {
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
