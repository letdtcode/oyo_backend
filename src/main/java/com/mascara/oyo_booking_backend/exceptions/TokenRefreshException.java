package com.mascara.oyo_booking_backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 17/10/2023
 * Time      : 5:31 CH
 * Filename  : TokenRefreshException
 */
@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class TokenRefreshException extends AuthenticationException {
    private final String token;
    private final String message;

    public TokenRefreshException(String token, String message) {
        super(String.format("Couldn't refresh token for [%s]: [%s])", token, message));
        this.token = token;
        this.message = message;
    }
}

