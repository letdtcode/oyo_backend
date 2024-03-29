package com.mascara.oyo_booking_backend.repositories;

import com.mascara.oyo_booking_backend.entities.authentication.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

    @Query(value = "select * from users u where u.mail =:mail and u.deleted is false", nativeQuery = true)
    Optional<User> findByMail(@Param("mail") String email);

    @Query(value = "select if(count(mail) > 0,'true','false') from users u where u.mail =:mail and u.deleted is false", nativeQuery = true)
    Boolean existsByMail(@Param("mail") String mail);

    @Query(value = "select u.* from users u join refresh_token r on u.id = r.user_id where r.token =:token and u.deleted is false", nativeQuery = true)
    Optional<User> findByRefreshToken(@Param("token") String refreshToken);

    @Query(value = "select u.* from users u join mail_confirm_token mct on u.id = mct.user_id where mct.id = :id and u.deleted is false", nativeQuery = true)
    Optional<User> findByMailConfirmTokenId(@Param("id") Long id);

    @Query(value = "select u.* from users u where u.id = :id and u.deleted is false", nativeQuery = true)
    Optional<User> findByUserId(@Param("id") Long id);

    @Query(value = "select u.* from users u where u.deleted is false",
            countQuery = "select count(id) from users u where u.deleted is false",
            nativeQuery = true)
    Page<User> getAllWithPaging(Pageable pageable);

    @Modifying
    @Query(value = "update users u set u.status = :status where u.mail = :mail and u.deleted is false", nativeQuery = true)
    void changeStatusUser(@Param("mail") String mail, @Param("status") String status);

    @Query(value = "select u.* from users u join accom_place ap on u.id = ap.user_id join booking b on " +
            "b.accom_id = ap.id where b.booking_code = :booking_code and b.deleted is false", nativeQuery = true)
    Optional<User> findHostOfAccomByBookingCode(@Param("booking_code") String bookingCode);

    @Query(value = "select u.* from users u join booking_list bl on u.id = bl.user_id join booking b " +
            "on b.booking_list_id = bl.id where b.booking_code = :booking_code and b.deleted is false", nativeQuery = true)
    Optional<User> findUserByBookingCode(@Param("booking_code") String bookingCode);
}
