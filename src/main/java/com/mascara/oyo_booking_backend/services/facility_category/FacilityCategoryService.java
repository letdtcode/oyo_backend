package com.mascara.oyo_booking_backend.services.facility_category;

import com.mascara.oyo_booking_backend.dtos.BaseMessageData;
import com.mascara.oyo_booking_backend.dtos.request.facility_category.AddFacilityCategoryRequest;
import com.mascara.oyo_booking_backend.dtos.request.facility_category.UpdateFacilityCategoryRequest;
import com.mascara.oyo_booking_backend.dtos.response.facility_category.GetFacilityCategorWithFacilityListResponse;
import com.mascara.oyo_booking_backend.dtos.response.facility_category.GetFacilityCategoryResponse;
import com.mascara.oyo_booking_backend.dtos.response.paging.BasePagingData;
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
