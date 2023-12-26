package com.mascara.oyo_booking_backend.exceptions;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 01/10/2023
 * Time      : 4:26 CH
 * Filename  : ResourceNotFoundException
 */
public class ResourceNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 3L;

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
