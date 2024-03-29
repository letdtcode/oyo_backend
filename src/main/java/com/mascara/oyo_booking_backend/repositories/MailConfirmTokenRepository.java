package com.mascara.oyo_booking_backend.repositories;

import com.mascara.oyo_booking_backend.entities.authentication.MailConfirmToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
    @Query(value = "select mct.* from mail_confirm_token mct where mct.verify_token = :token and mct.deleted is false", nativeQuery = true)
    Optional<MailConfirmToken> findByVerifyToken(@Param("token") String token);

    boolean existsByUserId(Long userId);

    @Query(value = "select mct.* from mail_confirm_token mct where mct.user_id = :userid and mct.deleted is false", nativeQuery = true)
    Optional<MailConfirmToken> findByUserId(@Param("userid") Long userId);
}
