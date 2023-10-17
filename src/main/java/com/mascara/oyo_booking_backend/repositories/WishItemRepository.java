package com.mascara.oyo_booking_backend.repositories;

import com.mascara.oyo_booking_backend.entities.WishItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 01/10/2023
 * Time      : 3:28 CH
 * Filename  : WishItemRepository
 */
@Repository
public interface WishItemRepository extends JpaRepository<WishItem, Long> {
}
