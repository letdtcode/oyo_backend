package com.mascara.oyo_booking_backend.common.mapper.facility;

import com.mascara.oyo_booking_backend.dtos.facility.response.FacilityResponse;
import com.mascara.oyo_booking_backend.dtos.facility.response.GetFacilityResponse;
import com.mascara.oyo_booking_backend.entities.facility.Facility;
import com.mascara.oyo_booking_backend.common.mapper.helper.FacilityHelperMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 17/06/2024
 * Time      : 6:03 CH
 * Filename  : FacilityMapper
 */

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {FacilityHelperMapper.class})
public interface FacilityMapper {
    FacilityResponse toFacilityResponse(Facility facility);

    @Mapping(source = "facilityCateCode", target = "facilityCateName", qualifiedByName = "facilityCateCodeToFacilityCateName")
    GetFacilityResponse toGetFacilityResponse(Facility facility);
}
