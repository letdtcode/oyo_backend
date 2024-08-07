package com.mascara.oyo_booking_backend.controllers.partner;

import com.mascara.oyo_booking_backend.common.constant.MessageConstant;
import com.mascara.oyo_booking_backend.dtos.base.BaseMessageData;
import com.mascara.oyo_booking_backend.dtos.base.BasePagingData;
import com.mascara.oyo_booking_backend.dtos.base.BaseResponse;
import com.mascara.oyo_booking_backend.dtos.booking.response.GetBookingResponse;
import com.mascara.oyo_booking_backend.common.enums.booking.BookingStatusEnum;
import com.mascara.oyo_booking_backend.services.booking.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 01/12/2023
 * Time      : 2:52 SA
 * Filename  : PartnerBookingController
 */
@Tag(name = "Partner Manage", description = "Partner Manage APIs")
@RestController
@RequestMapping("/api/v1/partner/booking")
@RequiredArgsConstructor
@Validated
public class PartnerManageBookingController {

    private final BookingService bookingService;

    @Operation(summary = "Add Accom Place For Rent", description = "Partner Api for add accom place")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BaseResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @GetMapping("/pages")
    public ResponseEntity<?> getListBookingOfPartner(@RequestParam(value = "status", required = false)
                                                     @Pattern(regexp = "WAITING|CHECK_IN|CHECK_OUT|CANCELED",
                                                             message = "Status must be WAITING|CHECK_IN|CHECK_OUT|CANCELED")
                                                     String status,
                                                     @RequestParam("pageNumber")
                                                     @NotNull(message = "Page number must not be null")
                                                     @Min(value = 0, message = "Page number must greater or equal 0")
                                                     Integer pageNumber,
                                                     @RequestParam("pageSize")
                                                     @NotNull(message = "Page size must not be null")
                                                     @Min(value = 1, message = "Page size must greater or equal 1")
                                                     Integer pageSize) {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        String hostMail = principal.getName();
        String sortType = "DESC";
        String field = "created_date";
        BasePagingData<GetBookingResponse> response = bookingService.getListBookingOfPartner(hostMail, status, pageNumber, pageSize, sortType, field);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @Operation(summary = "Check in booking", description = "Check in booking")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BaseResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PutMapping("/check-in")
    public ResponseEntity<?> checkInBooking(@RequestParam("bookingCode")
                                            @NotNull(message = "Booking code must not null")
                                            String bookingCode) {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        String hostMail = principal.getName();
        String status = BookingStatusEnum.CHECK_IN.toString();
        BaseMessageData response = bookingService.changeStatusBookingByHost(hostMail, bookingCode, status);
        if (response.getMessage().equals(MessageConstant.NOT_PERMIT)) {
            return ResponseEntity.status(HttpStatusCode.valueOf(403)).body(new BaseResponse<>(false, 403, response));
        }
        response.setMessage("Check in booking sucessful");
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @Operation(summary = "Check out booking", description = "Check out booking")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BaseResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PutMapping("/check-out")
    public ResponseEntity<?> checkOutBooking(@RequestParam("bookingCode")
                                             @NotNull(message = "Booking code must not null")
                                             String bookingCode) {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        String hostMail = principal.getName();
        String status = BookingStatusEnum.CHECK_OUT.toString();
        BaseMessageData response = bookingService.changeStatusBookingByHost(hostMail, bookingCode, status);
        if (response.getMessage().equals(MessageConstant.NOT_PERMIT)) {
            return ResponseEntity.status(HttpStatusCode.valueOf(403)).body(new BaseResponse<>(false, 403, response));
        }
        response.setMessage("Check out booking sucessful");
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }
}
