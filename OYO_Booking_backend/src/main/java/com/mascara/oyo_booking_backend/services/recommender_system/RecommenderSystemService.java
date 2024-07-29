package com.mascara.oyo_booking_backend.services.recommender_system;

import com.mascara.oyo_booking_backend.dtos.base.BasePagingData;
import com.mascara.oyo_booking_backend.dtos.recommender_system.response.RecommenderAccomPlaceResponse;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 01/07/2024
 * Time      : 6:43 CH
 * Filename  : RecommenderSystemService
 */
public interface RecommenderSystemService {
    @Transactional
    BasePagingData<RecommenderAccomPlaceResponse> getAccomPlaceRecommend(Integer pageNum,
                                                                         Integer pageSize,
                                                                         String userMail);
}
