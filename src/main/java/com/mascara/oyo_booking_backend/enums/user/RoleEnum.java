package com.mascara.oyo_booking_backend.enums.user;

import lombok.Getter;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 30/09/2023
 * Time      : 10:42 CH
 * Filename  : RoleEnum
 */
@Getter
public enum RoleEnum {
    ROLE_CLIENT("ROLE_CLIENT", "Khách hàng"),
    ROLE_ADMIN("ROLE_ADMIN", "Admin"),
    ROLE_PARTNER("ROLE_PARTNER", "Đối tác");
    private final String key;
    private final String value;

    RoleEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
