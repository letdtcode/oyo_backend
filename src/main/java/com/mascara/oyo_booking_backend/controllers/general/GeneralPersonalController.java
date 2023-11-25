package com.mascara.oyo_booking_backend.controllers.general;

import com.mascara.oyo_booking_backend.dtos.request.user.ChangePasswordRequest;
import com.mascara.oyo_booking_backend.dtos.request.user.UpdateInfoPersonalRequest;
import com.mascara.oyo_booking_backend.dtos.response.BaseResponse;
import com.mascara.oyo_booking_backend.dtos.response.user.InfoUserResponse;
import com.mascara.oyo_booking_backend.services.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<?> updateInfoPersonal(@Valid @RequestBody UpdateInfoPersonalRequest updateInfoPersonalRequest, @Email @RequestParam("mail") String email) {
        return ResponseEntity.ok(userService.updateInfoPersonal(updateInfoPersonalRequest, email));
    }

    @Operation(summary = "Change password user", description = "General Api for change password user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BaseResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest) {
        String response = userService.changePassword(changePasswordRequest);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @Operation(summary = "Update avatar user", description = "General Api for update avatar")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BaseResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PutMapping("/update_avatar")
    public ResponseEntity<?> updateAvatar(@RequestParam("file") MultipartFile file, @NotBlank @Email @RequestParam("mail") String mail) {
        InfoUserResponse response = userService.updateAvatar(file, mail);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }
}
