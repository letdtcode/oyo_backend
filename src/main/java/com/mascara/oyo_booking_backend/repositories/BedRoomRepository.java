package com.mascara.oyo_booking_backend.repositories;

import com.mascara.oyo_booking_backend.entities.BedRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 04/11/2023
 * Time      : 2:31 SA
 * Filename  : BedRoomRepository
 */
@Repository
public interface BedRoomRepository extends JpaRepository<BedRoom, Long> {
    @Query(value = "select br.* from bed_room br where br.id = :id and br.deleted = false", nativeQuery = true)
    Optional<BedRoom> findById(@Param("id") Long id);

    @Modifying
    @Query(value = "DELETE FROM bed_room br WHERE br.accom_id = :accom_id", nativeQuery = true)
    void deleteByAccomId(@Param("accom_id") Long accomId);
}
