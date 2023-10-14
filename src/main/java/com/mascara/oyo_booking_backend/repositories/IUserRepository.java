package com.mascara.oyo_booking_backend.repositories;

import com.mascara.oyo_booking_backend.entities.AccommodationCategories;
import com.mascara.oyo_booking_backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 01/10/2023
 * Time      : 3:29 CH
 * Filename  : UserRepository
 */
@Repository
public interface IUserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByMail(String email);

    @Query(value = "select if(count(mail) > 0,'true','false') from user u where u.mail=:mail", nativeQuery = true)
    Boolean existsByMail(@Param("mail") String mail);
}
