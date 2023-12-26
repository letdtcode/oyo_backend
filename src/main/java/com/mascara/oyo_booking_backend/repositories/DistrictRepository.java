package com.mascara.oyo_booking_backend.repositories;

import com.mascara.oyo_booking_backend.entities.District;
import com.mascara.oyo_booking_backend.entities.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 22/10/2023
 * Time      : 11:52 CH
 * Filename  : DistrictRepository
 */
@Repository
public interface DistrictRepository extends JpaRepository<District, Long> {
    @Query(value = "select d.* from district d limit 1", nativeQuery = true)
    List<District> checkExistData();

    @Query(value = "select d.* from district d where d.district_code = :districtcode and d.deleted is false", nativeQuery = true)
    Optional<District> findByDistrictCode(@Param("districtcode") String districtcode);

    @Query(value = "SELECT if(COUNT(*) >0,'true','false') FROM district d WHERE d.district_code = :districtCode and d.deleted is false", nativeQuery = true)
    boolean existsByDistrictCode(@Param("districtCode") String districtCode);
}
