package com.mascara.oyo_booking_backend.dtos.response.auth;

import lombok.*;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 13/10/2023
 * Time      : 4:09 CH
 * Filename  : TokenRefreshResponse
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenRefreshResponse {
    private String accessToken;
}
