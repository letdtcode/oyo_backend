package com.mascara.oyo_booking_backend.services.accom_category;

import com.mascara.oyo_booking_backend.dtos.request.accom_category.AddAccomCategoryRequest;
import com.mascara.oyo_booking_backend.dtos.request.accom_category.UpdateAccomCategoryRequest;
import com.mascara.oyo_booking_backend.dtos.response.accom_category.GetAllAccomCategoryResponse;
import com.mascara.oyo_booking_backend.dtos.response.general.MessageResponse;
import com.mascara.oyo_booking_backend.entities.AccommodationCategories;
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
    public List<GetAllAccomCategoryResponse> getAllAccomCategory() {
        List<AccommodationCategories> accomCategoriesList = accomCategoriesRepository.findAll();
        return accomCategoriesList.stream().map(accomCateItem -> mapper.map(accomCateItem, GetAllAccomCategoryResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AccommodationCategories addAccomCategory(AddAccomCategoryRequest request) {
        AccommodationCategories accommodationCategories = AccommodationCategories.builder()
                .accomCateName(request.getAccomCateName())
                .description(request.getDescription()).build();
        return accomCategoriesRepository.save(accommodationCategories);
    }

    @Override
    @Transactional
    public AccommodationCategories updateAccomCategory(UpdateAccomCategoryRequest request) {
        AccommodationCategories accommodationCategories = accomCategoriesRepository.findByAccomCateName(request.getAccomCateName())
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("accommodation category")));
        accommodationCategories.setAccomCateName(request.getAccomCateName());
        accommodationCategories.setDescription(request.getDescription());
        accommodationCategories.setStatus(request.getStatus());
        return accomCategoriesRepository.save(accommodationCategories);
    }

    @Override
    @Transactional
    public MessageResponse deleteAccomCategory(String accomCateName) {
        AccommodationCategories accommodationCategories = accomCategoriesRepository.findByAccomCateName(accomCateName)
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("accommodation category")));
        accomCategoriesRepository.delete(accommodationCategories);
        return new MessageResponse(AppContants.DELETE_SUCCESS_MESSAGE("accommodation category"));
    }
}
