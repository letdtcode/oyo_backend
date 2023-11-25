package com.mascara.oyo_booking_backend.services.facility;

import com.mascara.oyo_booking_backend.dtos.request.facility.AddFacilityRequest;
import com.mascara.oyo_booking_backend.dtos.request.facility.UpdateFacilityRequest;
import com.mascara.oyo_booking_backend.dtos.response.facility.GetFacilityCategoryResponse;
import com.mascara.oyo_booking_backend.dtos.response.paging.BasePagingData;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 22/10/2023
 * Time      : 7:37 CH
 * Filename  : FacilAccomService
 */
public interface FacilityService {

    @Transactional
    String addFacility(AddFacilityRequest request);

    @Transactional
    String updateFacility(UpdateFacilityRequest request, Long id);

    @Transactional
    String changeStatusFacility(Long id, String status);

    @Transactional
    String deletedFacility(Long id);
}
