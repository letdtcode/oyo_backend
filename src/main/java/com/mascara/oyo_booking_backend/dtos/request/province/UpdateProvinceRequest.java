package com.mascara.oyo_booking_backend.dtos.request.province;

import jakarta.persistence.Column;
import lombok.Getter;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 22/10/2023
 * Time      : 7:08 CH
 * Filename  : UpdateProvinceRequest
 */
@Getter
public class UpdateProvinceRequest {
    private String provinceName;
    private String thumbnailLink;
}
