package com.mascara.oyo_booking_backend.repositories;

import com.mascara.oyo_booking_backend.entities.ReviewList;
import com.mascara.oyo_booking_backend.entities.SurchargeOfAccom;
import com.mascara.oyo_booking_backend.entities.User;
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
 * Time      : 2:41 SA
 * Filename  : SurchargeOfAccomRepository
 */
@Repository
public interface SurchargeOfAccomRepository extends JpaRepository<SurchargeOfAccom, Long> {
    @Query(value = "select soc.* from surcharge_of_accom soc where soc.accom_id = :accom_id and soc.deleted is false",nativeQuery = true)
    List<SurchargeOfAccom> findByAccomPlaceId(@Param("accom_id") Long accomId);
}
