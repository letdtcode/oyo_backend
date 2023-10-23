package com.mascara.oyo_booking_backend.repositories;

import com.mascara.oyo_booking_backend.entities.MailConfirmToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 19/10/2023
 * Time      : 3:00 SA
 * Filename  : MailConfirmTokenRepository
 */
@Repository
public interface MailConfirmTokenRepository extends JpaRepository<MailConfirmToken, Long> {
    @Query(value = "select mct.* from mail_confirm_token mct where mct.verify_token = :token", nativeQuery = true)
    Optional<MailConfirmToken> findByVerifyToken(@Param("token") String token);

    @Modifying
    @Transactional
    @Query(value = "UPDATE mail_confirm_token mct set mct.verify_token = :token where mct.user_id = :userid", nativeQuery = true)
    void updateVerifyToken(@Param("userid") Long userId, @Param("token") String token);

    boolean existsByUserId(Long userId);
}
