package com.mascara.oyo_booking_backend.exceptions;

import org.springframework.security.core.AuthenticationException;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 17/10/2023
 * Time      : 5:31 CH
 * Filename  : TokenRefreshException
 */
public class TokenRefreshException extends AuthenticationException {
    private static final long serialVersionUID = 4L;

    public TokenRefreshException(String message) {
        super(message);
    }
}

