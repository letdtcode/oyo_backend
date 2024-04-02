package com.mascara.oyo_booking_backend.services.type_bed;

import com.mascara.oyo_booking_backend.dtos.base.BaseMessageData;
import com.mascara.oyo_booking_backend.dtos.type_bed.request.AddTypeBedRequest;
import com.mascara.oyo_booking_backend.dtos.type_bed.request.UpdateTypeBedRequest;
import com.mascara.oyo_booking_backend.dtos.base.BasePagingData;
import com.mascara.oyo_booking_backend.dtos.type_bed.response.GetTypeBedResponse;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 25/11/2023
 * Time      : 5:25 CH
 * Filename  : TypeBedService
 */
public interface TypeBedService {
    @Transactional
    BasePagingData<GetTypeBedResponse> getAllTypeBedWithPaging(Integer pageNum, Integer pageSize, String sortType, String field);

    @Transactional
    BasePagingData<GetTypeBedResponse> getAllTypeBedWithPagingByStatus(String status, Integer pageNum, Integer pageSize, String sortType, String field);

    @Transactional
    GetTypeBedResponse addTypeBed(AddTypeBedRequest request);

    @Transactional
    GetTypeBedResponse updateTypeBed(UpdateTypeBedRequest request, Long id);

    @Transactional
    BaseMessageData changeStatusTypeBed(Long id, String status);

    @Transactional
    BaseMessageData deletedTypeBed(Long id);
}
