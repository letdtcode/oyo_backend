package com.mascara.oyo_booking_backend.services.type_bed;

import com.mascara.oyo_booking_backend.dtos.BaseMessageData;
import com.mascara.oyo_booking_backend.dtos.request.type_bed.AddTypeBedRequest;
import com.mascara.oyo_booking_backend.dtos.request.type_bed.UpdateTypeBedRequest;
import com.mascara.oyo_booking_backend.dtos.response.paging.BasePagingData;
import com.mascara.oyo_booking_backend.dtos.response.type_bed.GetTypeBedResponse;
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
    BaseMessageData addTypeBed(AddTypeBedRequest request);

    @Transactional
    BaseMessageData updateTypeBed(UpdateTypeBedRequest request, Long id);

    @Transactional
    BaseMessageData changeStatusTypeBed(Long id, String status);

    @Transactional
    BaseMessageData deletedTypeBed(Long id);
}
