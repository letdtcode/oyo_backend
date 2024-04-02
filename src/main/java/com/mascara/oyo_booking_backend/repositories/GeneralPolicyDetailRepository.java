package com.mascara.oyo_booking_backend.repositories;

import com.mascara.oyo_booking_backend.entities.accommodation.GeneralPolicyDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 19/03/2024
 * Time      : 7:19 CH
 * Filename  : GeneralPolicyDetailRepository
 */
@Repository
public interface GeneralPolicyDetailRepository extends JpaRepository<GeneralPolicyDetail, Long> {
    Optional<GeneralPolicyDetail> findById(Long id);
}
