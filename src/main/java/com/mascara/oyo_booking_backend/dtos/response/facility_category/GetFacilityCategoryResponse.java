package com.mascara.oyo_booking_backend.dtos.response.facility_category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 09/12/2023
 * Time      : 6:09 CH
 * Filename  : GetFacilityCategoryResponse
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetFacilityCategoryResponse {
    private Long id;
    private String faciCateName;
    private String faciCateCode;
    private String description;
    private String status;
}
