package com.mascara.oyo_booking_backend.services.facility;

import com.mascara.oyo_booking_backend.dtos.BaseMessageData;
import com.mascara.oyo_booking_backend.dtos.request.facility.AddFacilityRequest;
import com.mascara.oyo_booking_backend.dtos.request.facility.UpdateFacilityRequest;
import com.mascara.oyo_booking_backend.dtos.response.facility.GetFacilityResponse;
import com.mascara.oyo_booking_backend.dtos.response.paging.BasePagingData;
import com.mascara.oyo_booking_backend.entities.Facility;
import com.mascara.oyo_booking_backend.entities.FacilityCategories;
import com.mascara.oyo_booking_backend.enums.CommonStatusEnum;
import com.mascara.oyo_booking_backend.exceptions.ResourceNotFoundException;
import com.mascara.oyo_booking_backend.mapper.FacilityMapper;
import com.mascara.oyo_booking_backend.repositories.FacilityCategoriesRepository;
import com.mascara.oyo_booking_backend.repositories.FacilityRepository;
import com.mascara.oyo_booking_backend.utils.AliasUtils;
import com.mascara.oyo_booking_backend.utils.AppContants;
import com.mascara.oyo_booking_backend.utils.GenerateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 22/10/2023
 * Time      : 7:37 CH
 * Filename  : FacilAccomServiceImpl
 */
@Service
public class FacilityServiceImpl implements FacilityService {
    @Autowired
    private FacilityRepository facilityRepository;

    @Autowired
    private FacilityCategoriesRepository facilityCategoriesRepository;

    @Autowired
    private FacilityMapper facilityMapper;

    @Override
    @Transactional
    public BasePagingData<GetFacilityResponse> getAllFacilityWithPaging(Integer pageNum, Integer pageSize, String sortType, String field) {
        Pageable paging = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.valueOf(sortType), field));
        Page<Facility> facilityPage = facilityRepository.getAllWithPaging(paging);

        List<Facility> facilityList = facilityPage.stream().toList();
        List<GetFacilityResponse> responses = facilityList.stream()
                .map(facility -> facilityMapper.toGetFacilityResponse(facility)).collect(Collectors.toList());
        return new BasePagingData<>(responses,
                facilityPage.getNumber(),
                facilityPage.getSize(),
                facilityPage.getTotalElements());
    }

    @Override
    @Transactional
    public GetFacilityResponse addFacility(AddFacilityRequest request) {
        int count = (int) facilityRepository.count();
        FacilityCategories facilityCategories = facilityCategoriesRepository.findByFaciCateCode(request.getFacilityCateCode())
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("Facility category")));
        Facility facility = Facility.builder()
                .facilityName(request.getFacilityName())
                .facilityCateCode(request.getFacilityCateCode())
                .facilityCode(GenerateCodeUtils.generateCode(AliasUtils.FACILITY, count))
                .facilityCategories(facilityCategories)
                .imageUrl(request.getImageUrl())
                .status(CommonStatusEnum.valueOf(request.getStatus()))
                .build();
        facility = facilityRepository.save(facility);
        return facilityMapper.toGetFacilityResponse(facility);
    }


    @Override
    @Transactional
    public GetFacilityResponse updateFacility(UpdateFacilityRequest request, Long id) {
        Facility facility = facilityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("Facility")));
        FacilityCategories facilityCategories = facilityCategoriesRepository.findByFaciCateCode(request.getFacilityCateCode())
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("Facility category")));
        facility.setFacilityName(request.getFacilityName());
        facility.setFacilityCateCode(request.getFacilityCateCode());
        facility.setFacilityCategories(facilityCategories);
        facility.setImageUrl(request.getImageUrl());
        facility.setStatus(CommonStatusEnum.valueOf(request.getStatus()));
        facility = facilityRepository.save(facility);
        return facilityMapper.toGetFacilityResponse(facility);
    }

    @Override
    @Transactional
    public BaseMessageData changeStatusFacility(Long id, String status) {
        Facility facility = facilityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("Facility")));
        facility.setStatus(CommonStatusEnum.valueOf(status));
        facilityRepository.save(facility);
        return new BaseMessageData(AppContants.UPDATE_SUCCESS_MESSAGE("Facility"));
    }

    @Override
    @Transactional
    public BaseMessageData deletedFacility(Long id) {
        Facility facility = facilityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("Facility")));
        facility.setDeleted(true);
        facilityRepository.save(facility);
        return new BaseMessageData(AppContants.DELETE_SUCCESS_MESSAGE("Facility"));
    }
}
