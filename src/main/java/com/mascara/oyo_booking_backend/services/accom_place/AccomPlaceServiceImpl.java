package com.mascara.oyo_booking_backend.services.accom_place;

import com.mascara.oyo_booking_backend.dtos.request.accommodation.AddAccommodationRequest;
import com.mascara.oyo_booking_backend.dtos.response.general.MessageResponse;
import com.mascara.oyo_booking_backend.entities.*;
import com.mascara.oyo_booking_backend.enums.CommonStatusEnum;
import com.mascara.oyo_booking_backend.exceptions.ResourceNotFoundException;
import com.mascara.oyo_booking_backend.repositories.*;
import com.mascara.oyo_booking_backend.utils.AppContants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 22/10/2023
 * Time      : 7:40 CH
 * Filename  : AccomPlaceServiceImpl
 */
public class AccomPlaceServiceImpl implements AccomPlaceService {

    @Autowired
    private AccomPlaceRepository accomPlaceRepository;

    @Autowired
    private AccommodationCategoriesRepository accommodationCategoriesRepository;

    @Autowired
    private ProvinceRepository provinceRepository;

    @Autowired
    private DistrictRepository districtRepository;

    @Autowired
    private WardRepository wardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FacilityRepository facilityRepository;

    @Override
    @Transactional
    public MessageResponse addAccomPlace(AddAccommodationRequest request, String mail) {
        Province province = provinceRepository.findByProvinceCode(request.getProvinceCode())
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("province")));
        District district = districtRepository.findByDistrictCode(request.getDistrictCode())
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("district")));
        Ward ward = wardRepository.findByWardCode(request.getWardCode())
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("ward")));
        User user = userRepository.findByMail(mail)
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("user")));
        AccommodationCategories accomCategories = accommodationCategoriesRepository.findByAccomCateName(request.getAccomCateName())
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("accommodation category")));

        Set<Facility> facilitySet = new HashSet<>();
        for (String facilityName : request.getFacilityNameList()) {
            facilitySet.add(facilityRepository.findByFacilityName(facilityName)
                    .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("facility"))));
        }
        String addressDetail = request.getNumHouseAndStreetName() + " " + ward.getWardName() + district.getDistrictName() + province.getProvinceName();
        AccomPlace accomPlace = AccomPlace.builder()
                .accomName(request.getAccomName())
                .description(request.getDescription())
                .addressDetail(addressDetail)
                .gradeRate(5F)
                .numReview(0L)
                .user(user)
                .accommodationCategories(accomCategories)
                .province(province)
                .districtCode(district.getDistrictCode())
                .wardCode(ward.getWardCode())
                .acreage(request.getAcreage())
                .numPeople(request.getNumPeople())
                .numBathRoom(request.getNumBathRoom())
                .numBed(request.getNumBed())
                .pricePerNight(request.getPricePerNight())
                .facilitySet(facilitySet)
                .status(CommonStatusEnum.ACTIVE)
                .build();
        accomPlaceRepository.save(accomPlace);
        return new MessageResponse(AppContants.ADD_SUCCESS_MESSAGE("Accom Place"));
    }
}
