package com.mascara.oyo_booking_backend.services.accom_place;

import com.mascara.oyo_booking_backend.dtos.request.accommodation.AddAccommodationRequest;
import com.mascara.oyo_booking_backend.dtos.response.general.MessageResponse;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 22/10/2023
 * Time      : 7:40 CH
 * Filename  : AccomPlaceService
 */
public interface AccomPlaceService {
    @Transactional
    MessageResponse addAccomPlace(AddAccommodationRequest request, String mail);
}
