package com.mascara.oyo_booking_backend.services.facility_category;

import com.mascara.oyo_booking_backend.dtos.base.BaseMessageData;
import com.mascara.oyo_booking_backend.dtos.facility_category.request.AddFacilityCategoryRequest;
import com.mascara.oyo_booking_backend.dtos.facility_category.request.UpdateFacilityCategoryRequest;
import com.mascara.oyo_booking_backend.dtos.facility_category.response.GetFacilityCategorWithFacilityListResponse;
import com.mascara.oyo_booking_backend.dtos.facility_category.response.GetFacilityCategoryResponse;
import com.mascara.oyo_booking_backend.dtos.base.BasePagingData;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 22/10/2023
 * Time      : 7:36 CH
 * Filename  : FacilityAccomCategoryService
 */
public interface FacilityCategoryService {
    @Transactional
    BasePagingData<GetFacilityCategorWithFacilityListResponse> getAllFacilityCategoryHaveFacilityListWithPaging(Integer pageNum, Integer pageSize, String sortType, String field);

    @Transactional
    BasePagingData<GetFacilityCategoryResponse> getAllFacilityCategoryWithPaging(Integer pageNum, Integer pageSize, String sortType, String field);

    @Transactional
    List<GetFacilityCategorWithFacilityListResponse> getAllDataFacility();

    @Transactional
    GetFacilityCategoryResponse addFacilityCategory(AddFacilityCategoryRequest request);

    @Transactional
    GetFacilityCategoryResponse updateFacilityCategory(UpdateFacilityCategoryRequest request, Long id);

    @Transactional
    BaseMessageData changeStatusFacilityCategory(Long id, String status);

    @Transactional
    BaseMessageData deletedFacilityCategory(Long id);
}
