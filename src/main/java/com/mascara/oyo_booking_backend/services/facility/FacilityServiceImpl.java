package com.mascara.oyo_booking_backend.services.facility;

import com.mascara.oyo_booking_backend.dtos.response.facility.GetFacilityCategoryResponse;
import com.mascara.oyo_booking_backend.entities.Facility;
import com.mascara.oyo_booking_backend.entities.FacilityCategories;
import com.mascara.oyo_booking_backend.repositories.FacilityCategoriesRepository;
import com.mascara.oyo_booking_backend.repositories.FacilityRepository;
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
    private ModelMapper mapper;

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
}
