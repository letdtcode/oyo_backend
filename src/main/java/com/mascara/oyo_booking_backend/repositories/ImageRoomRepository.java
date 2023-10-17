package com.mascara.oyo_booking_backend.repositories;

import com.mascara.oyo_booking_backend.entities.ImageRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 01/10/2023
 * Time      : 3:30 CH
 * Filename  : ImageRoomRepository
 */
@Repository
public interface ImageRoomRepository extends JpaRepository<ImageRoom, Long> {
}
