package com.mascara.oyo_booking_backend.repositories;

import com.mascara.oyo_booking_backend.entities.notification.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
    @Query(value = "select noti.* from notification noti where noti.recipient_mail = :recipient_mail and noti.deleted is false",
            countQuery = "select count(id) from notification noti where noti.recipient_mail = :recipient_mail and noti.deleted = false",
            nativeQuery = true)
    Page<Notification> getAllWithPaging(@Param("recipient_mail") String recipientMail, Pageable pageable);

    @Query(value = "select noti.* from notification noti where noti.view = :view and noti.recipient_mail = :recipient_mail and noti.deleted is false",
            countQuery = "select count(id) from notification noti where noti.view = :view and noti.recipient_mail = :recipient_mail and noti.deleted = false",
            nativeQuery = true)
    Page<Notification> getAllByViewWithPaging(@Param("view") boolean view, @Param("recipient_mail") String recipientMail, Pageable pageable);

    int countByRecipientMailAndViewAndDeleted(String recipientMail, boolean viewed, boolean deleted);

    @Modifying
    @Query(value = "update notification noti set noti.view = :view where noti.recipient_mail = :recipient_mail", nativeQuery = true)
    void updateViewNotificationForUser(@Param("view") boolean view, @Param("recipient_mail") String recipientMail);
}
