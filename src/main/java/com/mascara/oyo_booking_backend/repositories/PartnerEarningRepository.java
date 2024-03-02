package com.mascara.oyo_booking_backend.repositories;

import com.mascara.oyo_booking_backend.entities.PartnerEarning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 02/03/2024
 * Time      : 10:23 CH
 * Filename  : PartnerEarning
 */
@Repository
public interface PartnerEarningRepository extends JpaRepository<PartnerEarning, Long> {
}
