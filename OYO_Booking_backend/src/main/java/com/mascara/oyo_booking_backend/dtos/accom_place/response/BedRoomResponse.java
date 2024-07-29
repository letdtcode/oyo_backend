package com.mascara.oyo_booking_backend.dtos.accom_place.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 05/04/2024
 * Time      : 12:25 CH
 * Filename  : BedRooms
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BedRoomResponse {
    private Integer total;
    private List<TypeBedOfRoom> typeBeds;
}