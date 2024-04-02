package com.mascara.oyo_booking_backend.services.review;

import com.mascara.oyo_booking_backend.dtos.base.BaseMessageData;
import com.mascara.oyo_booking_backend.dtos.review.request.ReviewBookingRequest;
import com.mascara.oyo_booking_backend.dtos.review.response.GetReviewResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 03/11/2023
 * Time      : 2:01 SA
 * Filename  : ReviewService
 */
public interface ReviewService {
    @Transactional
    List<GetReviewResponse> getReviewListOfAccomPlace(Long id);

    @Transactional
    BaseMessageData createReviewForBooking(ReviewBookingRequest request, String userMail);
}
