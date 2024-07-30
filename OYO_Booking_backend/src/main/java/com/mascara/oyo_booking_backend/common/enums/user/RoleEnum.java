package com.mascara.oyo_booking_backend.common.enums.user;

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
    CLIENT("CLIENT", "Khách hàng"),
    ADMIN("ADMIN", "Admin"),
    PARTNER("PARTNER", "Đối tác");
    private final String key;
    private final String value;

    RoleEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
