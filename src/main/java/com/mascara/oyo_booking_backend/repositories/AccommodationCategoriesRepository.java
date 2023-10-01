package com.mascara.oyo_booking_backend.repositories;

import com.mascara.oyo_booking_backend.entities.AccommodationCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 01/10/2023
 * Time      : 3:33 CH
 * Filename  : AccommodationCategoriesRepository
 */
@Repository
public interface AccommodationCategoriesRepository extends JpaRepository<AccommodationCategories, UUID> {
}
