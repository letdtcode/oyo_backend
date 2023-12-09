package com.mascara.oyo_booking_backend.mapper;

import com.mascara.oyo_booking_backend.dtos.response.accommodation.GetAccomPlaceDetailResponse;
import com.mascara.oyo_booking_backend.dtos.response.accommodation.GetAccomPlaceResponse;
import com.mascara.oyo_booking_backend.dtos.response.facility.GetFacilityResponse;
import com.mascara.oyo_booking_backend.dtos.response.facility_category.GetFacilityCategorWithFacilityListResponse;
import com.mascara.oyo_booking_backend.dtos.response.surcharge.GetSurchargeOfAccomResponse;
import com.mascara.oyo_booking_backend.dtos.response.type_bed.GetTypeBedResponse;
import com.mascara.oyo_booking_backend.entities.*;
import com.mascara.oyo_booking_backend.exceptions.ResourceNotFoundException;
import com.mascara.oyo_booking_backend.repositories.*;
import com.mascara.oyo_booking_backend.utils.AppContants;
import jakarta.annotation.PostConstruct;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 27/11/2023
 * Time      : 5:53 CH
 * Filename  : AccomPlaceMapper
 */
@Component
public class AccomPlaceMapper {
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private AccomPlaceRepository accomPlaceRepository;

    @Autowired
    private ProvinceRepository provinceRepository;

    @Autowired
    private DistrictRepository districtRepository;

    @Autowired
    private FacilityCategoriesRepository facilityCategoriesRepository;

    @Autowired
    private TypeBedRepository typeBedRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SurchargeCategoryRepository surchargeCategoryRepository;

    @Autowired
    private SurchargeOfAccomRepository surchargeOfAccomRepository;

    @Autowired
    private BookingRepository bookingRepository;

    //    Covert Address General
    private final Converter<Long, String> idAccomPlaceToAddressGeneral = context -> {
        Long id = context.getSource();
        AccomPlace accomPlace = accomPlaceRepository.findById(id).get();
        Province province = provinceRepository.findByProvinceCode(accomPlace.getProvinceCode())
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("province")));
        District district = districtRepository.findByDistrictCode(accomPlace.getDistrictCode())
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("district")));
        String addressGeneral = district.getDistrictName() + ", " + province.getProvinceName();
        return id != null ? addressGeneral : null;
    };

    //    Covert Address General
    private final Converter<Long, List<LocalDate>> idAccomPlaceToListOfBookedDates = context -> {
        Long accomId = context.getSource();
        if (accomId != null) {
            LocalDate dateNow = LocalDate.now();
            List<Booking> bookingOfRangeDate = bookingRepository.findBookingByRangeDateStartFromCurrent(accomId, dateNow);
            List<LocalDate> datesBooked = new ArrayList<>();
            if (!bookingOfRangeDate.isEmpty() && bookingOfRangeDate != null) {
                for (Booking booking : bookingOfRangeDate) {
                    LocalDate currentDate = booking.getCheckIn();
                    while (!currentDate.isAfter(booking.getCheckOut()) && !datesBooked.contains(currentDate)) {
                        datesBooked.add(currentDate);
                        currentDate = currentDate.plusDays(1);
                    }
                }
            }
            return datesBooked;
        }
        return null;
    };

    private final Converter<Long, List<GetSurchargeOfAccomResponse>> idAccomPlaceToSurchargeList = context -> {
        Long accomId = context.getSource();
        if (accomId != null) {
            List<GetSurchargeOfAccomResponse> surchargeList = new ArrayList<>();
            List<SurchargeOfAccom> surchargeOfAccomList = surchargeOfAccomRepository.findByAccomPlaceId(accomId);
            if (surchargeOfAccomList != null && !surchargeOfAccomList.isEmpty()) {
                for (SurchargeOfAccom surcharge : surchargeOfAccomList) {
                    SurchargeCategory surchargeCate = surchargeCategoryRepository.findSurchargeCategoryById(surcharge.getSurchargeCateId()).get();
                    surchargeList.add(new GetSurchargeOfAccomResponse(surcharge.getCost(),
                            surchargeCate.getSurchargeCateName(),
                            surchargeCate.getSurchargeCode()));
                }
            }
            return surchargeList;
        }
        return null;
    };


    //    Covert Image Accom
    private final Converter<Set<ImageAccom>, List<String>> imageAccomToImageAccomUrl = context -> {
        Set<ImageAccom> imageAccomSet = context.getSource();
        if (imageAccomSet != null) {
            List<String> imageAccomUrl = new ArrayList();
            for (ImageAccom imgAccom : imageAccomSet) {
                imageAccomUrl.add(imgAccom.getImgAccomLink());
            }
            return imageAccomUrl;
        }
        return null;
    };

    //    Convert accom category
    private final Converter<AccommodationCategories, String> accomCategoryToAccomCategoryName = context -> {
        AccommodationCategories accommodationCategories = context.getSource();
        return accommodationCategories != null ? accommodationCategories.getAccomCateName() : null;
    };

    //    Convert user id to host name
    private final Converter<Long, String> userIdToNameHost = context -> {
        Long userId = context.getSource();
        if (userId != null) {
            User host = userRepository.findByUserId(userId)
                    .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("User")));
            return host.getFirstName() + " " + host.getLastName();
        }
        return null;
    };

    //    Convert Set Facility
    private final Converter<Set<Facility>, List<GetFacilityCategorWithFacilityListResponse>> setFacilityToFacilityCateDetails = context -> {
        Set<Facility> facilitySet = context.getSource();
        if (facilitySet != null) {
            List<GetFacilityResponse> facilityList = new ArrayList<>();
            List<GetFacilityCategorWithFacilityListResponse> facilityCategoryList = new ArrayList();
            for (Facility facility : facilitySet) {
                GetFacilityResponse facilityResponse = mapper.map(facility, GetFacilityResponse.class);
                facilityResponse.setStatus(facility.getStatus().toString());
                facilityList.add(facilityResponse);
            }
            for (GetFacilityResponse facilityResponse : facilityList) {
                boolean addCate = true;
                for (GetFacilityCategorWithFacilityListResponse facilityCategoryResponse : facilityCategoryList) {
                    if (facilityResponse.getFacilityCateCode().equals(facilityCategoryResponse.getFaciCateCode())) {
                        addCate = false;
                        break;
                    }
                }
                if (addCate == true) {
                    FacilityCategories facilityCategories = facilityCategoriesRepository.findByFaciCateCode(facilityResponse
                            .getFacilityCateCode()).get();
                    GetFacilityCategorWithFacilityListResponse faciCateResponse = mapper.map(facilityCategories, GetFacilityCategorWithFacilityListResponse.class);
                    faciCateResponse.setStatus(facilityCategories.getStatus().toString());
                    facilityCategoryList.add(faciCateResponse);
                }
            }

            for (GetFacilityCategorWithFacilityListResponse faciCate : facilityCategoryList) {
                List<GetFacilityResponse> facilityResponseList = new ArrayList<>();
                for (GetFacilityResponse facility : facilityList) {
                    if (faciCate.getFaciCateCode().equals(facility.getFacilityCateCode()))
                        facilityResponseList.add(facility);
                }
                faciCate.setInfoFacilityList(facilityResponseList);
            }
            return facilityCategoryList;
        }
        return null;
    };

    //    Covert bed rooms
    private final Converter<Set<BedRoom>, List<GetTypeBedResponse>> setBedRoomToNameTypeBed = context -> {
        Set<BedRoom> bedRoomSet = context.getSource();
        if (bedRoomSet != null) {
            List<GetTypeBedResponse> bedNameList = new ArrayList<>();
            for (BedRoom bedRoom : bedRoomSet) {
                String typeBedCode = bedRoom.getTypeBedCode();
                TypeBed typeBed = typeBedRepository.findByTypeBedCode(typeBedCode).get();
                GetTypeBedResponse bedName = mapper.map(typeBed, GetTypeBedResponse.class);
                bedNameList.add(bedName);
            }
            return bedNameList;
        }
        return null;
    };

    @PostConstruct
    public void init() {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        mapper.createTypeMap(AccomPlace.class, GetAccomPlaceResponse.class)
                .addMappings(mapper -> mapper.using(idAccomPlaceToAddressGeneral)
                        .map(AccomPlace::getId, GetAccomPlaceResponse::setAddressGeneral))

                .addMappings(mapper -> mapper.using(imageAccomToImageAccomUrl)
                        .map(AccomPlace::getImageAccoms, GetAccomPlaceResponse::setImageAccomsUrls))

                .addMappings(mapper -> mapper.using(accomCategoryToAccomCategoryName)
                        .map(AccomPlace::getAccommodationCategories, GetAccomPlaceResponse::setAccomCateName))

                .addMappings(mapper -> mapper.using(userIdToNameHost)
                        .map(AccomPlace::getUserId, GetAccomPlaceResponse::setNameHost));

        mapper.createTypeMap(AccomPlace.class, GetAccomPlaceDetailResponse.class)
                .addMappings(mapper -> mapper.using(idAccomPlaceToAddressGeneral)
                        .map(AccomPlace::getId, GetAccomPlaceDetailResponse::setAddressGeneral))

                .addMappings(mapper -> mapper.using(imageAccomToImageAccomUrl)
                        .map(AccomPlace::getImageAccoms, GetAccomPlaceDetailResponse::setImageAccomsUrls))

                .addMappings(mapper -> mapper.using(setFacilityToFacilityCateDetails)
                        .map(AccomPlace::getFacilitySet, GetAccomPlaceDetailResponse::setFacilityCategoryList))

                .addMappings(mapper -> mapper.using(accomCategoryToAccomCategoryName)
                        .map(AccomPlace::getAccommodationCategories, GetAccomPlaceDetailResponse::setAccomCateName))

                .addMappings(mapper -> mapper.using(userIdToNameHost)
                        .map(AccomPlace::getUserId, GetAccomPlaceDetailResponse::setNameHost))

                .addMappings(mapper -> mapper.using(idAccomPlaceToSurchargeList)
                        .map(AccomPlace::getId, GetAccomPlaceDetailResponse::setSurchargeList))

                .addMappings(mapper -> mapper.using(setBedRoomToNameTypeBed)
                        .map(AccomPlace::getBedRoomSet, GetAccomPlaceDetailResponse::setBedRooms))

                .addMappings(mapper -> mapper.using(idAccomPlaceToListOfBookedDates)
                        .map(AccomPlace::getId, GetAccomPlaceDetailResponse::setBookedDates));
    }

    public GetAccomPlaceResponse toGetAccomPlaceResponse(AccomPlace accomPlace) {
        GetAccomPlaceResponse accomPlaceResponse;
        accomPlaceResponse = mapper.map(accomPlace, GetAccomPlaceResponse.class);
        return accomPlaceResponse;
    }

    public GetAccomPlaceDetailResponse toGetAccomPlaceDetailResponse(AccomPlace accomPlace) {
        GetAccomPlaceDetailResponse accomPlaceDetailResponse;
        accomPlaceDetailResponse = mapper.map(accomPlace, GetAccomPlaceDetailResponse.class);
        return accomPlaceDetailResponse;
    }
}
