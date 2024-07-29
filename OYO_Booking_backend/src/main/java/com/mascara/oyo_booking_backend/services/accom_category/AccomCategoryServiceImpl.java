package com.mascara.oyo_booking_backend.services.accom_category;

import com.mascara.oyo_booking_backend.common.constant.MessageConstant;
import com.mascara.oyo_booking_backend.dtos.accom_category.request.AddAccomCategoryRequest;
import com.mascara.oyo_booking_backend.dtos.accom_category.request.UpdateAccomCategoryRequest;
import com.mascara.oyo_booking_backend.dtos.accom_category.response.GetAccomCategoryResponse;
import com.mascara.oyo_booking_backend.dtos.base.BaseMessageData;
import com.mascara.oyo_booking_backend.dtos.base.BasePagingData;
import com.mascara.oyo_booking_backend.entities.accommodation.AccommodationCategories;
import com.mascara.oyo_booking_backend.common.enums.CommonStatusEnum;
import com.mascara.oyo_booking_backend.common.exceptions.ResourceExistException;
import com.mascara.oyo_booking_backend.common.exceptions.ResourceNotFoundException;
import com.mascara.oyo_booking_backend.common.mapper.accommodation.AccommodationCategoriesMapper;
import com.mascara.oyo_booking_backend.repositories.AccommodationCategoriesRepository;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class AccomCategoryServiceImpl implements AccomCategoryService {

    private final AccommodationCategoriesRepository accomCategoriesRepository;

    private final AccommodationCategoriesMapper accommodationCategoriesMapper;

    @Override
    @Transactional
    public BasePagingData<GetAccomCategoryResponse> getAllAccomCategoryWithPaging(Integer pageNum, Integer pageSize, String sortType, String field) {
        Pageable paging = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.fromString(sortType), field));
        Page<AccommodationCategories> accomCategoriesPage = accomCategoriesRepository.getAllWithPaging(paging);
        List<AccommodationCategories> accomCategoriesList = accomCategoriesPage.stream().toList();
        List<GetAccomCategoryResponse> responseList = accomCategoriesList.stream()
                .map(accomCate -> accommodationCategoriesMapper.toGetAccomCategoryResponse(accomCate)).collect(Collectors.toList());
        return new BasePagingData<>(responseList,
                accomCategoriesPage.getNumber(),
                accomCategoriesPage.getSize(),
                accomCategoriesPage.getTotalElements());
    }

    @Override
    @Transactional
    public List<GetAccomCategoryResponse> getAllAccomCategory() {
        List<AccommodationCategories> accomCategoryList = accomCategoriesRepository.findAll();
        List<GetAccomCategoryResponse> responseList = accomCategoryList.stream()
                .map(accomCate -> accommodationCategoriesMapper.toGetAccomCategoryResponse(accomCate)).collect(Collectors.toList());
        return responseList;
    }

    @Override
    @Transactional
    public GetAccomCategoryResponse addAccomCategory(AddAccomCategoryRequest request) {
        if (accomCategoriesRepository.existsByAccomCateName(request.getAccomCateName()))
            throw new ResourceExistException(MessageConstant.FIELD_EXIST_WHEN_ADD_ENTITY("Accom category", "accomCategoryName"));
        if (accomCategoriesRepository.existsByIcon(request.getIcon()))
            throw new ResourceExistException(MessageConstant.FIELD_EXIST_WHEN_ADD_ENTITY("Accom category", "icon"));
        AccommodationCategories accommodationCategories = AccommodationCategories.builder()
                .accomCateName(request.getAccomCateName())
                .description(request.getDescription())
                .icon(request.getIcon())
                .status(CommonStatusEnum.valueOf(request.getStatus()))
                .build();
        accommodationCategories = accomCategoriesRepository.save(accommodationCategories);
        return accommodationCategoriesMapper.toGetAccomCategoryResponse(accommodationCategories);
    }

    @Override
    @Transactional
    public GetAccomCategoryResponse updateAccomCategory(UpdateAccomCategoryRequest request, Long id) {
        AccommodationCategories accommodationCategories = accomCategoriesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstant.NOT_FOUND_MESSAGE("Accom category")));
        accommodationCategories.setAccomCateName(request.getAccomCateName());
        accommodationCategories.setDescription(request.getDescription());
        accommodationCategories.setIcon(request.getIcon());
        accommodationCategories.setStatus(CommonStatusEnum.valueOf(request.getStatus()));
        accommodationCategories = accomCategoriesRepository.save(accommodationCategories);
        return accommodationCategoriesMapper.toGetAccomCategoryResponse(accommodationCategories);
    }

    @Override
    @Transactional
    public BaseMessageData changeStatusAccomCategory(Long id, String status) {
        AccommodationCategories accommodationCategories = accomCategoriesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstant.NOT_FOUND_MESSAGE("Accom category")));
        accommodationCategories.setStatus(CommonStatusEnum.valueOf(status));
        accomCategoriesRepository.save(accommodationCategories);
        return new BaseMessageData(MessageConstant.UPDATE_SUCCESS_MESSAGE("accom category"));
    }

    @Override
    @Transactional
    public BaseMessageData deleteAccomCategory(Long id) {
        AccommodationCategories accommodationCategories = accomCategoriesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstant.NOT_FOUND_MESSAGE("Accom category")));
        accommodationCategories.setDeleted(true);
        accomCategoriesRepository.save(accommodationCategories);
        return new BaseMessageData(MessageConstant.DELETE_SUCCESS_MESSAGE("accommodation category"));
    }
}
