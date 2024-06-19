package com.mascara.oyo_booking_backend.mapper.review;

import com.mascara.oyo_booking_backend.dtos.review.request.ReviewBookingRequest;
import com.mascara.oyo_booking_backend.dtos.review.response.GetReviewResponse;
import com.mascara.oyo_booking_backend.entities.review.Review;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 17/06/2024
 * Time      : 6:04 CH
 * Filename  : ReviewMapper
 */

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReviewMapper {

    GetReviewResponse toGetReviewResponse(Review review);

    Review toEntity(ReviewBookingRequest reviewBookingRequest);
}
