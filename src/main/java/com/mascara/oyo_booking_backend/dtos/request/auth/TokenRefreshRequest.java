package com.mascara.oyo_booking_backend.dtos.request.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 01/10/2023
 * Time      : 4:14 CH
 * Filename  : TokenRefreshRequest
 */
@Data
public class TokenRefreshRequest {
    @NotBlank(message = "Refresh token is empty")
    private String tokenRefresh;
}
