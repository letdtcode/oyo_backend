package com.mascara.oyo_booking_backend.services.facility;

import com.mascara.oyo_booking_backend.dtos.response.facility.GetFacilityCategoryResponse;
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
    List<GetFacilityCategoryResponse> getAllDataFacility();
}
