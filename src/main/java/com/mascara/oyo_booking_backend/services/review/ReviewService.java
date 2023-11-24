package com.mascara.oyo_booking_backend.services.review;

import com.mascara.oyo_booking_backend.dtos.request.review.ReviewAccomPlaceRequest;
import com.mascara.oyo_booking_backend.dtos.response.general.MessageResponse;
import com.mascara.oyo_booking_backend.dtos.response.review.GetReviewResponse;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
    String reviewAccomPlace(ReviewAccomPlaceRequest request, List<MultipartFile> imageReviewFiles);
}
