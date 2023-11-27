package com.mascara.oyo_booking_backend.services.accom_category;

import com.mascara.oyo_booking_backend.dtos.BaseMessageData;
import com.mascara.oyo_booking_backend.dtos.request.accom_category.AddAccomCategoryRequest;
import com.mascara.oyo_booking_backend.dtos.request.accom_category.UpdateAccomCategoryRequest;
import com.mascara.oyo_booking_backend.dtos.response.accom_category.GetAccomCategoryResponse;
import com.mascara.oyo_booking_backend.dtos.response.paging.BasePagingData;
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

    BaseMessageData addAccomCategory(AddAccomCategoryRequest request);

    BaseMessageData updateAccomCategory(UpdateAccomCategoryRequest request, Long id);

    @Transactional
    BaseMessageData changeStatusAccomCategory(Long id, String status);

    @Transactional
    BaseMessageData deleteAccomCategory(Long id);
}
