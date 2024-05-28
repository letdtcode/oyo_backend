package com.mascara.oyo_booking_backend.dtos.accom_place.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 04/04/2024
 * Time      : 6:47 CH
 * Filename  : GetAddressAccomResponse
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAddressAccomResponse {
    private String numHouseAndStreetName;
    private DistrictAddress districtAddress;
    private WardAddress wardAddress;
    private ProvinceAddress provinceAddress;
    private Double longitude;
    private Double latitude;

    private String guide;

    public GetAddressAccomResponse(String numHouseAndStreetName,
                                   String districtCode,
                                   String districtName,
                                   String wardCode,
                                   String wardName,
                                   String provinceCode,
                                   String provinceName,
                                   Double longitude,
                                   Double latitude,
                                   String guide) {
        this.numHouseAndStreetName = numHouseAndStreetName;

        this.districtAddress = DistrictAddress.builder()
                .districtCode(districtCode)
                .districtName(districtName)
                .build();
        this.wardAddress = WardAddress.builder()
                .wardCode(wardCode)
                .wardName(wardName)
                .build();
        this.provinceAddress = ProvinceAddress.builder()
                .provinceCode(provinceCode)
                .provinceName(provinceName)
                .build();
        this.longitude = longitude;
        this.latitude = latitude;
        this.guide = guide;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class DistrictAddress {
        private String districtCode;
        private String districtName;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class WardAddress {
        private String wardCode;
        private String wardName;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class ProvinceAddress {
        private String provinceCode;
        private String provinceName;
    }
}
