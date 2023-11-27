package com.mascara.oyo_booking_backend.services.accom_place;

import com.mascara.oyo_booking_backend.dtos.BaseMessageData;
import com.mascara.oyo_booking_backend.dtos.request.accom_place.AddAccomPlaceRequest;
import com.mascara.oyo_booking_backend.dtos.request.accom_place.GetAccomPlaceFilterRequest;
import com.mascara.oyo_booking_backend.dtos.response.accommodation.GetAccomPlaceResponse;
import com.mascara.oyo_booking_backend.dtos.response.paging.BasePagingData;
import com.mascara.oyo_booking_backend.entities.*;
import com.mascara.oyo_booking_backend.enums.AccomStatusEnum;
import com.mascara.oyo_booking_backend.exceptions.ResourceNotFoundException;
import com.mascara.oyo_booking_backend.mapper.AccomPlaceMapper;
import com.mascara.oyo_booking_backend.repositories.*;
import com.mascara.oyo_booking_backend.services.storage.cloudinary.CloudinaryService;
import com.mascara.oyo_booking_backend.utils.AppContants;
import com.mascara.oyo_booking_backend.utils.SlugsUtils;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 22/10/2023
 * Time      : 7:40 CH
 * Filename  : AccomPlaceServiceImpl
 */
@Slf4j
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

    @Autowired
    private AccomPlaceMapper accomPlaceMapper;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private ImageAccomRepository imageAccomRepository;

    @Autowired
    private FacilityCategoriesRepository facilityCategoriesRepository;

    @Override
    @Transactional
    public GetAccomPlaceResponse addAccomPlace(AddAccomPlaceRequest request) {
        Province province = provinceRepository.findByProvinceCode(request.getProvinceCode())
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("province")));
        District district = districtRepository.findByDistrictCode(request.getDistrictCode())
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("district")));
        Ward ward = wardRepository.findByWardCode(request.getWardCode())
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("ward")));


        AccommodationCategories accomCategories = accommodationCategoriesRepository.findByAccomCateName(request.getAccomCateName())
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("accommodation category")));

        Set<Facility> facilitySet = new HashSet<>();
        for (String facilityName : request.getFacilityNameList()) {
            facilitySet.add(facilityRepository.findByFacilityName(facilityName)
                    .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("facility"))));
        }
        String addressDetail = request.getNumHouseAndStreetName() + ", "
                + ward.getWardName() + ", " + district.getDistrictName() + ", " + province.getProvinceName();
        String slugs = SlugsUtils.toSlug(request.getAccomName());
        AccomPlace accomPlace = AccomPlace.builder()
                .accomName(request.getAccomName())
                .description(request.getDescription())
                .addressDetail(addressDetail)
                .gradeRate(5F)
                .numReview(0L)
                .slugs(slugs)
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
                .numView(0L)
                .pricePerNight(request.getPricePerNight())
                .facilitySet(facilitySet)
                .status(AccomStatusEnum.ENABLE)
                .build();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            User user = userRepository.findByMail(authentication.getName()).get();
            accomPlace.setUser(user);
            accomPlace.setUserId(user.getId());
        } else {
            User user = userRepository.findByMail("client1@gmail.com")
                    .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("User")));
            accomPlace.setUser(user);
            accomPlace.setUserId(user.getId());
            accomPlace.setCreatedBy("dev");
            accomPlace.setLastModifiedBy("dev");
        }
        accomPlace = accomPlaceRepository.save(accomPlace);
        GetAccomPlaceResponse response = accomPlaceMapper.toDTO(accomPlace);
        return response;
    }

    @Override
    public BaseMessageData addImageAccomPlace(List<MultipartFile> files, Long id) {
        if (!files.isEmpty()) {
            for (int i = 0; i < files.size(); i++) {
                AccomPlace accomPlace = accomPlaceRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("Accom Place")));
                String pathImg = cloudinaryService.store(files.get(i));
                ImageAccom imageAccom = ImageAccom.builder()
                        .imgAccomLink(pathImg)
                        .accomPlace(accomPlace)
                        .accomPlaceId(accomPlace.getId())
                        .build();
                imageAccomRepository.save(imageAccom);
            }
            return new BaseMessageData(AppContants.ADD_SUCCESS_MESSAGE("Images accom place"));
        }
        throw new ResourceNotFoundException(AppContants.FILE_IS_NULL);
    }

    @Override
    @Transactional
    public BasePagingData<GetAccomPlaceResponse> getAllAccomPlaceWithPaging(Integer pageNum, Integer pageSize, String sortType, String field) {
        Pageable paging = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.fromString(sortType), field));
        Page<AccomPlace> accomPlacePage = accomPlaceRepository.getAllWithPaging(paging);
        List<AccomPlace> accomPlaceList = accomPlacePage.stream().toList();
        List<GetAccomPlaceResponse> responseList = accomPlaceList.stream().map(accomPlace -> accomPlaceMapper.toDTO(accomPlace))
                .collect(Collectors.toList());
        return new BasePagingData<>(responseList,
                accomPlacePage.getNumber(),
                accomPlacePage.getSize(),
                accomPlacePage.getTotalElements());
    }

    @Override
    @Transactional
    public BasePagingData<GetAccomPlaceResponse> getAccomPlaceFilterWithPaging(GetAccomPlaceFilterRequest filter,
                                                                               Integer pageNum,
                                                                               Integer pageSize,
                                                                               String sortType,
                                                                               String field) {
        int length = 0;
        if (filter.getFacilityName() != null) {
            length = filter.getFacilityName().size();
        }
        if (filter.getFacilityName() == null || filter.getFacilityName().isEmpty()) {
            filter.setFacilityName(List.of(UUID.randomUUID().toString()));
        }
        Pageable paging = PageRequest.of(pageNum, 10, Sort.by(Sort.Direction.valueOf(sortType), field));
        Page<AccomPlace> accomPlacePage = accomPlaceRepository.getPageWithFullFilter(filter.getProvinceCode(),
                filter.getDistrictCode(),
                filter.getWardCode(),
                filter.getPriceFrom(),
                filter.getPriceTo(),
                filter.getFacilityName(),
                length,
                filter.getNumBathroom(),
                filter.getNumPeople(),
                filter.getNumBed(),
                paging);
        List<AccomPlace> accomPlaceList = accomPlacePage.stream().toList();
        List<GetAccomPlaceResponse> responseList = accomPlaceList.stream().map(accomPlace -> accomPlaceMapper.toDTO(accomPlace))
                .collect(Collectors.toList());
        return new BasePagingData<>(responseList,
                accomPlacePage.getNumber(),
                accomPlacePage.getSize(),
                accomPlacePage.getTotalElements());
    }

    @Override
    @Transactional
    public GetAccomPlaceResponse getAccomPlaceDetails(Long id) {
        AccomPlace accomPlace = accomPlaceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(AppContants
                        .NOT_FOUND_MESSAGE("accom place")));
//        List<String> imageAccom = new ArrayList();
//        List<GetFacilityCategoryResponse> facilityCategoryList = new ArrayList();
//        List<GetFacilityResponse> facilityList = new ArrayList<>();
//        for (ImageAccom imgAccom : accomPlace.getImageAccoms()) {
//            imageAccom.add(imgAccom.getImgAccomLink());
//        }
//        for (Facility facility : accomPlace.getFacilitySet()) {
//            GetFacilityResponse facilityResponse = mapper.map(facility, GetFacilityResponse.class);
//            facilityResponse.setStatus(facility.getStatus().toString());
//            facilityList.add(facilityResponse);
//        }
//        log.error(facilityList.toString());
//        for (GetFacilityResponse facilityResponse : facilityList) {
//            boolean addCate = true;
//            for (GetFacilityCategoryResponse facilityCategoryResponse : facilityCategoryList) {
//                if (facilityResponse.getFacilityCateCode().equals(facilityCategoryResponse.getFaciCateCode())) {
//                    addCate = false;
//                    break;
//                }
//            }
//            if (addCate == true) {
//                FacilityCategories facilityCategories = facilityCategoriesRepository.findByFaciCateCode(facilityResponse
//                        .getFacilityCateCode()).get();
//                GetFacilityCategoryResponse faciCateResponse = mapper.map(facilityCategories, GetFacilityCategoryResponse.class);
//                faciCateResponse.setStatus(facilityCategories.getStatus().toString());
//                facilityCategoryList.add(faciCateResponse);
//            }
//        }
//
//        for (GetFacilityCategoryResponse faciCate : facilityCategoryList) {
//            List<GetFacilityResponse> facilityResponseList = new ArrayList<>();
//            for (GetFacilityResponse facility : facilityList) {
//                if (faciCate.getFaciCateCode().equals(facility.getFacilityCateCode()))
//                    facilityResponseList.add(facility);
//            }
//            faciCate.setInfoFacilityList(facilityResponseList);
//        }
////
//        String accomCateName = accomPlace.getAccommodationCategories().getAccomCateName();
////
//        Province province = provinceRepository.findByProvinceCode(accomPlace.getProvinceCode())
//                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("province")));
//        District district = districtRepository.findByDistrictCode(accomPlace.getDistrictCode())
//                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("district")));
//        String addressGeneral = district.getDistrictName() + ", " + province.getProvinceName();
//
////
//        GetAccomPlaceResponse accomPlaceResponse = mapper.map(accomPlace, GetAccomPlaceResponse.class);
//        accomPlaceResponse.setAddressGeneral(addressGeneral);
//        accomPlaceResponse.setImageAccomsUrls(imageAccom);
//        accomPlaceResponse.setFacilityCategoryList(facilityCategoryList);
//        accomPlaceResponse.setAccomCateName(accomCateName);
        return accomPlaceMapper.toDTO(accomPlace);
    }

    @Override
    @Transactional
    public BasePagingData<GetAccomPlaceResponse> getTopAccomPlaceByField(Integer pageNum, Integer pageSize, String sortType, String field) {
        Pageable paging = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.valueOf(sortType), field));
        Page<AccomPlace> accomPlacePage = accomPlaceRepository.getAllWithPaging(paging);
        List<AccomPlace> accomPlaceList = accomPlacePage.stream().toList();
        List<GetAccomPlaceResponse> responseList = accomPlaceList.stream().map(accomPlace -> accomPlaceMapper.toDTO(accomPlace))
                .collect(Collectors.toList());
        return new BasePagingData<>(responseList,
                accomPlacePage.getNumber(),
                accomPlacePage.getSize(),
                accomPlacePage.getTotalElements());
    }

    @Override
    @Transactional
    public BaseMessageData changeStatusAccomPlace(Long id, String status) {
        AccomPlace accomPlace = accomPlaceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("Accom place")));
        accomPlaceRepository.changeStatusAccomPlace(id, status);
        return new BaseMessageData(AppContants.UPDATE_SUCCESS_MESSAGE("accom place"));
    }

    @Override
    @Transactional
    public BaseMessageData deleteAccomPlace(Long id) {
        AccomPlace accomPlace = accomPlaceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("Accom place")));
        accomPlace.setDeleted(true);
        accomPlaceRepository.save(accomPlace);
        return new BaseMessageData(AppContants.DELETE_SUCCESS_MESSAGE("accom place"));
    }
}
