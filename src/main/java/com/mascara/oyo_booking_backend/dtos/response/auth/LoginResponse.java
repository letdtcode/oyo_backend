package com.mascara.oyo_booking_backend.dtos.response.auth;

import com.mascara.oyo_booking_backend.dtos.response.user.InfoUserResponse;
import lombok.*;

import java.util.List;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 13/10/2023
 * Time      : 4:09 CH
 * Filename  : LoginResponse
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String accessToken;
    private String refreshToken;
    private List<String> roles;
    private InfoUserResponse infoUserResponse;
}
