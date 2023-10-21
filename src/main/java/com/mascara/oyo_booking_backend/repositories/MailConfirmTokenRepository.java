package com.mascara.oyo_booking_backend.repositories;

import com.mascara.oyo_booking_backend.entities.MailConfirmToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

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
    @Query(value = "select * from mail_confirm_token mct where mct.verify_token = :token", nativeQuery = true)
    Optional<MailConfirmToken> findByVerifyToken(String token);
}
