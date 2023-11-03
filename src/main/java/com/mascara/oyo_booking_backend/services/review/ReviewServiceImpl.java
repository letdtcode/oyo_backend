package com.mascara.oyo_booking_backend.services.review;

import com.mascara.oyo_booking_backend.repositories.ReviewRepository;
import com.mascara.oyo_booking_backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 03/11/2023
 * Time      : 2:00 SA
 * Filename  : ReviewService
 */
@Service
public class ReviewServiceImpl implements ReviewService{

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    public UserRepository userRepository;
}
