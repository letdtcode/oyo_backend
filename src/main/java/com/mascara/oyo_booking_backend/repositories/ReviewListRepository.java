package com.mascara.oyo_booking_backend.repositories;

import com.mascara.oyo_booking_backend.entities.ReviewList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 01/10/2023
 * Time      : 3:29 CH
 * Filename  : ReviewListRepository
 */
@Repository
public interface ReviewListRepository extends JpaRepository<ReviewList, Long> {
    @Query(value = "select rl.* from review_list rl where rl.user_id = :userId",nativeQuery = true)
    Optional<ReviewList> findByUserId(@Param("userId") Long id);
}
