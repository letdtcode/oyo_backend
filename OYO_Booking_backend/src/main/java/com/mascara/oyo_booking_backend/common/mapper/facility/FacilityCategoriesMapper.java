package com.mascara.oyo_booking_backend.common.mapper.facility;

import com.mascara.oyo_booking_backend.dtos.facility_category.response.GetFacilityCategorWithFacilityListResponse;
import com.mascara.oyo_booking_backend.dtos.facility_category.response.GetFacilityCategoryResponse;
import com.mascara.oyo_booking_backend.entities.facility.FacilityCategories;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 17/06/2024
 * Time      : 6:03 CH
 * Filename  : FacilityCategoriesMapper
 */

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FacilityCategoriesMapper {

    GetFacilityCategoryResponse toGetFacilityCategoryResponse(FacilityCategories facilityCategories);

    GetFacilityCategorWithFacilityListResponse toGetFacilityCategorWithFacilityListResponse(FacilityCategories facilityCategories);
}
