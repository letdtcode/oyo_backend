package com.mascara.oyo_booking_backend.repositories;

import com.mascara.oyo_booking_backend.entities.BedRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 04/11/2023
 * Time      : 2:31 SA
 * Filename  : BedRoomRepository
 */
@Repository
public interface BedRoomRepository extends JpaRepository<BedRoom, Long> {
}
