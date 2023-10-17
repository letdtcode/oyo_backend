package com.mascara.oyo_booking_backend.repositories;

import com.mascara.oyo_booking_backend.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 01/10/2023
 * Time      : 3:32 CH
 * Filename  : CartRepository
 */
@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
}
