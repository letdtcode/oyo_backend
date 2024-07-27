package com.mascara.oyo_booking_backend.common.mapper.surcharge;

import com.mascara.oyo_booking_backend.dtos.surcharge.surcharge_category.response.GetSurchargeCategoryResponse;
import com.mascara.oyo_booking_backend.entities.surcharge.SurchargeCategory;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 17/06/2024
 * Time      : 6:05 CH
 * Filename  : SurchargeCategoryMapper
 */

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SurchargeCategoryMapper {

    GetSurchargeCategoryResponse toGetSurchargeCategoryResponse(SurchargeCategory surchargeCategory);
}
