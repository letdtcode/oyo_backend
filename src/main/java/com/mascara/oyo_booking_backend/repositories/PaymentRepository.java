package com.mascara.oyo_booking_backend.repositories;

import com.mascara.oyo_booking_backend.entities.order.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 02/03/2024
 * Time      : 10:22 CH
 * Filename  : PaymentRepository
 */
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findById(Long id);

    Optional<Payment> findByPaypalOrderId(String paypalOrderId);
}
