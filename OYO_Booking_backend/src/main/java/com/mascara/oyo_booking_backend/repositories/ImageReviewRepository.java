package com.mascara.oyo_booking_backend.repositories;

import com.mascara.oyo_booking_backend.entities.review.ImageReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 04/11/2023
 * Time      : 2:30 SA
 * Filename  : ImageReviewRepository
 */
@Repository
public interface ImageReviewRepository extends JpaRepository<ImageReview, Long> {
}
