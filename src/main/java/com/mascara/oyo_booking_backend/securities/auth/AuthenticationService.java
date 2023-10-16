package com.mascara.oyo_booking_backend.securities.auth;

import com.mascara.oyo_booking_backend.dtos.request.auth.LoginRequest;
import com.mascara.oyo_booking_backend.dtos.request.auth.RegisterRequest;
import com.mascara.oyo_booking_backend.dtos.request.auth.TokenRefreshRequest;
import com.mascara.oyo_booking_backend.dtos.response.auth.LoginResponse;
import com.mascara.oyo_booking_backend.dtos.response.auth.RegisterResponse;
import com.mascara.oyo_booking_backend.dtos.response.auth.TokenRefreshResponse;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 01/10/2023
 * Time      : 4:19 CH
 * Filename  : AuthenticationService
 */

public interface AuthenticationService {
    RegisterResponse register(RegisterRequest request);

    LoginResponse authenticate(LoginRequest request);

    TokenRefreshResponse refresh(TokenRefreshRequest request);
}
