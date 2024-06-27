package com.mascara.oyo_booking_backend.repositories;

import com.mascara.oyo_booking_backend.dtos.statistic.admin.projections.InfoGuestBookingProjection;
import com.mascara.oyo_booking_backend.dtos.statistic.admin.projections.InfoHostStatisticProjection;
import com.mascara.oyo_booking_backend.dtos.statistic.admin.projections.StatisticCountProjection;
import com.mascara.oyo_booking_backend.entities.authentication.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
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


    @Query(nativeQuery = true,
            value = "select a.numberOfGuest, " +
                    "b.totalRevenue, " +
                    "c.numberOfBooking, " +
                    "d.numberOfOwner from (select count(*) as numberOfGuest from " +
                    "users where users.deleted is false and year(users.created_date) = :year) as a , (select coalesce(sum(p.total_bill),0) as totalRevenue from " +
                    "payment p where year(p.created_date) = :year) as b ,(select count(*) as numberOfBooking from payment p where year(p.created_date) = :year) as c, " +
                    "(select count(*) as numberOfOwner from (select distinct u.id from users u join accom_place ap on u.id = ap.user_id where ap.deleted is false and year(ap.created_date) = :year) " +
                    "as user_partner) as d")
    StatisticCountProjection getStatisticCountOfAdmin(@Param("year") Integer year);

    @Query(nativeQuery = true,
            value = "select t.id as userId, " +
                    "t.first_name as firstName, " +
                    "t.last_name as lastName," +
                    "t.mail as email, " +
                    "t.phone as phoneNumber, " +
                    "coalesce(sum(p.total_bill),0) as totalCost, " +
                    "count(p.id) as numberOfBooking " +
                    "from (select u.*, b.id as booking_id from users u left join booking b on u.id = b.booking_list_id) t left join " +
                    "payment p on t.booking_id = p.id where (DATE(p.created_date) is null or DATE(p.created_date) between :date_start and :date_end) group by t.id order by p.total_bill desc")
    Page<InfoGuestBookingProjection> getStatisticForGuestOfAdmin(@Param("date_start") LocalDate dateStart,
                                                                 @Param("date_end") LocalDate dateEnd,
                                                                 Pageable pageable);

    @Query(nativeQuery = true,
    value = "select temp.id as userId, " +
            "temp.first_name as firstName, " +
            "temp.last_name as lastName, " +
            "temp.mail as email, " +
            "count(ap.id) as numberOfAccom, " +
            "temp.numberOfBooking, " +
            "temp.totalRevenue " +
            "from (select u.*, pe.earning_amount, count(pe.id) as numberOfBooking, coalesce(sum(pe.earning_amount),0) as totalRevenue from users u left join partner_earning pe on u.id = pe.partner_id where (DATE(pe.created_date) is null or DATE(pe.created_date) between :date_start and :date_end) " +
            "group by u.id order by pe.earning_amount desc) temp " +
            "left join accom_place ap on temp.id = ap.user_id group by temp.id")
    Page<InfoHostStatisticProjection> getStatisticForHostOfAdmin(@Param("date_start") LocalDate dateStart,
                                                                 @Param("date_end") LocalDate dateEnd,
                                                                 Pageable pageable);
}
