package com.mascara.oyo_booking_backend.repositories;

import com.mascara.oyo_booking_backend.entities.BedCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 01/10/2023
 * Time      : 3:32 CH
 * Filename  : BedCategoriesRepository
 */
@Repository
public interface BedCategoriesRepository extends JpaRepository<BedCategories, Long> {
}
