package com.mascara.oyo_booking_backend.dtos.accom_place.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 03/04/2024
 * Time      : 11:05 CH
 * Filename  : PercentCreateAccomRespons
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PercentCreateAccomResponse {
    private Long accomId;
    private Boolean isDoneGeneralInfo;
    private Boolean isDoneAddress;
    private Boolean isDoneFacility;
    private Boolean isDoneGallery;
    private Boolean isDoneRoomSetting;
    private Boolean isDonePolicy;
    private Boolean isDonePayment;
    private Integer percent;
}
