package com.mascara.oyo_booking_backend.repositories;

import com.mascara.oyo_booking_backend.entities.accommodation.PriceCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 18/05/2024
 * Time      : 1:44 CH
 * Filename  : DiscountRepository
 */

@Repository
public interface PriceCustomRepository extends JpaRepository<PriceCustom, Long> {
    Optional<PriceCustom> findByAccomIdAndDateApply(Long accomId, LocalDate dateApply);

    List<PriceCustom> findByAccomId(Long accomId);
}
