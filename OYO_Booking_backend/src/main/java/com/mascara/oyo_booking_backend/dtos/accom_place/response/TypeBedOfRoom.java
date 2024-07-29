package com.mascara.oyo_booking_backend.dtos.accom_place.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 05/04/2024
 * Time      : 12:26 CH
 * Filename  : TypeBedOfRoom
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TypeBedOfRoom {
    private Long id;
    private String typeBedName;
    private String typeBedCode;
}