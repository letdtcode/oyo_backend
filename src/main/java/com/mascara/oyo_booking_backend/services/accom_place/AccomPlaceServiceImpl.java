package com.mascara.oyo_booking_backend.services.accom_place;

import com.mascara.oyo_booking_backend.dtos.request.accom_place.AddAccomPlaceRequest;
import com.mascara.oyo_booking_backend.dtos.request.accom_place.GetAccomPlaceFilterRequest;
import com.mascara.oyo_booking_backend.dtos.response.accommodation.GetAccomPlaceResponse;
import com.mascara.oyo_booking_backend.dtos.response.facility.InfoFacilityResponse;
import com.mascara.oyo_booking_backend.dtos.response.general.MessageResponse;
import com.mascara.oyo_booking_backend.entities.*;
import com.mascara.oyo_booking_backend.enums.CommonStatusEnum;
import com.mascara.oyo_booking_backend.exceptions.ResourceNotFoundException;
import com.mascara.oyo_booking_backend.repositories.*;
import com.mascara.oyo_booking_backend.utils.AppContants;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 22/10/2023
 * Time      : 7:40 CH
 * Filename  : AccomPlaceServiceImpl
 */
@Service
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

    @Autowired
    private ModelMapper mapper;

    @Override
    @Transactional
    public MessageResponse addAccomPlace(AddAccomPlaceRequest request, String mail) {
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
        String addressDetail = request.getNumHouseAndStreetName() + ", " + ward.getWardName() + ", " + district.getDistrictName() + ", " + province.getProvinceName();
        AccomPlace accomPlace = AccomPlace.builder()
                .accomName(request.getAccomName())
                .description(request.getDescription())
                .addressDetail(addressDetail)
                .gradeRate(5F)
                .numReview(0L)
                .user(user)
                .userId(user.getId())
                .accommodationCategories(accomCategories)
                .accomCateId(accomCategories.getId())
                .province(province)
                .provinceCode(request.getProvinceCode())
                .districtCode(district.getDistrictCode())
                .wardCode(ward.getWardCode())
                .acreage(request.getAcreage())
                .numPeople(request.getNumPeople())
                .numBathRoom(request.getNumBathRoom())
                .numBedRoom(request.getNumBedRoom())
                .numKitchen(request.getNumKitchen())
                .numView(0)
                .pricePerNight(request.getPricePerNight())
                .facilitySet(facilitySet)
                .status(CommonStatusEnum.ACTIVE)
                .build();
        accomPlaceRepository.save(accomPlace);
        return new MessageResponse(AppContants.ADD_SUCCESS_MESSAGE("Accom Place"));
    }

    @Override
    @Transactional
    public List<GetAccomPlaceResponse> getAllAccomPlaceWithPaging(Integer pageNum, Integer pageSize) {
        Pageable paging = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.DESC, "created_date"));
        List<AccomPlace> accomPlaceList = accomPlaceRepository.findAll(paging).toList();
        return accomPlaceList.stream().map(accomPlace -> mapper.map(accomPlace,
                        GetAccomPlaceResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<GetAccomPlaceResponse> getAccomPlaceFilterWithPaging(GetAccomPlaceFilterRequest filter,
                                                                     Integer pageNum,
                                                                     Integer pageSize) {
        int length = 0;
        if (filter.getFacilityName() != null) {
            length = filter.getFacilityName().size();
        }
        if (filter.getFacilityName() == null || filter.getFacilityName().isEmpty()) {
            filter.setFacilityName(List.of(UUID.randomUUID().toString()));
        }
        Pageable paging = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.DESC, "created_date"));
        List<AccomPlace> accomPlaceList = accomPlaceRepository.getPageWithFullFilter(filter.getProvinceCode(),
                filter.getDistrictCode(),
                filter.getWardCode(),
                filter.getPriceFrom(),
                filter.getPriceTo(),
                filter.getFacilityName(),
                length,
                filter.getNumBathroom(),
                filter.getNumPeople(),
                filter.getNumBed(),
                paging).toList();
        return accomPlaceList.stream().map(accomPlace -> mapper.map(accomPlace,
                        GetAccomPlaceResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public GetAccomPlaceResponse getAccomPlaceDetails(Long id) {
        AccomPlace accomPlace = accomPlaceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(AppContants
                        .NOT_FOUND_MESSAGE("accom place")));
        List<String> imageAccom = new ArrayList();
        List<InfoFacilityResponse> facilityResponseList = new ArrayList();
        for (ImageAccom imgAccom : accomPlace.getImageAccoms()) {
            imageAccom.add(imgAccom.getImgAccomLink());
        }
        for (Facility facility : accomPlace.getFacilitySet()) {
            InfoFacilityResponse facilityResponse = mapper.map(facility, InfoFacilityResponse.class);
            facilityResponseList.add(facilityResponse);
        }

        Province province = provinceRepository.findByProvinceCode(accomPlace.getProvinceCode())
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("province")));
        District district = districtRepository.findByDistrictCode(accomPlace.getDistrictCode())
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("district")));
        String addressGeneral = district.getDistrictName() + ", " + province.getProvinceName();
        GetAccomPlaceResponse accomPlaceResponse = mapper.map(accomPlace, GetAccomPlaceResponse.class);
        accomPlaceResponse.setAddressGeneral(addressGeneral);
        accomPlaceResponse.setImageAccomsUrls(imageAccom);
        accomPlaceResponse.setInfoFacilityResponseList(facilityResponseList);
        return accomPlaceResponse;
    }
}
