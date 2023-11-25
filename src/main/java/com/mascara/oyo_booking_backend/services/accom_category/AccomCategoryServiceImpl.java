package com.mascara.oyo_booking_backend.services.accom_category;

import com.mascara.oyo_booking_backend.dtos.request.accom_category.AddAccomCategoryRequest;
import com.mascara.oyo_booking_backend.dtos.request.accom_category.UpdateAccomCategoryRequest;
import com.mascara.oyo_booking_backend.dtos.response.accom_category.GetAccomCategoryResponse;
import com.mascara.oyo_booking_backend.entities.AccommodationCategories;
import com.mascara.oyo_booking_backend.enums.CommonStatusEnum;
import com.mascara.oyo_booking_backend.exceptions.ResourceNotFoundException;
import com.mascara.oyo_booking_backend.repositories.AccommodationCategoriesRepository;
import com.mascara.oyo_booking_backend.utils.AppContants;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 22/10/2023
 * Time      : 7:39 CH
 * Filename  : AccomCategoryServiceImpl
 */
@Service
public class AccomCategoryServiceImpl implements AccomCategoryService {

    @Autowired
    private AccommodationCategoriesRepository accomCategoriesRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    @Transactional
    public List<GetAccomCategoryResponse> getAllAccomCategory() {
        List<AccommodationCategories> accomCategoriesList = accomCategoriesRepository.findAll();
        return accomCategoriesList.stream().map(accomCateItem -> mapper.map(accomCateItem, GetAccomCategoryResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public String addAccomCategory(AddAccomCategoryRequest request) {
        AccommodationCategories accommodationCategories = AccommodationCategories.builder()
                .accomCateName(request.getAccomCateName())
                .description(request.getDescription())
                .status(CommonStatusEnum.ENABLE)
                .build();
        accomCategoriesRepository.save(accommodationCategories);
        return AppContants.ADD_SUCCESS_MESSAGE("Accom category");
    }

    @Override
    @Transactional
    public String updateAccomCategory(UpdateAccomCategoryRequest request, Long id) {
        AccommodationCategories accommodationCategories = accomCategoriesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("Accom category")));
        accommodationCategories.setAccomCateName(request.getAccomCateName());
        accommodationCategories.setDescription(request.getDescription());
        accommodationCategories.setStatus(request.getStatus());
        accomCategoriesRepository.save(accommodationCategories);
        return AppContants.UPDATE_SUCCESS_MESSAGE("accom category");
    }

    @Override
    @Transactional
    public String deleteAccomCategory(Long id) {
        AccommodationCategories accommodationCategories = accomCategoriesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("Accom category")));
        accommodationCategories.setDeleted(true);
        accomCategoriesRepository.save(accommodationCategories);
        return AppContants.DELETE_SUCCESS_MESSAGE("accommodation category");
    }
}
