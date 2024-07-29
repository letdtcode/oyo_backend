package com.mascara.oyo_booking_backend.dtos.location.response.locationDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 24/11/2023
 * Time      : 11:29 CH
 * Filename  : DistrictResponse
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DistrictResponse {
    private String districtCode;
    private String districtName;
    private String provinceCode;
    private List<WardResponse> wardResponseList;
}
