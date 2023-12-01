package com.mascara.oyo_booking_backend.repositories;

import com.mascara.oyo_booking_backend.entities.Revenue;
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
 * Filename  : CommisionRepository
 */
@Repository
public interface RevenueRepository extends JpaRepository<Revenue, Long> {

    @Query(value = "select r.* from revenue r where r.booking_code = :booking_code and r.deleted is false",nativeQuery = true)
    Optional<Revenue> findByBookingCode(@Param("booking_code") String bookingCode);
}
