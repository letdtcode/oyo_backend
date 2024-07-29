package com.mascara.oyo_booking_backend.controllers.client;

import com.mascara.oyo_booking_backend.dtos.base.BaseMessageData;
import com.mascara.oyo_booking_backend.dtos.base.BaseResponse;
import com.mascara.oyo_booking_backend.dtos.base.BasePagingData;
import com.mascara.oyo_booking_backend.services.wish.WishService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 27/11/2023
 * Time      : 4:38 CH
 * Filename  : ClientWishController
 */
@Tag(name = "Client Wish", description = "API xử lý danh mục yêu thích của khách hàng")
@RestController
@RequestMapping("/api/v1/client/wish")
@RequiredArgsConstructor
public class ClientWishController {
    @Autowired
    private WishService wishService;

    @Operation(summary = "Kiểm tra chỗ ở đó có nằm trong danh sách yêu thích của khách hàng không", description = "Kiểm tra chỗ ở đó có nằm trong danh sách yêu thích của khách hàng không")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BaseResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PostMapping("/check-accom-user")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<?> checkAccomPlaceIsWishOfUser(@RequestParam("accomId") @NotNull Long accomId) {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        String userMail = principal.getName();
        BaseMessageData response = wishService.checkAccomPlaceIsWishOfUser(accomId, userMail);
        if (response.getMessage().equals(Boolean.FALSE))
            return ResponseEntity.status(207).body(new BaseResponse<>(true, 207, response));
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }


    @Operation(summary = "Thêm hoặc xóa một danh sách yêu thích", description = "Thêm hoặc xóa một danh sách yêu thích")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BaseResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PostMapping("/handle")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<?> handleWishItemOfUser(@RequestParam("accomId") @NotNull Long accomId) {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        String userMail = principal.getName();
        BaseMessageData response = wishService.handleWishItemOfUser(accomId, userMail);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @Operation(summary = "Lấy và phân trang danh sách yêu thích của khách hàng", description = "Lấy và phân trang danh sách yêu thích của khách hàng")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    content = {@Content(schema = @Schema(implementation = BaseResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
                    content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500",
                    content = {@Content(schema = @Schema())})})
    @GetMapping("/pages")
    public ResponseEntity<?> getListWishItemOfUser(@RequestParam("pageNumber")
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
        BasePagingData response = wishService.getListWishItemOfUser(pageNumber, pageSize, sortType, fieldSort, userMail);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }
}
