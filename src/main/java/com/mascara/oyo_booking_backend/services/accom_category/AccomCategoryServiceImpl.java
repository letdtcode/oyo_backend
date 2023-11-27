package com.mascara.oyo_booking_backend.services.accom_category;

import com.mascara.oyo_booking_backend.dtos.BaseMessageData;
import com.mascara.oyo_booking_backend.dtos.request.accom_category.AddAccomCategoryRequest;
import com.mascara.oyo_booking_backend.dtos.request.accom_category.UpdateAccomCategoryRequest;
import com.mascara.oyo_booking_backend.dtos.response.accom_category.GetAccomCategoryResponse;
import com.mascara.oyo_booking_backend.dtos.response.paging.BasePagingData;
import com.mascara.oyo_booking_backend.entities.AccommodationCategories;
import com.mascara.oyo_booking_backend.enums.CommonStatusEnum;
import com.mascara.oyo_booking_backend.exceptions.ResourceExistException;
import com.mascara.oyo_booking_backend.exceptions.ResourceNotFoundException;
import com.mascara.oyo_booking_backend.repositories.AccommodationCategoriesRepository;
import com.mascara.oyo_booking_backend.utils.AppContants;
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
    public BasePagingData<GetAccomCategoryResponse> getAllAccomCategoryWithPaging(Integer pageNum, Integer pageSize, String sortType, String field) {
        Pageable paging = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.fromString(sortType), field));
        Page<AccommodationCategories> accomCategoriesPage = accomCategoriesRepository.getAllWithPaging(paging);
        List<AccommodationCategories> accomCategoriesList = accomCategoriesPage.stream().toList();
        List<GetAccomCategoryResponse> responseList = accomCategoriesList.stream().map(accomCate -> mapper.map(accomCate,
                GetAccomCategoryResponse.class)).collect(Collectors.toList());
        return new BasePagingData<>(responseList,
                accomCategoriesPage.getNumber(),
                accomCategoriesPage.getSize(),
                accomCategoriesPage.getTotalElements());
    }

    @Override
    @Transactional
    public List<GetAccomCategoryResponse> getAllAccomCategory() {
        List<AccommodationCategories> accomCategoryList = accomCategoriesRepository.findAll();
        List<GetAccomCategoryResponse> responseList = accomCategoryList.stream().map(accomCate -> mapper.map(accomCate,
                GetAccomCategoryResponse.class)).collect(Collectors.toList());
        return responseList;
    }

    @Override
    @Transactional
    public BaseMessageData addAccomCategory(AddAccomCategoryRequest request) {
        if (accomCategoriesRepository.existsByAccomCateName(request.getAccomCateName()))
            throw new ResourceExistException(AppContants.FIELD_EXIST_WHEN_ADD_ENTITY("Accom category", "accomCategoryName"));
        if (accomCategoriesRepository.existsByIcon(request.getIcon()))
            throw new ResourceExistException(AppContants.FIELD_EXIST_WHEN_ADD_ENTITY("Accom category", "icon"));
        AccommodationCategories accommodationCategories = AccommodationCategories.builder()
                .accomCateName(request.getAccomCateName())
                .description(request.getDescription())
                .icon(request.getIcon())
                .status(CommonStatusEnum.valueOf(request.getStatus()))
                .build();
        accomCategoriesRepository.save(accommodationCategories);
        return new BaseMessageData(AppContants.ADD_SUCCESS_MESSAGE("Accom category"));
    }

    @Override
    @Transactional
    public BaseMessageData<String> updateAccomCategory(UpdateAccomCategoryRequest request, Long id) {
        AccommodationCategories accommodationCategories = accomCategoriesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("Accom category")));
        accommodationCategories.setAccomCateName(request.getAccomCateName());
        accommodationCategories.setDescription(request.getDescription());
        accommodationCategories.setIcon(request.getIcon());
        accommodationCategories.setStatus(CommonStatusEnum.valueOf(request.getStatus()));
        accomCategoriesRepository.save(accommodationCategories);
        return new BaseMessageData(AppContants.UPDATE_SUCCESS_MESSAGE("accom category"));
    }

    @Override
    @Transactional
    public BaseMessageData changeStatusAccomCategory(Long id, String status) {
        AccommodationCategories accommodationCategories = accomCategoriesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("Accom category")));
        accommodationCategories.setStatus(CommonStatusEnum.valueOf(status));
        accomCategoriesRepository.save(accommodationCategories);
        return new BaseMessageData(AppContants.UPDATE_SUCCESS_MESSAGE("accom category"));
    }

    @Override
    @Transactional
    public BaseMessageData deleteAccomCategory(Long id) {
        AccommodationCategories accommodationCategories = accomCategoriesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("Accom category")));
        accommodationCategories.setDeleted(true);
        accomCategoriesRepository.save(accommodationCategories);
        return new BaseMessageData(AppContants.DELETE_SUCCESS_MESSAGE("accommodation category"));
    }
}
