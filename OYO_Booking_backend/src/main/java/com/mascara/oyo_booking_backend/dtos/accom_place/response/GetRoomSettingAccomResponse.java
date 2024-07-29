package com.mascara.oyo_booking_backend.dtos.accom_place.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 04/04/2024
 * Time      : 6:50 CH
 * Filename  : GetRoomSettingAccomResponse
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetRoomSettingAccomResponse {
    private BedRoomResponse bedRooms;
    private Integer numBathRoom;
    private Integer numKitchen;
    private Integer numBedRoom;
    private String accomCateName;
    private Integer numPeople;
    private Long accomCateId;
}
