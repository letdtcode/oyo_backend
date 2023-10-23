package com.mascara.oyo_booking_backend.dtos.response.province;

import com.mascara.oyo_booking_backend.entities.Province;
import lombok.*;

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
public class GetAllDataProvinceResponse {
    private List<Province> provinceList;
}
