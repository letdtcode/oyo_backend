package com.mascara.oyo_booking_backend.config.init_data.service;

import com.mascara.oyo_booking_backend.config.init_data.models.InitAccomPlaceModel;
import com.mascara.oyo_booking_backend.config.init_data.models.PaymentInfoModel;
import com.mascara.oyo_booking_backend.common.constant.MessageConstant;
import com.mascara.oyo_booking_backend.entities.accommodation.*;
import com.mascara.oyo_booking_backend.entities.address.District;
import com.mascara.oyo_booking_backend.entities.address.Province;
import com.mascara.oyo_booking_backend.entities.address.Ward;
import com.mascara.oyo_booking_backend.entities.authentication.User;
import com.mascara.oyo_booking_backend.entities.bank.Bank;
import com.mascara.oyo_booking_backend.entities.facility.Facility;
import com.mascara.oyo_booking_backend.entities.surcharge.SurchargeCategory;
import com.mascara.oyo_booking_backend.entities.type_bed.TypeBed;
import com.mascara.oyo_booking_backend.common.enums.homestay.AccomStatusEnum;
import com.mascara.oyo_booking_backend.common.enums.homestay.CancellationPolicyEnum;
import com.mascara.oyo_booking_backend.common.exceptions.ResourceNotFoundException;
import com.mascara.oyo_booking_backend.repositories.*;
import com.mascara.oyo_booking_backend.utils.SlugsUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 02/04/2024
 * Time      : 3:44 CH
 * Filename  : InitAccomPlaceService
 */
@Component
@RequiredArgsConstructor
public class InitAccomPlaceService {

    private final AccomPlaceRepository accomPlaceRepository;


    private final ProvinceRepository provinceRepository;


    private final DistrictRepository districtRepository;


    private final WardRepository wardRepository;


    private final UserRepository userRepository;


    private final AccommodationCategoriesRepository accommodationCategoriesRepository;


    private final FacilityRepository facilityRepository;


    private final BedRoomRepository bedRoomRepository;


    private final TypeBedRepository typeBedRepository;


    private final SurchargeOfAccomRepository surchargeOfAccomRepository;


    private final SurchargeCategoryRepository surchargeCategoryRepository;


    private final GeneralPolicyDetailRepository generalPolicyDetailRepository;


    private final PaymentInfoDetailRepository paymentInfoDetailRepository;

    private final BankRepository bankRepository;

    @Transactional
    public String addAccomPlace(InitAccomPlaceModel request, String mailPartner) {
        Province province = provinceRepository.findByProvinceCode(request.getProvinceCode())
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstant.NOT_FOUND_MESSAGE("province")));

        District district = districtRepository.findByDistrictCode(request.getDistrictCode())
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstant.NOT_FOUND_MESSAGE("district")));

        Ward ward = wardRepository.findByWardCode(request.getWardCode())
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstant.NOT_FOUND_MESSAGE("ward")));

        User user = userRepository.findByMail(mailPartner).get();

        AccommodationCategories accomCategories = accommodationCategoriesRepository.findByAccomCateName(request.getAccomCateName())
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstant.NOT_FOUND_MESSAGE("accommodation category")));

        Set<Facility> facilitySet = new HashSet<>();
        for (String facilityName : request.getFacilityNameList()) {
            facilitySet.add(facilityRepository.findByFacilityName(facilityName)
                    .orElseThrow(() -> new ResourceNotFoundException(MessageConstant.NOT_FOUND_MESSAGE("facility"))));
        }

        String addressDetail = request.getNumHouseAndStreetName() + ", " + ward.getWardName() + ", " + district.getDistrictName() + ", " + province.getProvinceName();
        String slugs = SlugsUtils.toSlug(request.getAccomName());
        AccomPlace accomPlace = AccomPlace.builder()
                .accomName(request.getAccomName())
                .description(request.getDescription())
                .addressDetail(addressDetail)
                .longitude(request.getLongitude())
                .latitude(request.getLatitude())
                .slugs(slugs)
                .accommodationCategories(accomCategories)
                .accomCateId(accomCategories.getId())
                .province(province)
                .provinceCode(request.getProvinceCode())
                .districtCode(district.getDistrictCode())
                .wardCode(ward.getWardCode())
                .cancellationPolicy(CancellationPolicyEnum.CANCEL_24H)
                .cancellationFeeRate(10)
                .acreage(request.getAcreage())
                .checkInFrom(request.getCheckInFrom())
                .checkOutTo(request.getCheckOutTo())
                .numPeople(request.getNumPeople())
                .numBathRoom(request.getNumBathRoom())
                .numBedRoom(request.getNumBedRoom())
                .numKitchen(request.getNumKitchen())
                .numView(request.getNumView())
                .pricePerNight(request.getPricePerNight())
                .facilitySet(facilitySet)
                .user(user)
                .userId(user.getId())
                .status(AccomStatusEnum.APPROVED).build();

        String guide = request.getGuide();
        String cldVideoId = request.getCldVideoId();
        if (guide != null && !guide.isBlank() && !guide.isEmpty())
            accomPlace.setGuide(guide);

        if (cldVideoId != null && !cldVideoId.isBlank()) {
            accomPlace.setCldVideoId(cldVideoId);
        }
        accomPlace = accomPlaceRepository.save(accomPlace);

//        Create type bed for number of bed room
        int numRoom = request.getNumBedRoom();
        TypeBed typeBedDefault = typeBedRepository.findByTypeBedCode("TYPE_BED_003")
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstant.NOT_FOUND_MESSAGE("Type bed")));
        List<BedRoom> bedRooms = new ArrayList<>();
        for (int i = 0; i < numRoom; i++) {
            BedRoom bedRoom = BedRoom.builder()
                    .accomPlace(accomPlace)
                    .accomId(accomPlace.getId())
                    .typeBed(typeBedDefault)
                    .typeBedCode(typeBedDefault.getTypeBedCode())
                    .build();
            bedRooms.add(bedRoom);
        }
        bedRoomRepository.saveAll(bedRooms);

//        Init surcharge sample for 20 init data accom place
        List<SurchargeCategory> surchargeCategoryList = surchargeCategoryRepository.findAll();
        for (SurchargeCategory surchargeCategory : surchargeCategoryList) {
            SurchargeOfAccom surcharge = SurchargeOfAccom.builder()
                    .cost(20000D)
                    .accomPlace(accomPlace)
                    .accomPlaceId(accomPlace.getId())
                    .surchargeCategory(surchargeCategory)
                    .surchargeCateId(surchargeCategory.getId())
                    .build();
            surchargeOfAccomRepository.save(surcharge);
        }

//        Create general policy detail for accom place
        GeneralPolicyDetail generalPolicyDetail = GeneralPolicyDetail.builder()
//                .Id(accomPlace.getId())
                .allowEvent(request.getGeneralPolicy().getAllowEvent())
                .allowPet(request.getGeneralPolicy().getAllowPet())
                .allowSmoking(request.getGeneralPolicy().getAllowSmoking())
                .accomPlace(accomPlace)
                .build();
        generalPolicyDetailRepository.save(generalPolicyDetail);

        PaymentInfoModel paymentInfoModel = request.getPaymentInfo();
        Bank bank = bankRepository.findById(paymentInfoModel.getBankId()).get();
//        Create payment info detail for accom place
        PaymentInfoDetail paymentInfoDetail = PaymentInfoDetail.builder()
                .accomPlace(accomPlace)
                .bank(bank)
                .bankId(bank.getId())
                .accountNumber(paymentInfoModel.getAccountNumber())
                .accountNameHost(paymentInfoModel.getAccountNameHost())
                .swiftCode(paymentInfoModel.getSwiftCode())
                .build();
        paymentInfoDetailRepository.save(paymentInfoDetail);
        return "Success";
    }
}
