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
    Long registrationAccomPlace(Long categoryId, String mailPartner);

    @Transactional
    GetAccomPlaceResponse addAccomPlace(AddAccomPlaceRequest request, String mailPartner);

    GetAccomPlaceResponse addImageAccomPlace(List<MultipartFile> files, Long id, String hostMail);

    @Transactional
    BasePagingData<GetAccomPlaceResponse> getAllAccomPlaceWithPaging(Integer pageNum, Integer pageSize, String sortType, String field);

    @Transactional
    BasePagingData<GetAccomPlaceResponse> getFilterByKeyWord(String keyword, Integer pageNum, Integer pageSize, String sortType, String field);

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
    GetAccomPlaceDetailResponse updateTitleAccom(UpdateTitleAccomRequest request, Long accomId);

    @Transactional
    GetAccomPlaceDetailResponse updateFacilityAccom(UpdateFacilityAccomRequest request, Long accomId);

    @Transactional
    GetAccomPlaceDetailResponse updateRoomAccom(UpdateRoomAccomRequest request, Long accomId);

    @Transactional
    GetAccomPlaceDetailResponse updateImageAccom(UpdateImageAccomRequest request, Long accomId);

    @Transactional
    GetAccomPlaceDetailResponse updateVideoAccom(UpdateVideoAccomRequest request, Long accomId);

    @Transactional
    GetAccomPlaceDetailResponse updateAddressAccom(UpdateAddressAccomRequest request, Long accomId);

    @Transactional
    GetAccomPlaceDetailResponse updateSurchargeAccom(UpdateSurchargeAccomRequest request, Long accomId);

    @Transactional
    GetAccomPlaceDetailResponse changePriceAccom(Double pricePerNight, Long accomId);

    @Transactional
    GetAccomPlaceDetailResponse updateDiscountAccom(Double discountPercent, Long accomId);

    @Transactional
    GetAccomPlaceDetailResponse updateCancellationPolicy(UpdateCancellationPolicyRequest request, Long accomId, String partnerMail);
}
