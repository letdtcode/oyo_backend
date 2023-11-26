package com.mascara.oyo_booking_backend.repositories;

import com.mascara.oyo_booking_backend.entities.Facility;
import com.mascara.oyo_booking_backend.entities.FacilityCategories;
import com.mascara.oyo_booking_backend.entities.TypeBed;
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
 * Time      : 3:31 CH
 * Filename  : FacilityAccomCategoriesRepository
 */
@Repository
public interface FacilityCategoriesRepository extends JpaRepository<FacilityCategories, Long> {
    @Query(value = "select fac.* from facility_categories fac limit 1", nativeQuery = true)
    List<FacilityCategories> checkExistData();

    @Query(value = "select fac.* from facility_categories fac where fac.faci_cate_code = :facilitycatecode and fac.deleted is false", nativeQuery = true)
    Optional<FacilityCategories> findByFaciCateCode(@Param("facilitycatecode") String facilityCateCode);

    @Query(value = "select fac.* from facility_categories fac where fac.deleted is false",
            countQuery = "select count(id) from facility_categories fac where fac.deleted = false",
            nativeQuery = true)
    Page<FacilityCategories> getAllWithPaging(Pageable pageable);
}
