package com.mascara.oyo_booking_backend.services.review;

import com.mascara.oyo_booking_backend.dtos.base.BaseMessageData;
import com.mascara.oyo_booking_backend.dtos.review.request.ReviewBookingRequest;
import com.mascara.oyo_booking_backend.dtos.review.response.GetReviewResponse;
import com.mascara.oyo_booking_backend.entities.accommodation.AccomPlace;
import com.mascara.oyo_booking_backend.entities.authentication.User;
import com.mascara.oyo_booking_backend.entities.booking.Booking;
import com.mascara.oyo_booking_backend.entities.review.ImageReview;
import com.mascara.oyo_booking_backend.entities.review.Review;
import com.mascara.oyo_booking_backend.entities.review.ReviewList;
import com.mascara.oyo_booking_backend.enums.booking.BookingStatusEnum;
import com.mascara.oyo_booking_backend.enums.CommonStatusEnum;
import com.mascara.oyo_booking_backend.exceptions.ResourceNotFoundException;
import com.mascara.oyo_booking_backend.mapper.review.ReviewMapper;
import com.mascara.oyo_booking_backend.repositories.*;
import com.mascara.oyo_booking_backend.utils.AppContants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 03/11/2023
 * Time      : 2:00 SA
 * Filename  : ReviewService
 */
@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    public final UserRepository userRepository;
    private final AccomPlaceRepository accomPlaceRepository;
    private final ReviewListRepository reviewListRepository;
    private final ImageReviewRepository imageReviewRepository;
    private final BookingRepository bookingRepository;
    private final ReviewMapper reviewMapper;

    @Override
    @Transactional
    public List<GetReviewResponse> getReviewListOfAccomPlace(Long id) {
        List<Review> reviewListOfAccomplace = reviewRepository.findByAccomPlaceId(id);
        List<GetReviewResponse> reviewResponseList = new ArrayList<>();
        for (Review review : reviewListOfAccomplace) {
            GetReviewResponse reviewResponse = reviewMapper.toGetReviewResponse(review);
            User user = userRepository.findByUserId(review.getReviewListId())
                    .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("user")));
            reviewResponse.setAvatarUserUrl(user.getAvatarUrl());
            reviewResponse.setAccomPlaceId(id);
            reviewResponse.setFirstName(user.getFirstName());
            reviewResponse.setLastName(user.getLastName());
            if (review.getHaveImage()) {
                List<String> imageReviewUrls = new ArrayList<>();
                for (ImageReview imgReview : review.getImageReviewSet()) {
                    imageReviewUrls.add(imgReview.getImageUrl());
                }
                reviewResponse.setImageReviewUrls(imageReviewUrls);
            }
            reviewResponseList.add(reviewResponse);
        }
        return reviewResponseList;
    }

    @Override
    @Transactional
    public BaseMessageData createReviewForBooking(ReviewBookingRequest request, String userMail) {
        User user = userRepository.findByMail(userMail).orElseThrow(() -> new ResourceNotFoundException("User"));
        ReviewList reviewList = reviewListRepository.findByUserId(user.getId()).get();
        Booking booking = bookingRepository.findBookingByCode(request.getBookingCode())
                .orElseThrow(() -> new ResourceNotFoundException("Booking code"));
        if (!booking.getStatus().equals(BookingStatusEnum.CHECK_OUT)) {
            return new BaseMessageData(AppContants.REVIEW_IS_NOT_AVAILABLE);
        }

        Review review = reviewMapper.toEntity(request);
        review.setReviewList(reviewList);
        review.setBooking(booking);
        review.setReviewListId(reviewList.getId());
        review.setStatus(CommonStatusEnum.ENABLE);
        if (!request.getImagesUrls().isEmpty() && request.getImagesUrls() != null) {
            review.setHaveImage(true);
            review = reviewRepository.save(review);
            for (String imageUrl : request.getImagesUrls()) {
                ImageReview imageReview = ImageReview.builder()
                        .imageUrl(imageUrl)
                        .review(review)
                        .reviewId(review.getId())
                        .build();
                imageReviewRepository.save(imageReview);
            }
        } else {
            review.setHaveImage(false);
            reviewRepository.save(review);
        }

        AccomPlace accomPlace = accomPlaceRepository.findById(booking.getAccomId())
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("accom place")));
        long numCurrentReview = accomPlace.getNumReview();
        float currentGradeRate = accomPlace.getGradeRate();
        accomPlace.setNumReview(numCurrentReview + 1);
        accomPlace.setGradeRate((currentGradeRate * numCurrentReview + review.getRateStar()) / (currentGradeRate + 1));
        accomPlaceRepository.save(accomPlace);
        return new BaseMessageData(AppContants.ADD_SUCCESS_MESSAGE("Review"));
    }
}
