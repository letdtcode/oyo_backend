package com.mascara.oyo_booking_backend.dtos.recommender_system.projections;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 01/07/2024
 * Time      : 6:30 CH
 * Filename  : AccomPlaceRecommendProjection
 */
public interface AccomPlaceRecommendProjection {
    Long getAccomId();
    Double getSimilarity();
}
