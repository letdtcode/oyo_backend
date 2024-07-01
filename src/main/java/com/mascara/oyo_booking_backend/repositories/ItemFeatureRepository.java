package com.mascara.oyo_booking_backend.repositories;

import com.mascara.oyo_booking_backend.entities.accommodation.AccomPlace;
import com.mascara.oyo_booking_backend.entities.recommend.ItemFeature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 30/06/2024
 * Time      : 6:44 CH
 * Filename  : ItemFeatureRepository
 */
@Repository
public interface ItemFeatureRepository extends JpaRepository<ItemFeature, Long> {
    @Query(value = "select ifeat.* from item_feature ifeat limit 1", nativeQuery = true)
    List<ItemFeature> checkExistData();
}
