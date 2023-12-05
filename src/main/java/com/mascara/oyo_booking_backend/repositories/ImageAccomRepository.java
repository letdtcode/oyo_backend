package com.mascara.oyo_booking_backend.repositories;

import com.mascara.oyo_booking_backend.entities.ImageAccom;
import com.mascara.oyo_booking_backend.entities.TypeBed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 01/10/2023
 * Time      : 3:30 CH
 * Filename  : ImageAccomRepository
 */
@Repository
public interface ImageAccomRepository extends JpaRepository<ImageAccom, Long> {
    @Query(value = "select ic.* from image_accom ic limit 1", nativeQuery = true)
    List<ImageAccom> checkExistData();
}
