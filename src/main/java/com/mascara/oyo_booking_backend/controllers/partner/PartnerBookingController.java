package com.mascara.oyo_booking_backend.controllers.partner;

import com.mascara.oyo_booking_backend.dtos.response.BaseResponse;
import com.mascara.oyo_booking_backend.dtos.response.booking.GetBookingResponse;
import com.mascara.oyo_booking_backend.dtos.response.paging.BasePagingData;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
public class PartnerBookingController {

    @Autowired
    private BookingService bookingService;

    @Operation(summary = "Add Accom Place For Rent", description = "Partner Api for add accom place")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BaseResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @GetMapping("/pages")
    @PreAuthorize("hasRole('PARTNER')")
    public ResponseEntity<?> getBookingOfPartnerByStatus(@RequestParam("status")
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
        BasePagingData<GetBookingResponse> response = bookingService.getBookingOfPartnerByStatus(hostMail, status, pageNumber, pageSize, sortType, field);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

//    @Operation(summary = "Add Image Accom Place For Rent", description = "Partner Api for add image accom place")
//    @ApiResponses({
//            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BaseResponse.class), mediaType = "application/json")}),
//            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
//            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
//    @PostMapping("/{id}/images/create")
//    @PreAuthorize("hasRole('PARTNER')")
//    public ResponseEntity<?> addImageAccomPlace(@RequestParam("files") List<MultipartFile> files, @PathVariable("id") Long id) {
//        GetAccomPlaceResponse response = accomPlaceService.addImageAccomPlace(files, id);
//        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
//    }
}
