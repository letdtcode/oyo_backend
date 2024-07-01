package com.mascara.oyo_booking_backend.services.recommender_system;

import com.mascara.oyo_booking_backend.dtos.base.BasePagingData;
import com.mascara.oyo_booking_backend.dtos.recommender_system.projections.AccomPlaceRecommendProjection;
import com.mascara.oyo_booking_backend.dtos.recommender_system.response.RecommenderAccomPlaceResponse;
import com.mascara.oyo_booking_backend.entities.accommodation.AccomPlace;
import com.mascara.oyo_booking_backend.mapper.accommodation.AccomPlaceMapper;
import com.mascara.oyo_booking_backend.repositories.AccomPlaceRepository;
import com.mascara.oyo_booking_backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 01/07/2024
 * Time      : 6:43 CH
 * Filename  : RecommenderSystemServiceImpl
 */
@Service
@RequiredArgsConstructor
public class RecommenderSystemServiceImpl implements RecommenderSystemService {
    private final AccomPlaceRepository accomPlaceRepository;

    private final UserRepository userRepository;

    private final AccomPlaceMapper accomPlaceMapper;

    @Override
    @Transactional
    public BasePagingData<RecommenderAccomPlaceResponse> getAccomPlaceRecommend(Integer pageNum,
                                                                                Integer pageSize,
                                                                                String userMail) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        Long userId = userRepository.findByMail(userMail).get().getId();
        Page<AccomPlaceRecommendProjection> projections = accomPlaceRepository.getAccomPlaceRecommend(userId, pageable);
        List<AccomPlaceRecommendProjection> accomPlaceRecommendList = projections.stream().toList();

        List<RecommenderAccomPlaceResponse> responseList = accomPlaceRecommendList.stream()
                .map(accomPlaceProjection -> {
                    AccomPlace accomPlace = accomPlaceRepository.findById(accomPlaceProjection.getAccomId()).get();
                    RecommenderAccomPlaceResponse response = accomPlaceMapper.toRecommenderAccomPlaceResponse(accomPlace);
                    response.setSimilarity(accomPlaceProjection.getSimilarity());
                    return response;
                })
                .collect(Collectors.toList());
        return new BasePagingData<>(responseList,
                projections.getNumber(),
                projections.getSize(),
                projections.getTotalElements());
    }
}
