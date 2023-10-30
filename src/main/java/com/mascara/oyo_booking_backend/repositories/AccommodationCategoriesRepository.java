package com.mascara.oyo_booking_backend.repositories;

import com.mascara.oyo_booking_backend.entities.AccommodationCategories;
import com.mascara.oyo_booking_backend.entities.Facility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 01/10/2023
 * Time      : 3:33 CH
 * Filename  : AccommodationCategoriesRepository
 */
@Repository
public interface AccommodationCategoriesRepository extends JpaRepository<AccommodationCategories, Long> {

    @Query(value = "select ac.* from accommodation_categories ac limit 1", nativeQuery = true)
    List<AccommodationCategories> checkExistData();

    @Query(value = "select ac.* from accommodation_categories ac where ac.accom_cate_name = :accomCateName",nativeQuery = true)
    Optional<AccommodationCategories> findByAccomCateName(@Param("accomCateName") String accomCateName);
}
