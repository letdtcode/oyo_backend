package com.mascara.oyo_booking_backend.services.facility_category;

import com.mascara.oyo_booking_backend.dtos.request.facility_category.AddFacilityCategoryRequest;
import com.mascara.oyo_booking_backend.dtos.request.facility_category.UpdateFacilityCategoryRequest;
import com.mascara.oyo_booking_backend.dtos.response.facility.GetFacilityCategoryResponse;
import com.mascara.oyo_booking_backend.dtos.response.paging.BasePagingData;
import com.mascara.oyo_booking_backend.entities.Facility;
import com.mascara.oyo_booking_backend.entities.FacilityCategories;
import com.mascara.oyo_booking_backend.enums.CommonStatusEnum;
import com.mascara.oyo_booking_backend.exceptions.ResourceNotFoundException;
import com.mascara.oyo_booking_backend.repositories.FacilityCategoriesRepository;
import com.mascara.oyo_booking_backend.utils.AliasUtils;
import com.mascara.oyo_booking_backend.utils.AppContants;
import com.mascara.oyo_booking_backend.utils.GenerateCodeUtils;
import org.modelmapper.ModelMapper;
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
 * Filename  : FacilAccomCategoryServiceImpl
 */
@Service
public class FacilityCategoryServiceImpl implements FacilityCategoryService {
    @Autowired
    private FacilityCategoriesRepository facilityCategoriesRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    @Transactional
    public BasePagingData<GetFacilityCategoryResponse> getAllFacilityCategoryWithPaging(Integer pageNum, Integer pageSize) {
        Pageable paging = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.DESC, "created_date"));
        Page<FacilityCategories> facilityCategoriesPage = facilityCategoriesRepository.getAllWithPaging(paging);

        List<FacilityCategories> facilityCategoriesList = facilityCategoriesPage.stream().toList();
        List<GetFacilityCategoryResponse> responses = facilityCategoriesList.stream()
                .map(facilityCate -> mapper.map(facilityCate,
                        GetFacilityCategoryResponse.class)).collect(Collectors.toList());
        for (int i = 0; i < facilityCategoriesList.size(); i++) {
            List<Facility> facilities = facilityCategoriesList.get(i).getFacilitySet().stream().toList();
            List<String> facilityListName = facilities.stream().map(facility -> facility.getFacilityName())
                    .collect(Collectors.toList());
            responses.get(i).setFacilityListName(facilityListName);
        }
        return new BasePagingData<>(responses,
                facilityCategoriesPage.getNumber(),
                facilityCategoriesPage.getSize(),
                facilityCategoriesPage.getTotalElements());
    }

    @Override
    @Transactional
    public List<GetFacilityCategoryResponse> getAllDataFacility() {
        List<FacilityCategories> facilityCategoriesList = facilityCategoriesRepository.findAll();
        List<GetFacilityCategoryResponse> facilityCategoryResponses = facilityCategoriesList.stream()
                .map(facilityCate -> mapper.map(facilityCate, GetFacilityCategoryResponse.class))
                .collect(Collectors.toList());
        for (int i = 0; i < facilityCategoriesList.size(); i++) {
            List<Facility> facilities = facilityCategoriesList.get(i).getFacilitySet().stream().toList();
            List<String> facilityListName = facilities.stream().map(facility -> facility.getFacilityName())
                    .collect(Collectors.toList());
            facilityCategoryResponses.get(i).setFacilityListName(facilityListName);
        }
        return facilityCategoryResponses;
    }

    @Override
    @Transactional
    public String addFacilityCategory(AddFacilityCategoryRequest request) {
        int count = (int) facilityCategoriesRepository.count();
        FacilityCategories facilityCategories = FacilityCategories.builder()
                .faciCateName(request.getFaciCateName())
                .faciCateCode(GenerateCodeUtils.generateCode(AliasUtils.FACILITY_CATEGORY, count))
                .status(CommonStatusEnum.valueOf(request.getStatus()))
                .build();
        facilityCategoriesRepository.save(facilityCategories);
        return AppContants.ADD_SUCCESS_MESSAGE("Facility category");
    }


    @Override
    @Transactional
    public String updateFacilityCategory(UpdateFacilityCategoryRequest request, Long id) {
        FacilityCategories facilityCategories = facilityCategoriesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("Facility category")));
        facilityCategories.setFaciCateName(request.getFaciCateName());
        facilityCategories.setStatus(CommonStatusEnum.valueOf(request.getStatus()));
        facilityCategoriesRepository.save(facilityCategories);
        return AppContants.UPDATE_SUCCESS_MESSAGE("Facility category");
    }

    @Override
    @Transactional
    public String changeStatusFacilityCategory(Long id, String status) {
        FacilityCategories facilityCategories = facilityCategoriesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("Facility category")));
        facilityCategories.setStatus(CommonStatusEnum.valueOf(status));
        facilityCategoriesRepository.save(facilityCategories);
        return AppContants.UPDATE_SUCCESS_MESSAGE("Facility category");
    }

    @Override
    @Transactional
    public String deletedFacilityCategory(Long id) {
        FacilityCategories facilityCategories = facilityCategoriesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("Facility category")));
        facilityCategories.setDeleted(true);
        facilityCategoriesRepository.save(facilityCategories);
        return AppContants.DELETE_SUCCESS_MESSAGE("Facility category");
    }
}