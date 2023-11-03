package com.mascara.oyo_booking_backend.services.accom_place;

import com.mascara.oyo_booking_backend.dtos.request.accom_place.AddAccomPlaceRequest;
import com.mascara.oyo_booking_backend.dtos.request.accom_place.GetAccomPlaceFilterRequest;
import com.mascara.oyo_booking_backend.dtos.response.accommodation.GetAccomPlaceResponse;
import com.mascara.oyo_booking_backend.dtos.response.general.MessageResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 22/10/2023
 * Time      : 7:40 CH
 * Filename  : AccomPlaceService
 */
public interface AccomPlaceService {
    @Transactional
    MessageResponse addAccomPlace(AddAccomPlaceRequest request, String mail);

    @Transactional
    List<GetAccomPlaceResponse> getAllAccomPlaceWithPaging(Integer pageNum, Integer pageSize);

    @Transactional
    List<GetAccomPlaceResponse> getAccomPlaceFilterWithPaging(GetAccomPlaceFilterRequest filter, Integer pageNum, Integer pageSize);

    @Transactional
    GetAccomPlaceResponse getAccomPlaceDetails(Long id);
}
