package com.mascara.oyo_booking_backend.common.enums.order;

import lombok.Getter;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 29/11/2023
 * Time      : 12:08 SA
 * Filename  : PaymentMethodEnum
 */
@Getter
public enum PaymentMethodEnum {
    DIRECT("DIRECT", 100d),
    PAYPAL("PAYPAL", 90D),

    VNPAY("VNPAY", 90D);

    private String key;
    private Double percent;

    PaymentMethodEnum(String key, Double percent) {
        this.key = key;
        this.percent = percent;
    }


}
