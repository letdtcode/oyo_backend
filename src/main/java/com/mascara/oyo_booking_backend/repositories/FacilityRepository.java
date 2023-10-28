package com.mascara.oyo_booking_backend.repositories;

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
 * Time      : 3:31 CH
 * Filename  : FacilityAccomRepository
 */
@Repository
public interface FacilityRepository extends JpaRepository<Facility, Long> {
    @Query(value = "select f.* from facility f limit 1", nativeQuery = true)
    List<Facility> checkExistData();

    @Query(value = "select f from facility f where f.facility_name = :facilityName", nativeQuery = true)
    Optional<Facility> findByFacilityName(@Param("facilityName") String facilityName);
}
