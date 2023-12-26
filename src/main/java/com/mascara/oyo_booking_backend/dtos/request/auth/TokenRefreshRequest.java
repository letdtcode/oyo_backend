package com.mascara.oyo_booking_backend.dtos.request.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 01/10/2023
 * Time      : 4:14 CH
 * Filename  : TokenRefreshRequest
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenRefreshRequest {
    @NotBlank(message = "Refresh token is empty")
    @NotNull
    private String refreshToken;
}
