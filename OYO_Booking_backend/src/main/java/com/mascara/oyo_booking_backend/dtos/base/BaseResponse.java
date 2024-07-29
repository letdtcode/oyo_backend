package com.mascara.oyo_booking_backend.dtos.base;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 28/10/2023
 * Time      : 11:43 SA
 * Filename  : BaseResponse
 */
@Data
@NoArgsConstructor
public class BaseResponse<T> implements Serializable {
    private boolean success;
    private int statusCode;
    private T data;

    public BaseResponse(boolean success, int statusCode, T data) {
        this.success = success;
        this.statusCode = statusCode;
        this.data = data;
    }
}
