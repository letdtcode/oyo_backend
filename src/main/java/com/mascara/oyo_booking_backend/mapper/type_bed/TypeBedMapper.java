package com.mascara.oyo_booking_backend.mapper.type_bed;

import com.mascara.oyo_booking_backend.dtos.accom_place.response.TypeBedOfRoom;
import com.mascara.oyo_booking_backend.entities.type_bed.TypeBed;
import org.mapstruct.Mapper;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 17/06/2024
 * Time      : 6:05 CH
 * Filename  : TypeBedMapper
 */

@Mapper(componentModel = "spring")
public interface TypeBedMapper {
    TypeBedOfRoom toTypeBedOfRoom(TypeBed typeBed);
}
