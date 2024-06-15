package com.mascara.oyo_booking_backend.mapper;

import com.mascara.oyo_booking_backend.dtos.accom_place.response.*;
import com.mascara.oyo_booking_backend.dtos.facility.response.GetFacilityResponse;
import com.mascara.oyo_booking_backend.dtos.facility_category.response.GetFacilityCategorWithFacilityListResponse;
import com.mascara.oyo_booking_backend.dtos.surcharge.surcharge_accom.response.GetSurchargeOfAccomResponse;
import com.mascara.oyo_booking_backend.dtos.type_bed.response.GetTypeBedResponse;
import com.mascara.oyo_booking_backend.entities.accommodation.*;
import com.mascara.oyo_booking_backend.entities.address.District;
import com.mascara.oyo_booking_backend.entities.address.Province;
import com.mascara.oyo_booking_backend.entities.authentication.User;
import com.mascara.oyo_booking_backend.entities.booking.Booking;
import com.mascara.oyo_booking_backend.entities.facility.Facility;
import com.mascara.oyo_booking_backend.entities.facility.FacilityCategories;
import com.mascara.oyo_booking_backend.entities.surcharge.SurchargeCategory;
import com.mascara.oyo_booking_backend.entities.type_bed.TypeBed;
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
import java.util.LinkedList;
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

    @Autowired
    private PriceCustomRepository priceCustomRepository;

    //    Covert list price custom
    private final Converter<Long, List<PriceCustomForAccom>> idAccomPlaceToListPriceCustom = context -> {
        Long id = context.getSource();
        AccomPlace accomPlace = accomPlaceRepository.findById(id).get();

        List<PriceCustomForAccom> priceCustomForAccoms = new LinkedList<>();
        List<PriceCustom> priceCustoms = priceCustomRepository.findByAccomId(accomPlace.getId());

        for (PriceCustom priceCustom : priceCustoms) {
            priceCustomForAccoms.add(PriceCustomForAccom.builder()
                    .priceApply(priceCustom.getPriceApply())
                    .dateApply(priceCustom.getDateApply())
                    .build());
        }
        return id != null ? priceCustomForAccoms : null;
    };

    //    Covert list range date booking
    private final Converter<Long, List<RangeDateBooking>> idAccomPlaceToListRangeDateBooking = context -> {
        Long id = context.getSource();
        AccomPlace accomPlace = accomPlaceRepository.findById(id).get();

        List<RangeDateBooking> rangeDateBookings = new LinkedList<>();
        List<Booking> bookings = bookingRepository.findByAccomId(accomPlace.getId());

        for (Booking booking : bookings) {
            rangeDateBookings.add(RangeDateBooking.builder()
                    .nameCustomer(booking.getNameCustomer())
                    .dateStart(booking.getCheckIn())
                    .dateEnd(booking.getCheckOut())
                    .build());
        }
        return id != null ? rangeDateBookings : null;
    };

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

    private final Converter<Long, String> userIdToHostMail = context -> {
        Long userId = context.getSource();
        if (userId != null) {
            User host = userRepository.findByUserId(userId)
                    .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("User")));
            return host.getMail();
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

                .addMappings(mapper -> {
                    mapper.using(userIdToNameHost)
                            .map(AccomPlace::getUserId, GetAccomPlaceDetailResponse::setNameHost);
                    mapper.using(userIdToHostMail)
                            .map(AccomPlace::getUserId, GetAccomPlaceDetailResponse::setHostMail);
                })

                .addMappings(mapper -> mapper.using(idAccomPlaceToSurchargeList)
                        .map(AccomPlace::getId, GetAccomPlaceDetailResponse::setSurchargeList))

                .addMappings(mapper -> mapper.using(setBedRoomToNameTypeBed)
                        .map(AccomPlace::getBedRoomSet, GetAccomPlaceDetailResponse::setBedRooms))

                .addMappings(mapper -> mapper.using(idAccomPlaceToListOfBookedDates)
                        .map(AccomPlace::getId, GetAccomPlaceDetailResponse::setBookedDates));

        mapper.createTypeMap(AccomPlace.class, GetPriceCustomAccomResponse.class)
                .addMappings(mapper -> {
                    mapper.using(idAccomPlaceToListPriceCustom)
                            .map(AccomPlace::getId, GetPriceCustomAccomResponse::setPriceCustomForAccomList);
                    mapper.map(AccomPlace::getId, GetPriceCustomAccomResponse::setAccomId);
                });

        mapper.createTypeMap(AccomPlace.class, GetRangeDateBookingAccomResponse.class)
                .addMappings(mapper -> {
                    mapper.using(idAccomPlaceToListRangeDateBooking)
                            .map(AccomPlace::getId, GetRangeDateBookingAccomResponse::setRangeDateBookingList);
                    mapper.map(AccomPlace::getId, GetRangeDateBookingAccomResponse::setAccomId);
                });
    }

    public GetAccomPlaceResponse toGetAccomPlaceResponse(AccomPlace accomPlace) {
        GetAccomPlaceResponse accomPlaceResponse;
        accomPlaceResponse = mapper.map(accomPlace, GetAccomPlaceResponse.class);
        return accomPlaceResponse;
    }

    public GetPriceCustomAccomResponse toGetPriceCustomAccom(AccomPlace accomPlace) {
        GetPriceCustomAccomResponse accomPlaceResponse;
        accomPlaceResponse = mapper.map(accomPlace, GetPriceCustomAccomResponse.class);
        return accomPlaceResponse;
    }

    public GetRangeDateBookingAccomResponse toGetRangeDateBookingAccomResponse(AccomPlace accomPlace) {
        GetRangeDateBookingAccomResponse accomPlaceResponse;
        accomPlaceResponse = mapper.map(accomPlace, GetRangeDateBookingAccomResponse.class);
        return accomPlaceResponse;
    }

    public GetAccomPlaceDetailResponse toGetAccomPlaceDetailResponse(AccomPlace accomPlace) {
        GetAccomPlaceDetailResponse accomPlaceDetailResponse;
        accomPlaceDetailResponse = mapper.map(accomPlace, GetAccomPlaceDetailResponse.class);
        return accomPlaceDetailResponse;
    }
}
