package com.mascara.oyo_booking_backend.repositories;

import com.mascara.oyo_booking_backend.entities.AccomPlace;
import com.mascara.oyo_booking_backend.entities.Booking;
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
 * Time      : 3:32 CH
 * Filename  : BookingRepository
 */
@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query(value = "select if(count(id) > 0,'false','true') from booking b where b.accom_id = :accom_id and ((b.date_check_in <= :date_check_in AND b.date_check_out >= :date_check_in)" +
            "        OR (b.date_check_in <= :date_check_out AND b.date_check_out >= :date_check_out)" +
            "        OR (b.date_check_in >= :date_check_in AND b.date_check_out <= :date_check_out)) and b.status != 'CANCELED' and b.deleted is false", nativeQuery = true)
    boolean checkBookingAvailable(@Param("accom_id") Long accomId,
                                  @Param("date_check_in") LocalDate checkIn,
                                  @Param("date_check_out") LocalDate checkOut);

    @Query(value = "select b.* from accom_place ap join booking b where ap.id = b.accom_id and ap.user_id = :host_id and " +
            "(b.status = :status or :status is null) and b.date_check_in = :date and ap.deleted is false and b.deleted is false",
            countQuery = "select count(ap.id) from accom_place ap join booking b where ap.id = b.accom_id and " +
                    "ap.user_id = :host_id and (b.status = :status or :status is null) and b.date_check_in = :date and ap.deleted is false and b.deleted is false",
            nativeQuery = true)
    Page<Booking> getListBookingOfPartner(@Param("host_id") Long hostId, @Param("status") String status, @Param("date") LocalDate date, Pageable pageable);

    @Query(value = "select b.* from booking b where b.booking_list_id = :user_id and b.deleted is false",
            countQuery = "select count(b.id) from booking b where b.booking_list_id = :user_id and b.deleted is false",
            nativeQuery = true)
    Page<Booking> getHistoryBookingUser(@Param("user_id") Long userId, Pageable pageable);

    @Query(value = "select b.* from booking b where b.booking_code = :booking_code and b.deleted is false", nativeQuery = true)
    Optional<Booking> findBookingByCode(@Param("booking_code") String bookingCode);

    @Query(value = "select b.* from booking b where (b.date_check_in >= :start_date or b.date_check_out >= :start_date) and b.accom_id = :accom_id and b.status != 'CANCELED' and b.deleted is false ORDER BY b.created_date DESC", nativeQuery = true)
    List<Booking> findBookingByRangeDateStartFromCurrent(@Param("accom_id") Long accomId, @Param("start_date") LocalDate startDate);

    @Modifying
    @Query(value = "update booking b set b.status = :status where b.booking_code = :booking_code", nativeQuery = true)
    void changeStatusBooking(@Param("booking_code") String bookingCode, @Param("status") String status);
}
