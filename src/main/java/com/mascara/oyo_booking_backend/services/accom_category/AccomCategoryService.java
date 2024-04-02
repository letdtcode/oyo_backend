package com.mascara.oyo_booking_backend.services.accom_category;

import com.mascara.oyo_booking_backend.dtos.base.BaseMessageData;
import com.mascara.oyo_booking_backend.dtos.accom_category.request.AddAccomCategoryRequest;
import com.mascara.oyo_booking_backend.dtos.accom_category.request.UpdateAccomCategoryRequest;
import com.mascara.oyo_booking_backend.dtos.accom_category.response.GetAccomCategoryResponse;
import com.mascara.oyo_booking_backend.dtos.base.BasePagingData;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 22/10/2023
 * Time      : 7:39 CH
 * Filename  : AccomCategoryService
 */
public interface AccomCategoryService {

    @Transactional
    BasePagingData<GetAccomCategoryResponse> getAllAccomCategoryWithPaging(Integer pageNum, Integer pageSize, String sortType, String field);

    @Transactional
    List<GetAccomCategoryResponse> getAllAccomCategory();

    GetAccomCategoryResponse addAccomCategory(AddAccomCategoryRequest request);

    GetAccomCategoryResponse updateAccomCategory(UpdateAccomCategoryRequest request, Long id);

    @Transactional
    BaseMessageData changeStatusAccomCategory(Long id, String status);

    @Transactional
    BaseMessageData deleteAccomCategory(Long id);
}
