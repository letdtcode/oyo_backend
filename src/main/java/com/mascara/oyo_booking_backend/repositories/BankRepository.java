package com.mascara.oyo_booking_backend.repositories;

import com.mascara.oyo_booking_backend.entities.bank.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 19/03/2024
 * Time      : 7:18 CH
 * Filename  : BankRepository
 */
@Repository
public interface BankRepository extends JpaRepository<Bank,Long> {
}
