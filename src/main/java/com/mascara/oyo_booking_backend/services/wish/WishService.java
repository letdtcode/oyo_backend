package com.mascara.oyo_booking_backend.services.wish;

import com.mascara.oyo_booking_backend.dtos.BaseMessageData;
import com.mascara.oyo_booking_backend.dtos.response.accommodation.GetAccomPlaceResponse;
import com.mascara.oyo_booking_backend.dtos.response.paging.BasePagingData;
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
