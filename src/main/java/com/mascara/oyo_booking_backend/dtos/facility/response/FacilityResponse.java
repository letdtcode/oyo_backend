package com.mascara.oyo_booking_backend.dtos.facility.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 05/04/2024
 * Time      : 12:14 CH
 * Filename  : FacilityResponse
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FacilityResponse {
    private Long id;
    private String facilityName;
    private String facilityCode;
    private String status;
}