package com.mascara.oyo_booking_backend.repositories;

import com.mascara.oyo_booking_backend.entities.booking.BookingList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 01/10/2023
 * Time      : 3:32 CH
 * Filename  : BookingListRepository
 */
@Repository
public interface BookingListRepository extends JpaRepository<BookingList, Long> {

    @Query(value = "select bl.* from booking_list bl where bl.user_id = :userId and bl.deleted = false", nativeQuery = true)
    Optional<BookingList> findByUserId(@Param("userId") Long id);

    @Query(value = "select bl.* from booking_list bl inner join users u on bl.user_id = u.id where u.mail = :user_mail and u.status = 'ENABLE' and u.deleted is false and bl.deleted is false", nativeQuery = true)
    Optional<BookingList> findByUserMail(@Param("user_mail") String user_mail);

//    @Query
//    Page<BookingList> getTopByTotalBill(@Param("date_from") LocalDate dateForm, @Param("date_end") LocalDate dateEnd);
}
