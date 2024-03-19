package com.mascara.oyo_booking_backend.dtos.response.location;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 27/11/2023
 * Time      : 6:53 CH
 * Filename  : GetProvinceResponse
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetProvinceResponse {
    private String provinceName;
    private String thumbnail;
    private String provinceCode;
    private String divisionType;
    private String slugs;
    private Long numBooking;
}
