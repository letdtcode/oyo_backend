package com.mascara.oyo_booking_backend.securities.auth;

import com.mascara.oyo_booking_backend.dtos.request.auth.LoginRequest;
import com.mascara.oyo_booking_backend.dtos.request.auth.RegisterRequest;
import com.mascara.oyo_booking_backend.dtos.request.auth.TokenRefreshRequest;
import com.mascara.oyo_booking_backend.dtos.response.auth.AuthResponse;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 01/10/2023
 * Time      : 4:19 CH
 * Filename  : AuthenticationService
 */
public interface IAuthenticationService {
    AuthResponse register(@RequestBody RegisterRequest request);

    AuthResponse authenticate(LoginRequest request);

    AuthResponse refresh(TokenRefreshRequest request);
}
