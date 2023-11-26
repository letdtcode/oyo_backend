package com.mascara.oyo_booking_backend.repositories;

import com.mascara.oyo_booking_backend.entities.Ward;
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
 * Filename  : WardRepository
 */
@Repository
public interface WardRepository extends JpaRepository<Ward, Long> {
    @Query(value = "select w.* from ward w limit 1", nativeQuery = true)
    List<Ward> checkExistData();

    @Query(value = "select w.* from ward w where w.ward_code = :wardcode and w.deleted is false", nativeQuery = true)
    Optional<Ward> findByWardCode(@Param("wardcode") String wardCode);
}
