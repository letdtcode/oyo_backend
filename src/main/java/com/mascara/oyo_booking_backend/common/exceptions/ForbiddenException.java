package com.mascara.oyo_booking_backend.common.exceptions;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 05/03/2024
 * Time      : 5:48 CH
 * Filename  : ForbiddenException
 */
public class ForbiddenException extends RuntimeException{
    public ForbiddenException(String msg) {
        super(msg);
    }
}
