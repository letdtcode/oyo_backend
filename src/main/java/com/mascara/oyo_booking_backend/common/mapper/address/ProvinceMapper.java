package com.mascara.oyo_booking_backend.common.mapper.address;

import com.mascara.oyo_booking_backend.dtos.location.response.locationDTO.GetProvinceResponse;
import com.mascara.oyo_booking_backend.dtos.location.response.locationDTO.UpdateProvinceResponse;
import com.mascara.oyo_booking_backend.entities.address.Province;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 17/06/2024
 * Time      : 6:01 CH
 * Filename  : ProvinceMapper
 */

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProvinceMapper {

    UpdateProvinceResponse toUpdateProvinceResponse(Province province);

    GetProvinceResponse toGetProvinceResponse(Province province);
}
