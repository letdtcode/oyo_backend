package com.mascara.oyo_booking_backend.dtos.accom_place.response;

import com.mascara.oyo_booking_backend.dtos.facility.response.FacilityResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 04/04/2024
 * Time      : 6:50 CH
 * Filename  : GetFacilityAccomResponse
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetFacilityAccomResponse {
    private Integer total;
    private List<FacilityResponse> facilities;

//    @Data
//    @AllArgsConstructor
//    @NoArgsConstructor
//    @Builder
//    class FacilityResponse {
//        private Long id;
//        private String facilityName;
//        private String facilityCode;
//        private String status;
//    }
}
