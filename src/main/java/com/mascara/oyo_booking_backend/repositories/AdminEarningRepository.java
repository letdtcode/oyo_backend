package com.mascara.oyo_booking_backend.repositories;

import com.mascara.oyo_booking_backend.entities.order.AdminEarning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 02/03/2024
 * Time      : 10:24 CH
 * Filename  : OYOEarningRepository
 */
@Repository
public interface AdminEarningRepository extends JpaRepository<AdminEarning, Long> {
}
