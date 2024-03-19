package com.mascara.oyo_booking_backend.repositories;

import com.mascara.oyo_booking_backend.entities.authentication.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 17/10/2023
 * Time      : 4:28 CH
 * Filename  : RefreshTokenRepository
 */
@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    @Query(value = "select * from refresh_token rt where rt.token =:refresh_token and rt.deleted is false",nativeQuery = true)
    Optional<RefreshToken> findByRefreshToken(@Param("refresh_token") String refreshToken);
//
//    @Query(value = "select rt.* from refresh_token rt join users u on rt.user_id = u.id where u.mail =:mail and rt.deleted is false", nativeQuery = true)
//    Optional<RefreshToken> findByUserMail(@Param("mail") String mail);
}
