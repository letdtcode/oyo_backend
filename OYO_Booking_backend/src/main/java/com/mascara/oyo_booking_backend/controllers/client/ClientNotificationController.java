package com.mascara.oyo_booking_backend.controllers.client;

import com.mascara.oyo_booking_backend.dtos.base.BaseMessageData;
import com.mascara.oyo_booking_backend.dtos.base.BasePagingData;
import com.mascara.oyo_booking_backend.dtos.base.BaseResponse;
import com.mascara.oyo_booking_backend.services.notification.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 15/06/2024
 * Time      : 9:30 CH
 * Filename  : ClientNotificationController
 */
@Tag(name = "Client notification", description = "Client notification APIs")
@RestController
@RequestMapping("/api/v1/client/notification")
@RequiredArgsConstructor
@Slf4j
public class ClientNotificationController {

    private final NotificationService notificationService;

    @Operation(summary = "Check accom place is in wish list or not", description = "Client Api for check accom place is in wish list or not")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BaseResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @GetMapping("/page")
    public ResponseEntity<?> getDataNotificationOfUser(@RequestParam("pageNumber")
                                                       @NotNull(message = "Page number must not be null")
                                                       @Min(value = 0, message = "Page number must greater or equal 0")
                                                       Integer pageNumber,
                                                       @RequestParam("pageSize")
                                                       @NotNull(message = "Page size must not be null")
                                                       @Min(value = 1, message = "Page size must greater or equal 1")
                                                       Integer pageSize,
                                                       @RequestParam(value = "viewed", required = false) Boolean isView) {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        String userMail = principal.getName();
        String sortType = "DESC";
        String fieldSort = "created_date";
        BasePagingData response = notificationService.getDataNotificationOfUser(isView, pageNumber, pageSize, sortType, fieldSort, userMail);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @Operation(summary = "Check accom place is in wish list or not", description = "Client Api for check accom place is in wish list or not")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BaseResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PutMapping("/reset-all")
    public ResponseEntity<?> resetAllNotification() {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        String userMail = principal.getName();
        BaseMessageData response = notificationService.resetAllNotification(userMail);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }
}
