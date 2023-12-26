package com.mascara.oyo_booking_backend.repositories;

import com.mascara.oyo_booking_backend.entities.RevenueList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 01/10/2023
 * Time      : 3:31 CH
 * Filename  : CommisionListRepository
 */
@Repository
public interface RevenueListRepository extends JpaRepository<RevenueList, Long> {

    @Query(value = "select rl.* from revenue_list rl where rl.user_id = :user_id and rl.deleted is false", nativeQuery = true)
    Optional<RevenueList> findByUserId(@Param("user_id") Long userId);
}
