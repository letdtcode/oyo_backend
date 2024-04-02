package com.mascara.oyo_booking_backend.services.wish;

import com.mascara.oyo_booking_backend.dtos.base.BaseMessageData;
import com.mascara.oyo_booking_backend.dtos.accom_place.response.GetAccomPlaceResponse;
import com.mascara.oyo_booking_backend.dtos.base.BasePagingData;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 27/11/2023
 * Time      : 4:46 CH
 * Filename  : WishService
 */
public interface WishService {
    @Transactional
    BaseMessageData<Boolean> checkAccomPlaceIsWishOfUser(Long accomId, String mailUser);

    @Transactional

    BaseMessageData<String> handleWishItemOfUser(Long accomId, String mailUser);

    @Transactional
    BasePagingData<GetAccomPlaceResponse> getListWishItemOfUser(Integer pageNum,
                                                                Integer pageSize,
                                                                String sortType,
                                                                String field,
                                                                String userMail);
}
