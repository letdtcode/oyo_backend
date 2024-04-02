package com.mascara.oyo_booking_backend.dtos.base;

import lombok.Data;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 26/11/2023
 * Time      : 7:57 CH
 * Filename  : MessageResponse
 */
@Data
public class BaseMessageData<T> {
    private T message;

    public BaseMessageData(T message) {
        this.message = message;
    }

}
