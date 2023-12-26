package com.mascara.oyo_booking_backend.dtos.response.location.locationDTO;

import com.mascara.oyo_booking_backend.dtos.response.location.locationDTO.DistrictResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 24/11/2023
 * Time      : 11:29 CH
 * Filename  : ProvinceResponse
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProvinceResponse {
    private String provinceName;
    private String thumbnail;
    private String provinceCode;
    private String divisionType;
    private String slugs;
    private List<DistrictResponse> districtResponseList;
}
