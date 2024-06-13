package com.mascara.oyo_booking_backend.repositories;

import com.mascara.oyo_booking_backend.entities.notification.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 13/06/2024
 * Time      : 8:44 CH
 * Filename  : NotificationRepository
 */
@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
