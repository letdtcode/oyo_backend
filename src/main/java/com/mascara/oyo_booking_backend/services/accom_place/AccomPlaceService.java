package com.mascara.oyo_booking_backend.services.accom_place;

import com.mascara.oyo_booking_backend.dtos.accom_place.request.*;
import com.mascara.oyo_booking_backend.dtos.accom_place.response.*;
import com.mascara.oyo_booking_backend.dtos.base.BaseMessageData;
import com.mascara.oyo_booking_backend.dtos.base.BasePagingData;
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
    Long registerAccomPlace(String accomCateName, String mailPartner);

    @Transactional
    BaseMessageData requestApproval(Long accomId, String mailPartner);

    @Transactional
    BaseMessageData updateGeneralInfo(UpdateGeneralInfoRequest request, Long accomId);

    @Transactional
    BaseMessageData updateAddress(UpdateAddressAccomRequest request, Long accomId);

    @Transactional
    BaseMessageData updateFacilities(UpdateFacilityAccomRequest request, Long accomId);

    @Transactional
    BaseMessageData updateGallery(UpdateGalleryAccomRequest request, Long accomId);

    @Transactional
    BaseMessageData updateRooms(UpdateRoomAccomRequest request, Long accomId);

    @Transactional
    BaseMessageData updatePolicy(UpdatePolicyAccomRequest request, Long accomId);

    @Transactional
    BaseMessageData updatePayment(UpdatePaymentAccomRequest request, Long accomId);

    @Transactional
    BaseMessageData updatePriceCustom(List<UpdatePriceCustomAccomPlaceRequest> updatePriceCustomAccomPlaceRequests);

    void checkPermission(String mailPartner, Long accomId);

    PercentCreateAccomResponse getPercentCreateAccom(Long accomId);

    @Transactional
    BasePagingData<GetAccomPlaceResponse> getAllAccomPlaceWithPaging(Integer pageNum, Integer pageSize, String sortType, String field);

    @Transactional
    BasePagingData<GetAccomPlaceResponse> getFilterByKeyWord(String keyword, Integer pageNum, Integer pageSize, String sortType, String field);

    @Transactional
    BasePagingData<GetAccomPlaceResponse> getAccomPlaceFilterWithPaging(GetAccomPlaceFilterRequest filter, Integer pageNum, Integer pageSize, String sortType, String field);

    @Transactional
    GetAccomPlaceDetailResponse getAccomPlaceApprovedDetails(Long id);

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
    BasePagingData<GetPriceCustomAccomResponse> getListPriceCustomAccomOfPartner(String hostMail,
                                                                                 Integer pageNum,
                                                                                 Integer pageSize,
                                                                                 String sortType,
                                                                                 String field);

//    @Transactional
//    BasePagingData<GetRangeDateBookingAccomResponse> getListRangeDateAccomOfPartner(String hostMail,
//                                                                                    Integer pageNum,
//                                                                                    Integer pageSize,
//                                                                                    String sortType,
//                                                                                    String field);

    @Transactional
    BasePagingData<AccomPlaceGeneralResponse> getAllAcommPlaceWaitingApprovalWithPaging(Integer pageNum,
                                                                                        Integer pageSize,
                                                                                        String sortType,
                                                                                        String field);

    @Transactional
    BasePagingData<AccomPlaceGeneralResponse> getListAccomPlaceWaitingOfPartner(String hostMail,
                                                                                Integer pageNum,
                                                                                Integer pageSize,
                                                                                String sortType,
                                                                                String field);

    @Transactional
    BaseMessageData changeStatusAccomPlace(Long id, String status);

    BaseMessageData approveAccomPlace(Long id);

    @Transactional
    BaseMessageData deleteAccomPlace(Long id);

    GetGeneralInfoAccomResponse getGeneralInfoAccom(Long accomId);

    GetAddressAccomResponse getAddressAccom(Long accomId);

    GetFacilityAccomResponse getFacilityAccom(Long accomId);

    GetGalleryAccomResponse getGalleryAccom(Long accomId);

    GetRoomSettingAccomResponse getRoomSettingAccom(Long accomId);

    GetPolicyAccomResponse getPolicyAccom(Long accomId);

    GetPaymentAccomResponse getPaymentAccom(Long accomId);
}
