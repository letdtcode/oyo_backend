package com.mascara.oyo_booking_backend.common.exceptions;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 24/10/2023
 * Time      : 9:49 CH
 * Filename  : StorageException
 */
public class StorageException extends RuntimeException {
    private static final long serialVersionUID = 4L;

    public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Exception e) {
        super(message,e);
    }
}
