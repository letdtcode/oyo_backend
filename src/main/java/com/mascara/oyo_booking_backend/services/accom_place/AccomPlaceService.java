package com.mascara.oyo_booking_backend.services.accom_place;

import com.mascara.oyo_booking_backend.dtos.accom_place.request.*;
import com.mascara.oyo_booking_backend.dtos.accom_place.response.AccomPlaceWaitingResponse;
import com.mascara.oyo_booking_backend.dtos.accom_place.response.GetAccomPlaceDetailResponse;
import com.mascara.oyo_booking_backend.dtos.accom_place.response.GetAccomPlaceResponse;
import com.mascara.oyo_booking_backend.dtos.accom_place.response.PercentCreateAccomResponse;
import com.mascara.oyo_booking_backend.dtos.base.BaseMessageData;
import com.mascara.oyo_booking_backend.dtos.base.BasePagingData;
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
    Long registerAccomPlace(String accomCateName, String mailPartner);

    @Transactional
    BaseMessageData updateGeneralInfo(UpdateGeneralInfoRequest request, Long accomId);

    @Transactional
    BaseMessageData updateAddress(UpdateAddressAccomRequest request, Long accomId);

    @Transactional
    BaseMessageData updateFacilities(UpdateFacilityAccomRequest request, Long accomId);

    @Transactional
    BaseMessageData updateImages(UpdateImageAccomRequest request, Long accomId);

    @Transactional
    BaseMessageData updateRooms(UpdateRoomAccomRequest request, Long accomId);

    @Transactional
    BaseMessageData updatePolicy(UpdatePolicyAccomRequest request, Long accomId);

    @Transactional
    BaseMessageData updatePayment(UpdatePaymentAccomRequest request, Long accomId);

    void checkPermission(String mailPartner, Long accomId);

    PercentCreateAccomResponse getPercentCreateAccom(Long accomId);

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
    BasePagingData<GetAccomPlaceResponse> getListAccomPlaceApprovedOfPartner(String hostMail,
                                                                             Integer pageNum,
                                                                             Integer pageSize,
                                                                             String sortType,
                                                                             String field);

    @Transactional
    BasePagingData<AccomPlaceWaitingResponse> getListAccomPlaceWaitingOfPartner(String hostMail,
                                                                                Integer pageNum,
                                                                                Integer pageSize,
                                                                                String sortType,
                                                                                String field);

    @Transactional
    BaseMessageData changeStatusAccomPlace(Long id, String status);

    @Transactional
    BaseMessageData deleteAccomPlace(Long id);
}
