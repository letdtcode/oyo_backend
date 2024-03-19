package com.mascara.oyo_booking_backend.repositories;

import com.mascara.oyo_booking_backend.entities.surcharge.SurchargeCategory;
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
 * Date      : 02/12/2023
 * Time      : 2:40 SA
 * Filename  : SurchargeCategoryRepository
 */
@Repository
public interface SurchargeCategoryRepository extends JpaRepository<SurchargeCategory, Long> {
    @Query(value = "select sc.* from surcharge_category sc limit 1", nativeQuery = true)
    List<SurchargeCategory> checkExistData();

    @Query(value = "select sc.surcharge_cate_name from surcharge_category sc where sc.id = :surcharge_id and sc.deleted is false", nativeQuery = true)
    String getSurchargeCateNameById(@Param("surcharge_id") Long surchargeId);

    @Query(value = "select sc.* from surcharge_category sc where sc.surcharge_code = :surcharge_code and sc.deleted is false", nativeQuery = true)
    Optional<SurchargeCategory> findSurchargeCategoryByCode(@Param("surcharge_code") String surchargeCode);

    @Query(value = "select sc.* from surcharge_category sc where sc.id = :id and sc.deleted is false", nativeQuery = true)
    Optional<SurchargeCategory> findSurchargeCategoryById(@Param("id") Long id);

    @Query(value = "select sc.* from surcharge_category sc where sc.status = :status and sc.deleted is false", nativeQuery = true)
    List<SurchargeCategory> findAllByStatus(@Param("status") String status);

    @Query(value = "select sc.* from surcharge_category sc where sc.deleted is false",
            countQuery = "select count(id) from surcharge_category sc where sc.deleted is false",
            nativeQuery = true)
    Page<SurchargeCategory> getAllWithPaging(Pageable pageable);
}
