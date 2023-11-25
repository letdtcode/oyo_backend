package com.mascara.oyo_booking_backend.services.accom_category;

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
    BasePagingData<GetAccomCategoryResponse> getAllAccomCategoryWithPaging(Integer pageNum,Integer pageSize);

    @Transactional
    List<GetAccomCategoryResponse> getAllAccomCategory();

    String addAccomCategory(AddAccomCategoryRequest request);

    String updateAccomCategory(UpdateAccomCategoryRequest request, Long id);

    @Transactional
    String changeStatusAccomCategory(Long id, String status);

    @Transactional
    String deleteAccomCategory(Long id);
}
