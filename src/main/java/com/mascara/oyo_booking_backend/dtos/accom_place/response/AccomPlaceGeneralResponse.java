package com.mascara.oyo_booking_backend.dtos.accom_place.response;

import com.mascara.oyo_booking_backend.enums.homestay.AccomStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 04/04/2024
 * Time      : 12:15 SA
 * Filename  : AccomPlaceWaitingResponse
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccomPlaceGeneralResponse {
    private Long accomId;
    private String accomName;
    private String logo;
    private Integer progress;
    private String addressDetail;
    private AccomStatusEnum status;
}
