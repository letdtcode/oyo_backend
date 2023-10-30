package com.mascara.oyo_booking_backend.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 28/10/2023
 * Time      : 11:43 SA
 * Filename  : BaseResponse
 */
@Data
@AllArgsConstructor
public class BaseResponse <T>{
    private T data;
}
