package com.mascara.oyo_booking_backend.repositories;

import com.mascara.oyo_booking_backend.entities.RoomCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 01/10/2023
 * Time      : 3:29 CH
 * Filename  : RoomCategoriesRepository
 */
@Repository
public interface RoomCategoriesRepository extends JpaRepository<RoomCategories, Long> {
}
