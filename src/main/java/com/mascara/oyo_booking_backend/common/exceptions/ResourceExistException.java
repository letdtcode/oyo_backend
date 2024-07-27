package com.mascara.oyo_booking_backend.common.exceptions;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 09/10/2023
 * Time      : 5:56 CH
 * Filename  : ResourceExistException
 */
public class ResourceExistException extends RuntimeException {

    private static final long serialVersionUID = 2L;

    public ResourceExistException(String message) {
        super(message);
    }
}
