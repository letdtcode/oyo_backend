package com.mascara.oyo_booking_backend.controllers.client;

import com.mascara.oyo_booking_backend.dtos.BaseMessageData;
import com.mascara.oyo_booking_backend.dtos.request.booking.BookingRequest;
import com.mascara.oyo_booking_backend.dtos.response.BaseResponse;
import com.mascara.oyo_booking_backend.dtos.response.paging.BasePagingData;
import com.mascara.oyo_booking_backend.enums.BookingStatusEnum;
import com.mascara.oyo_booking_backend.services.booking.BookingService;
import com.mascara.oyo_booking_backend.utils.AppContants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 28/11/2023
 * Time      : 7:53 CH
 * Filename  : ClientBookingController
 */
@Tag(name = "Client booking", description = "CLient booking APIs")
@RestController
@RequestMapping("/api/v1/client/booking")
@RequiredArgsConstructor
public class ClientBookingController {
    @Autowired
    private BookingService bookingService;

    @Operation(summary = "Check accom place is in wish list or not", description = "Client Api for check accom place is in wish list or not")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BaseResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PostMapping("/create")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<?> createOrderBooking(@RequestBody @Valid BookingRequest request) {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        String userMail = principal.getName();
        BaseMessageData response = bookingService.createOrderBookingAccom(request, userMail);
        if (response.getMessage().equals(AppContants.BOOKING_NOT_AVAILABLE_TIME(
                request.getAccomId(),
                request.getCheckIn().toString(),
                request.getCheckOut().toString())))
            return ResponseEntity.status(210).body(new BaseResponse<>(true, 210, response));
        if (response.getMessage().equals(AppContants.BOOKING_NOT_AVAILABLE_PEOPLE))
            return ResponseEntity.status(211).body(new BaseResponse<>(true, 211, response));
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @Operation(summary = "Check accom place is in wish list or not", description = "Client Api for check accom place is in wish list or not")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BaseResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @GetMapping("/history")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<?> showHistoryBooking(@RequestParam("pageNumber")
                                                @NotNull(message = "Page number must not be null")
                                                @Min(value = 0, message = "Page number must greater or equal 0")
                                                Integer pageNumber,
                                                @RequestParam("pageSize")
                                                @NotNull(message = "Page size must not be null")
                                                @Min(value = 1, message = "Page size must greater or equal 1")
                                                Integer pageSize) {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        String userMail = principal.getName();
        String sortType = "DESC";
        String fieldSort = "created_date";
        BasePagingData response = bookingService.getHistoryBookingUser(userMail, pageNumber, pageSize, sortType, fieldSort);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @Operation(summary = "Check accom place is in wish list or not", description = "Client Api for check accom place is in wish list or not")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BaseResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PutMapping("/cancel")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<?> cancelBooking(@RequestParam("bookingCode")
                                           @NotNull(message = "Booking code must not null")
                                           String bookingCode) {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        String userMail = principal.getName();
        String status = BookingStatusEnum.CANCELED.toString();
        BaseMessageData response = bookingService.changeStatusBookingByUser(userMail, bookingCode, status);
        if (response.getMessage().equals(AppContants.NOT_PERMIT)) {
            return ResponseEntity.status(HttpStatusCode.valueOf(403)).body(new BaseResponse<>(false, 403, response));
        }
        response.setMessage("Cancel booking sucessful");
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }
}
