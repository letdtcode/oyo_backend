package com.mascara.oyo_booking_backend.services.facility;

import com.mascara.oyo_booking_backend.dtos.BaseMessageData;
import com.mascara.oyo_booking_backend.dtos.request.facility.AddFacilityRequest;
import com.mascara.oyo_booking_backend.dtos.request.facility.UpdateFacilityRequest;
import com.mascara.oyo_booking_backend.dtos.response.facility.GetFacilityResponse;
import com.mascara.oyo_booking_backend.dtos.response.paging.BasePagingData;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 22/10/2023
 * Time      : 7:37 CH
 * Filename  : FacilAccomService
 */
public interface FacilityService {

    @Transactional
    BasePagingData<GetFacilityResponse> getAllFacilityWithPaging(Integer pageNum, Integer pageSize, String sortType, String field);

    @Transactional
    GetFacilityResponse addFacility(AddFacilityRequest request);

    @Transactional
    GetFacilityResponse updateFacility(UpdateFacilityRequest request, Long id);

    @Transactional
    BaseMessageData changeStatusFacility(Long id, String status);

    @Transactional
    BaseMessageData deletedFacility(Long id);
}
