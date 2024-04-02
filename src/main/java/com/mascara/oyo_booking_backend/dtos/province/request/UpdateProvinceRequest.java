package com.mascara.oyo_booking_backend.dtos.province.request;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 22/10/2023
 * Time      : 7:08 CH
 * Filename  : UpdateProvinceRequest
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProvinceRequest {
    private String provinceName;
    private String thumbnailLink;
}
