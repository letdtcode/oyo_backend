package com.mascara.oyo_booking_backend.repositories;

import com.mascara.oyo_booking_backend.entities.wish.WishItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 01/10/2023
 * Time      : 3:28 CH
 * Filename  : WishItemRepository
 */
@Repository
public interface WishItemRepository extends JpaRepository<WishItem, Long> {
    @Query(value = "SELECT if(COUNT(*) >0,'true','false') FROM wish_item wi WHERE wi.accom_id = :accomId and wi.wish_id = :wishId and wi.deleted is false", nativeQuery = true)
    boolean checkAccomIsWishUser(@Param("accomId") Long accomId, @Param("wishId") Long wishId);

    @Query(value = "SELECT wi.* FROM wish_item wi WHERE wi.accom_id = :accomId and wi.wish_id = :wishId", nativeQuery = true)
    Optional<WishItem> findWishItemByAccomIdIgnoreDeleted(@Param("accomId") Long accomId,
                                                          @Param("wishId") Long wishId);

    @Modifying
    @Query(value = "update wish_item wi set wi.deleted = :is_deleted where wi.wish_id = :wish_id " +
            "and wi.accom_id = :accom_id", nativeQuery = true)
    void changeDeletedWishItem(@Param("is_deleted") boolean isDeleted,
                               @Param("wish_id") Long wishListId,
                               @Param("accom_id") Long accomId);
}
