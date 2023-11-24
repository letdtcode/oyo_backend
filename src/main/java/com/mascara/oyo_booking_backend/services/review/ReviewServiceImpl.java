package com.mascara.oyo_booking_backend.services.review;

import com.mascara.oyo_booking_backend.dtos.request.review.ReviewAccomPlaceRequest;
import com.mascara.oyo_booking_backend.dtos.response.general.MessageResponse;
import com.mascara.oyo_booking_backend.dtos.response.review.GetReviewResponse;
import com.mascara.oyo_booking_backend.entities.*;
import com.mascara.oyo_booking_backend.enums.ReviewStatusEnum;
import com.mascara.oyo_booking_backend.exceptions.ResourceNotFoundException;
import com.mascara.oyo_booking_backend.repositories.*;
import com.mascara.oyo_booking_backend.services.storage.cloudinary.CloudinaryService;
import com.mascara.oyo_booking_backend.utils.AppContants;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    public UserRepository userRepository;

    @Autowired
    private AccomPlaceRepository accomPlaceRepository;

    @Autowired
    private ReviewListRepository reviewListRepository;

    @Autowired
    private ImageReviewRepository imageReviewRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private ModelMapper mapper;

    @Override
    @Transactional
    public List<GetReviewResponse> getReviewListOfAccomPlace(Long id) {
        List<Review> reviewListOfAccomplace = reviewRepository.findByAccomPlaceId(id);
        List<GetReviewResponse> reviewResponseList = new ArrayList<>();
        for (Review review : reviewListOfAccomplace) {
            GetReviewResponse reviewResponse = mapper.map(review, GetReviewResponse.class);
            User user = userRepository.findByUserId(review.getReviewListId())
                    .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("user")));
            reviewResponse.setAvatarUserUrl(user.getAvatarUrl());
            reviewResponse.setFirstNameUser(user.getFirstName());
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
    public String reviewAccomPlace(ReviewAccomPlaceRequest request,
                                            List<MultipartFile> imageReviewFiles) {
        ReviewList reviewList = reviewListRepository.findByUserId(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("user")));
        AccomPlace accomPlace = accomPlaceRepository.findById(request.getAccomPlaceId())
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("accom place")));
        Review review = mapper.map(request, Review.class);
        review.setAccomPlace(accomPlace);
        review.setReviewList(reviewList);
        review.setStatus(ReviewStatusEnum.ACTIVE);
        if (!imageReviewFiles.isEmpty()) {
            review.setHaveImage(true);
            review = reviewRepository.save(review);
            for (MultipartFile file : imageReviewFiles) {
                String pathImg = cloudinaryService.store(file);
                ImageReview imageReview = ImageReview.builder()
                        .imageUrl(pathImg)
                        .review(review)
                        .reviewId(review.getId())
                        .build();
                imageReviewRepository.save(imageReview);
            }
        } else {
            review.setHaveImage(false);
            reviewRepository.save(review);
        }
        return "Add review accom place success !";
    }
}
