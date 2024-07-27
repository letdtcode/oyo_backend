package com.mascara.oyo_booking_backend.common.mapper.accommodation;

import com.mascara.oyo_booking_backend.dtos.accom_category.response.GetAccomCategoryResponse;
import com.mascara.oyo_booking_backend.entities.accommodation.AccommodationCategories;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 17/06/2024
 * Time      : 5:58 CH
 * Filename  : AccommodationCategoriesMapper
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccommodationCategoriesMapper {

    GetAccomCategoryResponse toGetAccomCategoryResponse(AccommodationCategories categories);
}
