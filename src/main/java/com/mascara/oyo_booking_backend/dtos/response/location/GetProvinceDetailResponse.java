package com.mascara.oyo_booking_backend.dtos.response.location;

import com.mascara.oyo_booking_backend.dtos.response.location.locationDTO.ProvinceResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 23/10/2023
 * Time      : 2:43 CH
 * Filename  : GetAllProvinceFullData
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetProvinceDetailResponse {
    private List<ProvinceResponse> provinceList;
}
