package com.mascara.oyo_booking_backend.repositories;

import com.mascara.oyo_booking_backend.entities.AccomPlace;
import com.mascara.oyo_booking_backend.entities.AccommodationCategories;
import com.mascara.oyo_booking_backend.entities.Facility;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Query(value = "select ac.* from accommodation_categories ac where ac.id = :id and ac.deleted = false",nativeQuery = true)
    Optional<AccommodationCategories> findById(@Param("id") Long id);

    @Query(value = "select ac.* from accommodation_categories ac where ac.accom_cate_name = :accomCateName and ac.deleted = false",nativeQuery = true)
    Optional<AccommodationCategories> findByAccomCateName(@Param("accomCateName") String accomCateName);

    @Query(value = "select ac.* from accommodation_categories ac where ac.deleted = false",
            countQuery = "select count(id) from accommodation_categories ac where ac.deleted = false",
            nativeQuery = true)
    Page<AccommodationCategories> getAllWithPaging(Pageable pageable);

    @Query(value = "SELECT if(COUNT(*) >0,'true','false') FROM accommodation_categories ac WHERE ac.accom_cate_name = :accomCateName and ac.deleted is false", nativeQuery = true)
    boolean existsByAccomCateName(@Param("accomCateName") String accomCateName);

    @Query(value = "SELECT if(COUNT(*) >0,'true','false') FROM accommodation_categories ac WHERE ac.icon = :icon and ac.deleted is false", nativeQuery = true)
    boolean existsByIcon(@Param("icon") String icon);
}
