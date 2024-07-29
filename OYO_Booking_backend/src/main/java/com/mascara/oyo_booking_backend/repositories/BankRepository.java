package com.mascara.oyo_booking_backend.repositories;

import com.mascara.oyo_booking_backend.entities.address.District;
import com.mascara.oyo_booking_backend.entities.bank.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 19/03/2024
 * Time      : 7:18 CH
 * Filename  : BankRepository
 */
@Repository
public interface BankRepository extends JpaRepository<Bank,Long> {
    @Query(value = "select b.* from bank b limit 1", nativeQuery = true)
    List<Bank> checkExistData();
}
