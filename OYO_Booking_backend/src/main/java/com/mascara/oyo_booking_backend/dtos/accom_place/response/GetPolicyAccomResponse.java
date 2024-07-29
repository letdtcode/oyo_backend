package com.mascara.oyo_booking_backend.dtos.accom_place.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 04/04/2024
 * Time      : 6:51 CH
 * Filename  : GetPolicyAccomResponse
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetPolicyAccomResponse {
    private CancellationPolicy cancellationPolicy;
    private GeneralPolicy generalPolicy;

    public GetPolicyAccomResponse(String cancelPolicyCode,
                                  Integer cancellationFeeRate,
                                  Boolean allowEvent,
                                  Boolean allowPet,
                                  Boolean allowSmoking) {
        this.cancellationPolicy = CancellationPolicy.builder()
                .code(cancelPolicyCode)
                .cancellationFeeRate(cancellationFeeRate)
                .build();
        this.generalPolicy = GeneralPolicy.builder()
                .allowEvent(allowEvent)
                .allowPet(allowPet)
                .allowSmoking(allowSmoking)
                .build();
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class CancellationPolicy {
        private String code;
        private Integer cancellationFeeRate;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class GeneralPolicy {
        private Boolean allowEvent;
        private Boolean allowPet;
        private Boolean allowSmoking;
    }
}
