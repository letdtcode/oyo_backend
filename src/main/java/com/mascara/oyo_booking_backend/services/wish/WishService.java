package com.mascara.oyo_booking_backend.services.wish;

import com.mascara.oyo_booking_backend.dtos.BaseMessageData;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 27/11/2023
 * Time      : 4:46 CH
 * Filename  : WishService
 */
public interface WishService {
    BaseMessageData<Boolean> checkAccomPlaceIsWishOfUser(Long accomId, String mailUser);
}
