package com.mascara.oyo_booking_backend.repositories;

import com.mascara.oyo_booking_backend.entities.Facility;
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
 * Filename  : FacilityAccomRepository
 */
@Repository
public interface FacilityRepository extends JpaRepository<Facility, Long> {
    @Query(value = "select f.* from facility f limit 1", nativeQuery = true)
    List<Facility> checkExistData();

    @Query(value = "SELECT if(COUNT(*) >0,'true','false') FROM facility f WHERE (f.facility_name = :facility_name or f.image_url = :image_url) and f.deleted is false", nativeQuery = true)
    boolean existsByFacilityNameOrImageUrl(@Param("facility_name") String facilityName,@Param("image_url") String imageUrl);

    @Query(value = "select f.* from facility f where f.facility_name = :facilityName and f.deleted is false", nativeQuery = true)
    Optional<Facility> findByFacilityName(@Param("facilityName") String facilityName);

    @Query(value = "select f.* from facility f where f.faci_code = :facilityCode and f.deleted is false", nativeQuery = true)
    Optional<Facility> findByFacilityCode(@Param("facilityCode") String facilityCode);

    @Query(value = "select f.* from facility f where f.id = :id and f.deleted is false", nativeQuery = true)
    Optional<Facility> findById(@Param("id") Long id);

    @Query(value = "select f.* from facility f where f.facility_name in :listFacilityName and f.deleted is false",nativeQuery = true)
    List<Facility> findByListFacilityName(@Param("listFacilityName") List<String> listFacilityName);

    @Query(value = "select f.* from facility f where f.deleted is false",
            countQuery = "select count(id) from facility f where f.deleted is false",
            nativeQuery = true)
    Page<Facility> getAllWithPaging(Pageable pageable);
}
