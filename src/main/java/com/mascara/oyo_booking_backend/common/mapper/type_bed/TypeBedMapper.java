package com.mascara.oyo_booking_backend.common.mapper.type_bed;

import com.mascara.oyo_booking_backend.dtos.accom_place.response.TypeBedOfRoom;
import com.mascara.oyo_booking_backend.dtos.type_bed.response.GetTypeBedResponse;
import com.mascara.oyo_booking_backend.entities.type_bed.TypeBed;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 17/06/2024
 * Time      : 6:05 CH
 * Filename  : TypeBedMapper
 */

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TypeBedMapper {
    TypeBedOfRoom toTypeBedOfRoom(TypeBed typeBed);

    GetTypeBedResponse toGetTypeBedResponse(TypeBed typeBed);
}
