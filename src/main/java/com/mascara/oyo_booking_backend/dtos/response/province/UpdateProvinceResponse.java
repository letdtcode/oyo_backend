package com.mascara.oyo_booking_backend.dtos.response.province;

import jakarta.persistence.Column;
import lombok.*;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 22/10/2023
 * Time      : 7:08 CH
 * Filename  : UpdateProvinceResponse
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProvinceResponse {
    private Long Id;
    private String provinceName;
    private String thumbnailLink;

}
