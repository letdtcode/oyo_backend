package com.mascara.oyo_booking_backend.enums;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 28/03/2024
 * Time      : 7:02 CH
 * Filename  : SecurityEnum
 */
public enum SecurityEnum {
    UNAUTHENTICATED("Unauthenticated"),
    FORBIDDEN("Forbidden"),
    ACCEPTED("Accepted");
    private final String message;

    SecurityEnum(String message) {
        this.message = message;
    }
}
