package com.mascara.oyo_booking_backend.dtos.response.facility_category;

import com.mascara.oyo_booking_backend.dtos.response.facility.GetFacilityResponse;
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
public class GetFacilityCategorWithFacilityListResponse {
    private String faciCateName;
    private String faciCateCode;
    private String description;
    private List<GetFacilityResponse> infoFacilityList;
    private String status;
}
