package com.mascara.oyo_booking_backend.exceptions;

import org.springframework.security.authentication.BadCredentialsException;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 09/10/2023
 * Time      : 6:00 CH
 * Filename  : NotCredentialException
 */
public class NotCredentialException extends BadCredentialsException {
    private static final long serialVersionUID = 1L;
    public NotCredentialException(String msg) {
        super(msg);
    }
}
