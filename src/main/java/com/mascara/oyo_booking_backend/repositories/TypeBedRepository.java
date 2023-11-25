package com.mascara.oyo_booking_backend.repositories;

import com.mascara.oyo_booking_backend.entities.AccommodationCategories;
import com.mascara.oyo_booking_backend.entities.TypeBed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 04/11/2023
 * Time      : 12:42 SA
 * Filename  : TypeBedRepository
 */
@Repository
public interface TypeBedRepository extends JpaRepository<TypeBed, Long> {
    @Query(value = "select tb.* from type_bed tb limit 1", nativeQuery = true)
    List<TypeBed> checkExistData();

    @Query(value = "select tb.* from type_bed tb where tb.deleted = false",
            countQuery = "select count(id) from type_bed tb where tb.deleted = false",
            nativeQuery = true)
    Page<TypeBed> getAllWithPaging(Pageable pageable);
}
