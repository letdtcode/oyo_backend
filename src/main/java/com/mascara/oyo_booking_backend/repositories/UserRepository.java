package com.mascara.oyo_booking_backend.repositories;

import com.mascara.oyo_booking_backend.entities.User;
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
 * Date      : 01/10/2023
 * Time      : 3:29 CH
 * Filename  : UserRepository
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "select u.* from users u limit 1", nativeQuery = true)
    List<User> checkExistData();

    @Query(value = "select * from users u where u.mail =:mail", nativeQuery = true)
    Optional<User> findByMail(@Param("mail") String email);

    @Query(value = "select if(count(mail) > 0,'true','false') from users u where u.mail =:mail", nativeQuery = true)
    Boolean existsByMail(@Param("mail") String mail);

    @Query(value = "select u.* from users u join refresh_token r on u.id = r.user_id where r.token =:token", nativeQuery = true)
    Optional<User> findByRefreshToken(@Param("token") String refreshToken);

    @Query(value = "select u.* from users u join mail_confirm_token mct on u.id = mct.user_id where mct.id = :id", nativeQuery = true)
    Optional<User> findByMailConfirmTokenId(@Param("id") Long id);

    @Query(value = "select u.* from users u where u.id = :id",nativeQuery = true)
    Optional<User> findByUserId(@Param("id") Long id);
}
