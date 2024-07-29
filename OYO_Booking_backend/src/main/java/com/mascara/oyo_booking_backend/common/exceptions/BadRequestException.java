package com.mascara.oyo_booking_backend.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 17/12/2023
 * Time      : 5:35 CH
 * Filename  : BadRequestException
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
