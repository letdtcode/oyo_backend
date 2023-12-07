package com.mascara.oyo_booking_backend.services.surcharge;

import com.mascara.oyo_booking_backend.dtos.response.surcharge.SurchargeCategoryResponse;
import com.mascara.oyo_booking_backend.entities.SurchargeCategory;
import com.mascara.oyo_booking_backend.repositories.SurchargeCategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    private ModelMapper modelMapper;

    @Override
    public List<SurchargeCategoryResponse> getAllSurchargeCategoryByStatus(String status) {
        List<SurchargeCategory> surchargeCategoryList = surchargeCategoryRepository.findAllByStatus(status);
        return surchargeCategoryList.stream().map(surchargeCategory -> modelMapper.map(surchargeCategory, SurchargeCategoryResponse.class)).toList();
    }
}
