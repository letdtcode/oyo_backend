package com.mascara.oyo_booking_backend.services.surcharge;

import com.mascara.oyo_booking_backend.dtos.BaseMessageData;
import com.mascara.oyo_booking_backend.dtos.request.surcharge_category.AddSurchargeCategoryRequest;
import com.mascara.oyo_booking_backend.dtos.request.surcharge_category.UpdateSurchargeCategoryRequest;
import com.mascara.oyo_booking_backend.dtos.response.paging.BasePagingData;
import com.mascara.oyo_booking_backend.dtos.response.surcharge.GetSurchargeCategoryResponse;
import com.mascara.oyo_booking_backend.entities.SurchargeCategory;
import com.mascara.oyo_booking_backend.enums.CommonStatusEnum;
import com.mascara.oyo_booking_backend.exceptions.ResourceNotFoundException;
import com.mascara.oyo_booking_backend.repositories.SurchargeCategoryRepository;
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
 * Date      : 07/12/2023
 * Time      : 8:48 CH
 * Filename  : SurchargeServiceImpl
 */
@Service
public class SurchargeServiceImpl implements SurchargeService {

    @Autowired
    private SurchargeCategoryRepository surchargeCategoryRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    @Transactional
    public BasePagingData<GetSurchargeCategoryResponse> getAllSurchargeCategoryWithPaging(Integer pageNum, Integer pageSize, String sortType, String field) {
        Pageable paging = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.valueOf(sortType), field));
        Page<SurchargeCategory> surchargeCategoryPage = surchargeCategoryRepository.getAllWithPaging(paging);
        List<SurchargeCategory> surchargeCategoryList = surchargeCategoryPage.stream().toList();
        List<GetSurchargeCategoryResponse> responseList = surchargeCategoryList.stream().map(surchargeCategory -> mapper.map(surchargeCategory,
                GetSurchargeCategoryResponse.class)).collect(Collectors.toList());
        return new BasePagingData<>(responseList,
                surchargeCategoryPage.getNumber(),
                surchargeCategoryPage.getSize(),
                surchargeCategoryPage.getTotalElements());
    }

    @Override
    @Transactional
    public List<GetSurchargeCategoryResponse> getAllSurchargeCategoryByStatus(String status) {
        List<SurchargeCategory> surchargeCategoryList = surchargeCategoryRepository.findAllByStatus(status);
        return surchargeCategoryList.stream().map(surchargeCategory -> mapper.map(surchargeCategory, GetSurchargeCategoryResponse.class)).toList();
    }


    @Override
    @Transactional
    public GetSurchargeCategoryResponse addSurchargeCategory(AddSurchargeCategoryRequest request) {
        int count = (int) surchargeCategoryRepository.count();
        SurchargeCategory surchargeCategory = SurchargeCategory.builder()
                .surchargeCateName(request.getSurchargeCateName())
                .surchargeCode(GenerateCodeUtils.generateCode(AliasUtils.SURCHARGE_CATEGORY, count))
                .status(CommonStatusEnum.valueOf(request.getStatus()))
                .build();
        surchargeCategory = surchargeCategoryRepository.save(surchargeCategory);
        return mapper.map(surchargeCategory, GetSurchargeCategoryResponse.class);
    }

    @Override
    @Transactional
    public GetSurchargeCategoryResponse updateSurchargeCategory(UpdateSurchargeCategoryRequest request,Long id) {
        SurchargeCategory surchargeCategory = surchargeCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("Surcharge category")));
        surchargeCategory.setSurchargeCateName(request.getSurchargeCateName());
        surchargeCategory.setStatus(CommonStatusEnum.valueOf(request.getStatus()));
        surchargeCategory = surchargeCategoryRepository.save(surchargeCategory);
        return mapper.map(surchargeCategory, GetSurchargeCategoryResponse.class);
    }

    @Override
    @Transactional
    public BaseMessageData changeStatusSurchargeCategory(Long id, String status) {
        SurchargeCategory surchargeCategory = surchargeCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("Surcharge category")));
        surchargeCategory.setStatus(CommonStatusEnum.valueOf(status));
        surchargeCategoryRepository.save(surchargeCategory);
        return new BaseMessageData(AppContants.UPDATE_SUCCESS_MESSAGE("Surcharge category"));
    }

    @Override
    @Transactional
    public BaseMessageData deletedSurchargeCategory(Long id) {
        SurchargeCategory surchargeCategory = surchargeCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("Surcharge category")));
        surchargeCategory.setDeleted(true);
        surchargeCategoryRepository.save(surchargeCategory);
        return new BaseMessageData(AppContants.DELETE_SUCCESS_MESSAGE("Surcharge category"));
    }
}
