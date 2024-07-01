package com.mascara.oyo_booking_backend.controllers.client;

import com.mascara.oyo_booking_backend.dtos.base.BasePagingData;
import com.mascara.oyo_booking_backend.dtos.base.BaseResponse;
import com.mascara.oyo_booking_backend.services.recommender_system.RecommenderSystemService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 01/07/2024
 * Time      : 9:25 CH
 * Filename  : ClientRecommendationController
 */
@Tag(name = "Client accom place", description = "Client accom place APIs")
@RestController
@RequestMapping("/api/v1/client/accom-place")
@RequiredArgsConstructor
@Slf4j
public class ClientAccomPlaceController {

    private final RecommenderSystemService recommenderSystemService;

    @Operation(summary = "Check accom place is in wish list or not", description = "Client Api for check accom place is in wish list or not")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BaseResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @GetMapping("/recommend")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<?> getAccomPlaceRecommend(@RequestParam("pageNumber")
                                                    @NotNull(message = "Page number must not be null")
                                                    @Min(value = 0, message = "Page number must greater or equal 0")
                                                    Integer pageNumber,
                                                    @RequestParam("pageSize")
                                                    @NotNull(message = "Page size must not be null")
                                                    @Min(value = 1, message = "Page size must greater or equal 1")
                                                    Integer pageSize) {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        String userMail = principal.getName();
        BasePagingData response = recommenderSystemService.getAccomPlaceRecommend(pageNumber, pageSize, userMail);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }
}
