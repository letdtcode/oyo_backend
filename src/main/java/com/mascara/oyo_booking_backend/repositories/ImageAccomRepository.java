package com.mascara.oyo_booking_backend.repositories;

import com.mascara.oyo_booking_backend.entities.AccommodationCategories;
import com.mascara.oyo_booking_backend.entities.ImageAccom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 01/10/2023
 * Time      : 3:30 CH
 * Filename  : ImageAccomRepository
 */
@Repository
public interface ImageAccomRepository extends JpaRepository<ImageAccom, UUID> {
}
