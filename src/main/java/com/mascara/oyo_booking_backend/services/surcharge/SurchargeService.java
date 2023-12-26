package com.mascara.oyo_booking_backend.services.surcharge;

import com.mascara.oyo_booking_backend.dtos.BaseMessageData;
import com.mascara.oyo_booking_backend.dtos.request.surcharge_category.AddSurchargeCategoryRequest;
import com.mascara.oyo_booking_backend.dtos.request.surcharge_category.UpdateSurchargeCategoryRequest;
import com.mascara.oyo_booking_backend.dtos.response.paging.BasePagingData;
import com.mascara.oyo_booking_backend.dtos.response.surcharge.GetSurchargeCategoryResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 07/12/2023
 * Time      : 8:48 CH
 * Filename  : SurchargeService
 */
public interface SurchargeService {
    @Transactional
    BasePagingData<GetSurchargeCategoryResponse> getAllSurchargeCategoryWithPaging(Integer pageNum, Integer pageSize, String sortType, String field);

    List<GetSurchargeCategoryResponse> getAllSurchargeCategoryByStatus(String status);

    @Transactional
    GetSurchargeCategoryResponse addSurchargeCategory(AddSurchargeCategoryRequest request);

    @Transactional
    GetSurchargeCategoryResponse updateSurchargeCategory(UpdateSurchargeCategoryRequest request, Long id);

    @Transactional
    BaseMessageData changeStatusSurchargeCategory(Long id, String status);

    @Transactional
    BaseMessageData deletedSurchargeCategory(Long id);
}
