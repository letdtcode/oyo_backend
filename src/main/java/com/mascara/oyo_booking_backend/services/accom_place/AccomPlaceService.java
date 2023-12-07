package com.mascara.oyo_booking_backend.services.accom_place;

import com.mascara.oyo_booking_backend.dtos.BaseMessageData;
import com.mascara.oyo_booking_backend.dtos.request.accom_place.*;
import com.mascara.oyo_booking_backend.dtos.response.accommodation.GetAccomPlaceDetailResponse;
import com.mascara.oyo_booking_backend.dtos.response.accommodation.GetAccomPlaceResponse;
import com.mascara.oyo_booking_backend.dtos.response.paging.BasePagingData;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
    GetAccomPlaceResponse addAccomPlace(AddAccomPlaceRequest request);

    GetAccomPlaceResponse addImageAccomPlace(List<MultipartFile> files, Long id, String hostMail);

    @Transactional
    BasePagingData<GetAccomPlaceResponse> getAllAccomPlaceWithPaging(Integer pageNum, Integer pageSize, String sortType, String field);

    @Transactional
    BasePagingData<GetAccomPlaceResponse> getAccomPlaceFilterWithPaging(GetAccomPlaceFilterRequest filter, Integer pageNum, Integer pageSize, String sortType, String field);

    @Transactional
    GetAccomPlaceDetailResponse getAccomPlaceDetails(Long id);

    @Transactional
    BasePagingData<GetAccomPlaceResponse> getTopAccomPlaceByField(Integer pageNum, Integer pageSize, String sortType, String field);

    @Transactional
    BasePagingData<GetAccomPlaceResponse> getListAccomPlaceOfPartner(String hostMail, Integer pageNum, Integer pageSize, String sortType, String field);

    @Transactional
    BaseMessageData changeStatusAccomPlace(Long id, String status);

    @Transactional
    BaseMessageData deleteAccomPlace(Long id);

    @Transactional
    BaseMessageData updateTitleAccom(UpdateTitleAccomRequest request, Long accomId);

    @Transactional
    BaseMessageData updateFacilityAccom(UpdateFacilityAccomRequest request, Long accomId);

    @Transactional
    BaseMessageData updateRoomAccom(UpdateRoomAccomRequest request, Long accomId);

    @Transactional
    BaseMessageData updateImageAccom(UpdateImageAccomRequest request, Long accomId);

    @Transactional
    BaseMessageData updateAddressAccom(UpdateAddressAccomRequest request, Long accomId);

    @Transactional
    BaseMessageData updateSurchargeAccom(UpdateSurchargeAccomRequest request, Long accomId);

    @Transactional
    BaseMessageData changePriceAccom(Double pricePerNight, Long accomId);

    @Transactional
    BaseMessageData updateDiscountAccom(Double discountPercent, Long accomId);
}
