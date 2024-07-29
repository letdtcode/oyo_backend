package com.mascara.oyo_booking_backend.repositories;

import com.mascara.oyo_booking_backend.entities.accommodation.PaymentInfoDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 19/03/2024
 * Time      : 7:18 CH
 * Filename  : PaymentInfoDetailRepository
 */
@Repository
public interface PaymentInfoDetailRepository extends JpaRepository<PaymentInfoDetail,Long> {
}
