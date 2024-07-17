package com.mascara.oyo_booking_backend.repositories;

import com.mascara.oyo_booking_backend.dtos.recommender_system.projections.AccomPlaceRecommendProjection;
import com.mascara.oyo_booking_backend.dtos.statistic.admin.projections.InfoAccomPlaceStatisticProjection;
import com.mascara.oyo_booking_backend.dtos.statistic.partner.projections.HostHomeStatisticProjection;
import com.mascara.oyo_booking_backend.entities.accommodation.AccomPlace;
import com.mascara.oyo_booking_backend.enums.AccomStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
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
 * Filename  : AccomPlaceRepository
 */
@Repository
public interface AccomPlaceRepository extends JpaRepository<AccomPlace, Long>, JpaSpecificationExecutor<AccomPlace> {

    @Query(value = "SELECT distinct ap.* FROM accom_place ap LEFT JOIN facility_accom fa " +
            "ON ap.id = fa.accom_id LEFT JOIN facility f ON fa.facility_id = f.id LEFT JOIN accommodation_categories ac on  ap.accom_cate_id = ac.id" +
            " where (:accomCateName is null or ac.accom_cate_name = :accomCateName) and (:provinceCode is null or ap.province_code = :provinceCode) AND " +
            "(:districtCode is null or ap.district_code = :districtCode) " +
            "AND (:wardCode is null or ap.ward_code = :wardCode) AND (:priceFrom is null or :priceTo is null or " +
            "(ap.price_per_night BETWEEN :priceFrom AND :priceTo)) " +
            "AND IF(:size > 0, f.faci_code IN :facilityCode,true) AND " +
            "(:numBathroom is null or ap.num_bathroom = :numBathroom) " +
            "AND (:numPeople is null or ap.num_people = :numPeople) AND " +
            "(:numBedRoom is null or ap.num_bed_room = :numBedRoom) and ap.status = 'APPROVED' and ap.deleted = false group by ap.id having (:size > 0 AND COUNT(DISTINCT f.faci_code) = :size) OR (:size = 0)",
            countQuery = "SELECT count(ap.id) FROM accom_place ap LEFT JOIN facility_accom fa " +
                    "ON ap.id = fa.accom_id LEFT JOIN facility f ON fa.facility_id = f.id LEFT JOIN accommodation_categories ac on  ap.accom_cate_id = ac.id " +
                    "where (:accomCateName is null or ac.accom_cate_name = :accomCateName) and (:provinceCode is null or ap.province_code = :provinceCode) AND " +
                    "(:districtCode is null or ap.district_code = :districtCode) AND (:wardCode is null or ap.ward_code = :wardCode) AND (:priceFrom is null or :priceTo is null or " +
                    "(ap.price_per_night BETWEEN :priceFrom AND :priceTo)) " +
                    "AND IF(:size > 0, f.faci_code IN :facilityCode,true) AND " +
                    "(:numBathroom is null or ap.num_bathroom = :numBathroom) " +
                    "AND (:numPeople is null or ap.num_people = :numPeople) AND " +
                    "(:numBedRoom is null or ap.num_bed_room = :numBedRoom) and ap.status = 'APPROVED' and ap.deleted = false group by ap.id having (:size > 0 AND COUNT(DISTINCT f.faci_code) = :size) OR (:size = 0)",
            nativeQuery = true)
    Page<AccomPlace> getPageWithFullFilter(
            @Param("accomCateName") String accomCateName,
            @Param("provinceCode") String provinceCode,
            @Param("districtCode") String districtCode,
            @Param("wardCode") String wardCode,
            @Param("priceFrom") Double priceFrom,
            @Param("priceTo") Double priceTo,
            @Param("facilityCode") List<String> facilityName,
            @Param("size") Integer size,
            @Param("numBathroom") Integer numBathroom,
            @Param("numPeople") Integer numPeople,
            @Param("numBedRoom") Integer numBedRoom,
            Pageable pageable);

    @Query(value = "select ap.* from accom_place ap, province p where ap.province_code = p.province_code " +
            "and (upper(ap.accom_name) like upper(CONCAT('%', :keyword,'%')) OR " +
            "upper(p.province_name) like upper(CONCAT('%', :keyword,'%')))",
            countQuery = "select count(ap.id) from accom_place ap, province p where ap.province_code = p.province_code " +
                    "and (upper(ap.accom_name) like upper(CONCAT('%', :keyword,'%')) OR " +
                    "upper(p.province_name) like upper(CONCAT('%', :keyword,'%')))", nativeQuery = true)
    Page<AccomPlace> getFilterByKeyWord(@Param("keyword") String keyword, Pageable pageable);

    @Query(value = "select ap.* from accom_place ap where ap.id = :id and ap.deleted = false", nativeQuery = true)
    Optional<AccomPlace> findById(@Param("id") Long id);

    @Query(value = "select ap.* from accom_place ap limit 1", nativeQuery = true)
    List<AccomPlace> checkExistData();

    @Query(value = "select ap.* from accom_place ap where ap.deleted = false and ap.status = :#{#status.toString()}",
            countQuery = "select count(id) from accom_place ap where ap.deleted = false and ap.status = :#{#status.toString()}",
            nativeQuery = true)
    Page<AccomPlace> getAllWithPaging(Pageable pageable, @Param("status") AccomStatusEnum status);

    @Query(value = "select ap.* from accom_place ap where ap.deleted = false and ap.status = :status and ap.user_id != :user_id",
            countQuery = "select count(id) from accom_place ap where ap.deleted = false and ap.status = :status and ap.user_id != :user_id",
            nativeQuery = true)
    Page<AccomPlace> getAllWithPagingExceptAccomOfHost(Pageable pageable, @Param("status") String status, @Param("user_id") Long userId);

    @Query(value = "select ap.* from accom_place ap where ap.user_id = :host_id and ap.deleted = false and ap.status= :status",
            countQuery = "select count(id) from accom_place ap where ap.user_id = :host_id and ap.deleted = false and ap.status= :status",
            nativeQuery = true)
    Page<AccomPlace> getListAccomPlaceOfPartner(@Param("host_id") Long hostId,
                                                @Param("status") String status,
                                                Pageable pageable);


    @Query(value = "select ap.* from accom_place ap join wish_item wi on ap.id = wi.accom_id where wi.wish_id = :wish_id and wi.deleted is false",
            countQuery = "select count(ap.id) from accom_place ap join wish_item wi on ap.id = wi.accom_id where wi.wish_id = :wish_id and wi.deleted is false",
            nativeQuery = true)
    Page<AccomPlace> getListAccomPlaceOfWishListUser(@Param("wish_id") Long wishListId, Pageable pageable);

    @Modifying
    @Query(value = "update accom_place ap set ap.status = :status where ap.id = :id", nativeQuery = true)
    void changeStatusAccomPlace(@Param("id") Long id,
                                @Param("status") String status);

    @Query(nativeQuery = true,
            value = "select c.id as accomId," +
                    "c.accom_name as accomName, " +
                    "c.first_name as hostFirstName, " +
                    "c.last_name as hostLastName, " +
                    "c.num_view as numberOfView, " +
                    "c.num_booking as numberOfBooking, " +
                    "coalesce(sum(p.total_bill),0) as totalRevenue," +
                    "c.num_review as numberOfReview, " +
                    "c.grade_rate as averageGradeRate " +
                    "from ( select a.*, b.id as book_id from (select ap.*, u.first_name, u.last_name from " +
                    "accom_place ap join users u on ap.user_id = u.id where ap.status = 'APPROVED') a left join booking b on a.id = b.accom_id) c " +
                    "left join payment p on c.book_id = p.id where (DATE(p.created_date) is null or DATE(p.created_date) " +
                    "between :date_start and :date_end) group by c.id order by c.num_booking desc")
    Page<InfoAccomPlaceStatisticProjection> getStatisticForAccomPlaceOfAdmin(@Param("date_start") LocalDate dateStart,
                                                                             @Param("date_end") LocalDate dateEnd,
                                                                             Pageable pageable);

    @Query(nativeQuery = true,
            value = "with priceRangeOfHost as (" +
                    "    select avg(ap.price_per_night) as avg_price from booking b join accom_place ap on b.accom_id = ap.id where b.booking_list_id = :user_id), " +
                    "     homestay_range as (" +
                    "         select ap1.id as item_id1, ap2.id as item_id2, " +
                    "                case" +
                    "                    when (ap1.accom_cate_id = ap2.accom_cate_id and ap1.province_code = ap2.province_code) then 0.0826" +
                    "                    when (ap1.accom_cate_id != ap2.accom_cate_id and ap1.province_code = ap2.province_code) then 0.0156" +
                    "                    when (ap1.accom_cate_id = ap2.accom_cate_id and ap1.province_code != ap2.province_code) then 0.067" +
                    "                    else 0" +
                    "                    end as similarity," +
                    "                case" +
                    "                    when ap2.price_per_night <= 350000 then 'low_price'" +
                    "                    when ap2.price_per_night <= 500000 then 'mid_price'" +
                    "                    else 'luxury_price'" +
                    "                    end as price_range" +
                    "         from accom_place ap1" +
                    "                  join accom_place ap2 on ap1.id != ap2.id" +
                    "         order by item_id1, similarity desc" +
                    "     ), " +
                    "     adjusted_similarity as (" +
                    "         select" +
                    "             hr.item_id1," +
                    "             hr.item_id2," +
                    "             hr.similarity + case" +
                    "                                 when hr.price_range =" +
                    "                                      (" +
                    "                                          select" +
                    "                                              case" +
                    "                                                  when pr.avg_price <= 350000 then 'low_price'" +
                    "                                                  when pr.avg_price <= 500000 then 'mid_price'" +
                    "                                                  else 'luxury_price'" +
                    "                                                  end" +
                    "                                          from priceRangeOfHost pr)" +
                    "                                     then 0.08" +
                    "                                 else 0" +
                    "                 end as adjusted_similarity" +
                    "         from homestay_range hr" +
                    "     )" +
                    "SELECT DISTINCT item_id2 AS accomId, avg(adjusted_similarity) AS similarity " +
                    "FROM adjusted_similarity " +
                    "WHERE adjusted_similarity.item_id1 IN (" +
                    "    SELECT b2.accom_id" +
                    "    FROM booking b2" +
                    "    WHERE b2.booking_list_id = :user_id" +
                    ") " +
                    "group by accomId " +
                    "ORDER BY similarity DESC",
            countQuery = "with priceRangeOfHost as (" +
                    "    select avg(ap.price_per_night) as avg_price from booking b join accom_place ap on b.accom_id = ap.id where b.booking_list_id = :user_id), " +
                    "     homestay_range as (" +
                    "         select ap1.id as item_id1, ap2.id as item_id2, " +
                    "                case" +
                    "                    when (ap1.accom_cate_id = ap2.accom_cate_id and ap1.province_code = ap2.province_code) then 0.0826" +
                    "                    when (ap1.accom_cate_id != ap2.accom_cate_id and ap1.province_code = ap2.province_code) then 0.0156" +
                    "                    when (ap1.accom_cate_id = ap2.accom_cate_id and ap1.province_code != ap2.province_code) then 0.067" +
                    "                    else 0" +
                    "                    end as similarity," +
                    "                case" +
                    "                    when ap2.price_per_night <= 350000 then 'low_price'" +
                    "                    when ap2.price_per_night <= 500000 then 'mid_price'" +
                    "                    else 'luxury_price'" +
                    "                    end as price_range" +
                    "         from accom_place ap1" +
                    "                  join accom_place ap2 on ap1.id != ap2.id" +
                    "         order by item_id1, similarity desc" +
                    "     ), " +
                    "     adjusted_similarity as (" +
                    "         select" +
                    "             hr.item_id1," +
                    "             hr.item_id2," +
                    "             hr.similarity + case" +
                    "                                 when hr.price_range =" +
                    "                                      (" +
                    "                                          select" +
                    "                                              case" +
                    "                                                  when pr.avg_price <= 350000 then 'low_price'" +
                    "                                                  when pr.avg_price <= 500000 then 'mid_price'" +
                    "                                                  else 'luxury_price'" +
                    "                                                  end" +
                    "                                          from priceRangeOfHost pr)" +
                    "                                     then 0.08" +
                    "                                 else 0" +
                    "                 end as adjusted_similarity" +
                    "         from homestay_range hr" +
                    "     )" +
                    "SELECT count(adjusted_similarity.item_id1) " +
                    "FROM adjusted_similarity " +
                    "WHERE adjusted_similarity.item_id1 IN (" +
                    "    SELECT b2.accom_id" +
                    "    FROM booking b2" +
                    "    WHERE b2.booking_list_id = :user_id" +
                    ") " +
                    "group by item_id2 " +
                    "ORDER BY adjusted_similarity DESC")
    Page<AccomPlaceRecommendProjection> getAccomPlaceRecommend(@Param("user_id") Long userId,
                                                               Pageable pageable);

    @Query(nativeQuery = true,
            value = "with qr3 as (select count(b.id) as numBookingSuccess, " +
                    "ap.id as accomId " +
                    "from accom_place ap left join " +
                    "booking b on ap.id = b.accom_id where ap.user_id = :host_id and ap.status = 'APPROVED' and b.status != 'CANCELED' and month(b.created_date) = :month and year(b.created_date) = :year group by ap.id)," +
                    "qr2 as (select ap.id as accomId," +
                    "                    coalesce(sum(p.total_bill),0) as revenue " +
                    "from accom_place ap left join " +
                    "booking b on ap.id = b.accom_id left join payment p on b.id = p.id where ap.user_id = :host_id and ap.status = 'APPROVED' and (b.status != 'CANCELED' or b.status is null) and (month(b.created_date) = :month or month(b.created_date) is null) and (year(b.created_date) is null or year(b.created_date) = :year) group by ap.id)" +
                    "    " +
                    "select ap.id as accomId," +
                    "ap.accom_name as accomName," +
                    "        ap.num_view as numberOfView," +
                    "        ap.num_booking as numberOfBooking," +
                    "        qr2.revenue as revenue," +
                    "        ap.num_review as numberOfReview," +
                    "        ap.grade_rate as averageRate, " +
                    "        coalesce(coalesce(qr3.numBookingSuccess,0) / ap.num_booking,0) * 100 as reservationRate" +
                    "        from accom_place ap left join qr2 on ap.id = qr2.accomId left join qr3 on qr2.accomId = qr3.accomId where ap.user_id = :host_id and ap.status = 'APPROVED' order by revenue desc" +
                    "        ",
            countQuery = "with qr3 as (select count(b.id) as numBookingSuccess, " +
                    "ap.id as accomId " +
                    "from accom_place ap left join " +
                    "booking b on ap.id = b.accom_id where ap.user_id = :host_id and ap.status = 'APPROVED' and b.status != 'CANCELED' and month(b.created_date) = :month and year(b.created_date) = :year group by ap.id)," +
                    "qr2 as (select ap.id as accomId," +
                    "                    coalesce(sum(p.total_bill),0) as revenue " +
                    "from accom_place ap left join " +
                    "booking b on ap.id = b.accom_id left join payment p on b.id = p.id where ap.user_id = :host_id and ap.status = 'APPROVED' and (b.status != 'CANCELED' or b.status is null) and (month(b.created_date) = :month or month(b.created_date) is null) and (year(b.created_date) is null or year(b.created_date) = :year) group by ap.id)" +
                    "    " +
                    "select count(ap.id)" +
                    "        from accom_place ap left join qr2 on ap.id = qr2.accomId left join qr3 on qr2.accomId = qr3.accomId where ap.user_id = :host_id and order by revenue desc" +
                    "        ")
    Page<HostHomeStatisticProjection> getStatisticHomeByMonthAndYearOfHost(@Param("host_id") Long hostId,
                                                                           @Param("month") Integer month,
                                                                           @Param("year") Integer year,
                                                                           Pageable pageable);
}
