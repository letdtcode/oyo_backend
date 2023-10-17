package com.mascara.oyo_booking_backend.dtos.response.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 13/10/2023
 * Time      : 4:09 CH
 * Filename  : TokenRefreshResponse
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TokenRefreshResponse {
    private String accessToken;
}
