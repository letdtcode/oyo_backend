package com.mascara.oyo_booking_backend.dtos.response.province;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 22/10/2023
 * Time      : 7:08 CH
 * Filename  : UpdateProvinceResponse
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProvinceResponse {
    private Long Id;
    private String provinceName;
    private String thumbnailLink;

}
