package com.mascara.oyo_booking_backend.dtos.response.facility;

import com.mascara.oyo_booking_backend.entities.Facility;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 31/10/2023
 * Time      : 5:57 CH
 * Filename  : GetAllDataFacilityResponse
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetFacilityCategoryResponse {
    private String faciCateName;
    private String faciCateCode;
    private List<GetFacilityResponse> infoFacilityList;
    private String status;
}
