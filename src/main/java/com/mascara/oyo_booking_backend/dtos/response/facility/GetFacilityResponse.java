package com.mascara.oyo_booking_backend.dtos.response.facility;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 03/11/2023
 * Time      : 12:22 SA
 * Filename  : GetFacilityResponse
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetFacilityResponse {
    private Long id;
    private String facilityName;
    private String facilityCode;
    private String facilityCateCode;
    private String facilityCateName;
    private String imageUrl;
    private String status;
}
