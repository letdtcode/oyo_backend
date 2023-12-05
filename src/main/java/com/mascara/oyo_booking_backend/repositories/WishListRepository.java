package com.mascara.oyo_booking_backend.repositories;

import com.mascara.oyo_booking_backend.entities.WishItem;
import com.mascara.oyo_booking_backend.entities.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 01/10/2023
 * Time      : 3:28 CH
 * Filename  : WishListRepository
 */
@Repository
public interface WishListRepository extends JpaRepository<WishList, Long> {
    @Query(value = "select wl.* from wish_list wl where wl.user_id = :user_id and wl.deleted is false", nativeQuery = true)
    Optional<WishList> findWishListByUserId(@Param("user_id") Long userId);
}
