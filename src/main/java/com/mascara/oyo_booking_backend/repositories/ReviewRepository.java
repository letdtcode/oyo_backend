package com.mascara.oyo_booking_backend.repositories;

import com.mascara.oyo_booking_backend.entities.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 01/10/2023
 * Time      : 3:29 CH
 * Filename  : ReviewRepository
 */
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query(value = "select r.* from review r join booking b on r.booking_id = b.id where b.accom_id = :id and r.status = 'ENABLE' and r.deleted is false", nativeQuery = true)
    List<Review> findByAccomPlaceId(@Param("id") Long id);
}
