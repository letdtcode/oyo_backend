package com.mascara.oyo_booking_backend.services.accom_place;

import com.mascara.oyo_booking_backend.dtos.accom_place.request.*;
import com.mascara.oyo_booking_backend.dtos.accom_place.response.*;
import com.mascara.oyo_booking_backend.dtos.base.BaseMessageData;
import com.mascara.oyo_booking_backend.dtos.base.BasePagingData;
import com.mascara.oyo_booking_backend.dtos.facility.response.FacilityResponse;
import com.mascara.oyo_booking_backend.entities.accommodation.*;
import com.mascara.oyo_booking_backend.entities.address.District;
import com.mascara.oyo_booking_backend.entities.address.Province;
import com.mascara.oyo_booking_backend.entities.address.Ward;
import com.mascara.oyo_booking_backend.entities.authentication.User;
import com.mascara.oyo_booking_backend.entities.bank.Bank;
import com.mascara.oyo_booking_backend.entities.booking.Booking;
import com.mascara.oyo_booking_backend.entities.facility.Facility;
import com.mascara.oyo_booking_backend.entities.surcharge.SurchargeCategory;
import com.mascara.oyo_booking_backend.entities.type_bed.TypeBed;
import com.mascara.oyo_booking_backend.enums.AccomStatusEnum;
import com.mascara.oyo_booking_backend.enums.CancellationPolicyEnum;
import com.mascara.oyo_booking_backend.exceptions.ForbiddenException;
import com.mascara.oyo_booking_backend.exceptions.NotCredentialException;
import com.mascara.oyo_booking_backend.exceptions.ResourceNotFoundException;
import com.mascara.oyo_booking_backend.mapper.accommodation.AccomPlaceMapper;
import com.mascara.oyo_booking_backend.mapper.facility.FacilityMapper;
import com.mascara.oyo_booking_backend.mapper.type_bed.TypeBedMapper;
import com.mascara.oyo_booking_backend.repositories.*;
import com.mascara.oyo_booking_backend.utils.AppContants;
import com.mascara.oyo_booking_backend.utils.Utilities;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
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
@RequiredArgsConstructor
public class AccomPlaceServiceImpl implements AccomPlaceService {
    private final AccomPlaceRepository accomPlaceRepository;

    private final AccommodationCategoriesRepository accommodationCategoriesRepository;

    private final ProvinceRepository provinceRepository;

    private final DistrictRepository districtRepository;

    private final WardRepository wardRepository;

    private final UserRepository userRepository;

    private final FacilityRepository facilityRepository;

    private final ImageAccomRepository imageAccomRepository;

    private final TypeBedRepository typeBedRepository;

    private final BedRoomRepository bedRoomRepository;

    private final SurchargeOfAccomRepository surchargeOfAccomRepository;

    private final SurchargeCategoryRepository surchargeCategoryRepository;

    private final BookingRepository bookingRepository;

    private final GeneralPolicyDetailRepository generalPolicyDetailRepository;

    private final PaymentInfoDetailRepository paymentInfoDetailRepository;

    private final BankRepository bankRepository;

    private final PriceCustomRepository priceCustomRepository;

    private final AccomPlaceMapper accomPlaceMapper;

    private final TypeBedMapper typeBedMapper;

    private final FacilityMapper facilityMapper;

    @Override
    @Transactional
    public Long registerAccomPlace(String accomCateName, String mailPartner) {
        User user = userRepository.findByMail(mailPartner)
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("User")));
        AccommodationCategories accomCategories = accommodationCategoriesRepository.findByAccomCateName(accomCateName)
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("accommodation category")));

        AccomPlace accomPlace = AccomPlace.builder()
                .user(user)
                .userId(user.getId())
                .accommodationCategories(accomCategories)
                .accomCateId(accomCategories.getId())
                .status(AccomStatusEnum.WAITING_FOR_COMPLETE)
                .build();
        accomPlace = accomPlaceRepository.save(accomPlace);

        GeneralPolicyDetail generalPolicyDetail = GeneralPolicyDetail.builder()
                .accomPlace(accomPlace)
                .build();
        generalPolicyDetailRepository.save(generalPolicyDetail);

        PaymentInfoDetail paymentInfoDetail = PaymentInfoDetail.builder()
                .accomPlace(accomPlace)
                .build();
        paymentInfoDetailRepository.save(paymentInfoDetail);
        return accomPlace.getId();
    }

    @Override
    @Transactional
    public BaseMessageData requestApproval(Long accomId, String mailPartner) {
        AccomPlace place = accomPlaceRepository.findById(accomId)
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("accom place")));
        User host = userRepository.findByMail(mailPartner)
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("User")));

        if (place.getUserId() != host.getId()) {
            throw new ForbiddenException("Forbidden");
        }

        int percentComplete = getPercentCreateAccom(accomId).getPercent();
        if (place.getStatus().equals(AccomStatusEnum.WAITING_FOR_COMPLETE) && percentComplete == 100) {
            place.setStatus(AccomStatusEnum.WAITING_FOR_APPROVAL);
            accomPlaceRepository.save(place);
            return new BaseMessageData(AppContants.UPDATE_SUCCESS_MESSAGE("Accom place"));
        }
        throw new ForbiddenException("Forbidden");
    }

    @Override
    @Transactional
    public BaseMessageData updateGeneralInfo(UpdateGeneralInfoRequest request, Long accomId) {
        AccommodationCategories accomCategories = accommodationCategoriesRepository.findByAccomCateName(request.getAccomCateName())
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("accommodation category")));

        AccomPlace accomPlace = accomPlaceRepository.findById(accomId)
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("Accom place")));
        accomPlace.setAccommodationCategories(accomCategories);
        accomPlace.setAccomCateId(accomCategories.getId());
        accomPlace.setAccomName(request.getNameAccom());
        accomPlace.setDescription(request.getDescription());
        accomPlace.setGuide(request.getGuide());
        accomPlace.setAcreage(request.getAcreage());
        accomPlace.setPricePerNight(request.getPricePerNight());
        accomPlace.setCheckInFrom(request.getCheckInFrom());
        accomPlace.setCheckOutTo(request.getCheckOutTo());
        accomPlace.setDiscount(request.getDiscountPercent());
        accomPlaceRepository.save(accomPlace);

//        Set surcharge list for accom
        surchargeOfAccomRepository.deleteByAccomId(accomPlace.getId());
        Set<SurchargeOfAccom> surchargeOfAccomSet = new HashSet<>();
        for (ItemSurcharge itemSurcharge : request.getSurchargeList()) {
            String surchargeCode = itemSurcharge.getSurchargeCode();
            Double cost = itemSurcharge.getCost();
            SurchargeCategory surchargeCategory = surchargeCategoryRepository.findSurchargeCategoryByCode(surchargeCode)
                    .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("Surcharge code")));
            SurchargeOfAccom surcharge = SurchargeOfAccom.builder()
                    .accomPlace(accomPlace)
                    .accomPlaceId(accomPlace.getId())
                    .surchargeCategory(surchargeCategory)
                    .surchargeCateId(surchargeCategory.getId())
                    .cost(cost).build();
            surchargeOfAccomRepository.save(surcharge);
            surchargeOfAccomSet.add(surcharge);
        }
        return new BaseMessageData(AppContants.UPDATE_SUCCESS_MESSAGE("Accom place"));
    }

    @Override
    @Transactional
    public BaseMessageData updateAddress(UpdateAddressAccomRequest request, Long accomId) {
        AccomPlace accomPlace = accomPlaceRepository.findById(accomId)
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("Accom place")));

        Province province = provinceRepository.findByProvinceCode(request.getProvinceCode())
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("province")));
        boolean districtExist = districtRepository.existsByDistrictCode(request.getDistrictCode());
        boolean wardExist = wardRepository.existsByWardCode(request.getWardCode());

        District district = districtRepository.findByDistrictCode(request.getDistrictCode()).get();
        Ward ward = wardRepository.findByWardCode(request.getWardCode()).get();

        if (!districtExist || !wardExist) {
            throw new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("district or ward"));
        }
        String addressDetail = request.getNumHouseAndStreetName() + ", " + ward.getWardName() + ", " + district.getDistrictName() + ", " + province.getProvinceName();
        accomPlace.setDistrictCode(request.getDistrictCode());
        accomPlace.setWardCode(request.getWardCode());
        accomPlace.setProvince(province);
        accomPlace.setProvinceCode(request.getProvinceCode());
        accomPlace.setAddressDetail(addressDetail);
        accomPlace.setLongitude(request.getLongitude());
        accomPlace.setLatitude(request.getLatitude());
        accomPlace.setGuide(request.getGuide());
        accomPlaceRepository.save(accomPlace);
        return new BaseMessageData(AppContants.UPDATE_SUCCESS_MESSAGE("Accom place"));
    }

    @Override
    @Transactional
    public BaseMessageData updateFacilities(UpdateFacilityAccomRequest request, Long accomId) {
        AccomPlace accomPlace = accomPlaceRepository.findById(accomId)
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("Accom place")));
        Set<Facility> facilitySet = new HashSet<>();
        for (String facilityCode : request.getFacilityCodes()) {
            Facility facility = facilityRepository.findByFacilityCode(facilityCode).get();
            facilitySet.add(facility);
        }
        accomPlace.setFacilitySet(facilitySet);
        accomPlaceRepository.save(accomPlace);
        return new BaseMessageData(AppContants.UPDATE_SUCCESS_MESSAGE("Accom place"));
    }

    @Override
    @Transactional
    public BaseMessageData updateGallery(UpdateGalleryAccomRequest request, Long accomId) {
        AccomPlace accomPlace = accomPlaceRepository.findById(accomId)
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("Accom place")));

        imageAccomRepository.deleteByAccomId(accomPlace.getId());
        Set<ImageAccom> imageAccomSet = new HashSet<>();
        for (String imageUrl : request.getImageAccomUrls()) {
            ImageAccom imageAccom = ImageAccom.builder()
                    .imgAccomLink(imageUrl)
                    .accomPlace(accomPlace)
                    .accomPlaceId(accomPlace.getId()).build();
            imageAccomRepository.save(imageAccom);
            imageAccomSet.add(imageAccom);
        }
        accomPlace.setImageAccoms(imageAccomSet);
        accomPlaceRepository.save(accomPlace);
        return new BaseMessageData(AppContants.UPDATE_SUCCESS_MESSAGE("Accom place"));
    }

    @Override
    @Transactional
    public BaseMessageData updateRooms(UpdateRoomAccomRequest request, Long accomId) {
        AccomPlace accomPlace = accomPlaceRepository.findById(accomId)
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("Accom place")));
        AccommodationCategories accomCate = accommodationCategoriesRepository.findByAccomCateName(request.getAccomCateName())
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("Accom category")));

        accomPlace.setNumBathRoom(request.getNumBathRoom());
        accomPlace.setNumKitchen(request.getNumKitchen());
        accomPlace.setNumPeople(request.getNumPeople());
        accomPlace.setAccommodationCategories(accomCate);
        accomPlace.setAccomCateId(accomCate.getId());
        accomPlace.setNumBedRoom(request.getTypeBedCodes().size());

        bedRoomRepository.deleteByAccomId(accomPlace.getId());
        Set<BedRoom> bedRoomSet = new HashSet<>();
        for (String bedCode : request.getTypeBedCodes()) {
            TypeBed typeBed = typeBedRepository.findByTypeBedCode(bedCode).get();
            BedRoom bedRoom = BedRoom.builder()
                    .accomPlace(accomPlace)
                    .accomId(accomPlace.getId())
                    .typeBed(typeBed)
                    .typeBedCode(typeBed.getTypeBedCode())
                    .build();
            bedRoomRepository.save(bedRoom);
            bedRoomSet.add(bedRoom);
        }
        accomPlace.setBedRoomSet(bedRoomSet);
        accomPlaceRepository.save(accomPlace);
        return new BaseMessageData(AppContants.UPDATE_SUCCESS_MESSAGE("Accom place"));
    }

    @Override
    @Transactional
    public BaseMessageData updatePolicy(UpdatePolicyAccomRequest request, Long accomId) {
        AccomPlace accomPlace = accomPlaceRepository.findById(accomId)
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("Accom place")));
        GeneralPolicyDetail generalPolicyDetail = generalPolicyDetailRepository.findById(accomId).get();

        accomPlace.setCancellationPolicy(CancellationPolicyEnum.valueOf(request.getCancellationPolicy()));
        accomPlace.setCancellationFeeRate(request.getCancellationFeeRate());
        accomPlaceRepository.save(accomPlace);

        generalPolicyDetail.setAllowEvent(request.getAllowEvent());
        generalPolicyDetail.setAllowPet(request.getAllowPet());
        generalPolicyDetail.setAllowSmoking(request.getAllowSmoking());
        generalPolicyDetailRepository.save(generalPolicyDetail);
        return new BaseMessageData(AppContants.UPDATE_SUCCESS_MESSAGE("Accom place"));
    }

    @Override
    @Transactional
    public BaseMessageData updatePayment(UpdatePaymentAccomRequest request, Long accomId) {
        accomPlaceRepository.findById(accomId)
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("Accom place")));
        Bank bank = bankRepository.findById(request.getBankId())
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("Bank")));
        PaymentInfoDetail paymentInfoDetail = paymentInfoDetailRepository.findById(accomId).get();
        paymentInfoDetail.setAccountNumber(request.getAccountNumber());
        paymentInfoDetail.setAccountNameHost(request.getAccountNameHost());
        paymentInfoDetail.setSwiftCode(request.getSwiftCode());
        paymentInfoDetail.setBank(bank);
        paymentInfoDetail.setBankId(bank.getId());
        paymentInfoDetailRepository.save(paymentInfoDetail);
        return new BaseMessageData(AppContants.UPDATE_SUCCESS_MESSAGE("Accom place"));
    }

    @Override
    public BaseMessageData updatePriceCustom(List<UpdatePriceCustomAccomPlaceRequest> updatePriceCustomAccomPlaceRequests) {
        Long accomId = null;
        LocalDate dateApply = null;
        Double priceApply = null;

        for (UpdatePriceCustomAccomPlaceRequest request : updatePriceCustomAccomPlaceRequests) {
            accomId = request.getAccomId();
            dateApply = request.getDateApply();
            priceApply = request.getPriceApply();
            AccomPlace accomPlace = accomPlaceRepository.findById(accomId)
                    .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("Accom place")));
            Optional<PriceCustom> priceCustomOptional = priceCustomRepository.findByAccomIdAndDateApply(accomId, dateApply);

            if (priceCustomOptional.isPresent()) {
                PriceCustom priceCustom = priceCustomOptional.get();
                priceCustom.setPriceApply(priceApply);
                priceCustomRepository.save(priceCustom);
            } else {
                PriceCustom priceCustomCreate = PriceCustom.builder()
                        .accomPlace(accomPlace)
                        .accomId(accomId)
                        .dateApply(dateApply)
                        .priceApply(priceApply)
                        .build();
                priceCustomRepository.save(priceCustomCreate);
            }
        }
        return new BaseMessageData(AppContants.UPDATE_SUCCESS_MESSAGE("Accom place"));
    }

    @Override
    public void checkPermission(String mailPartner, Long accomId) {
        AccomPlace accomPlace = accomPlaceRepository.findById(accomId)
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("Accom Place")));
        User hostAccom = userRepository.findByUserId(accomPlace.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("user")));
        Optional<User> partner = userRepository.findByMail(mailPartner);
        if (partner.isEmpty())
            throw new NotCredentialException("Unauthenticated");
        hostAccom = (User) Hibernate.unproxy(hostAccom);
        if (!hostAccom.getMail().equals(mailPartner))
            throw new ForbiddenException("Forbidden");
    }

    @Override
    public PercentCreateAccomResponse getPercentCreateAccom(Long accomId) {
        AccomPlace accomPlace = accomPlaceRepository.findById(accomId)
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("accom place")));
        GeneralPolicyDetail generalPolicyDetail = generalPolicyDetailRepository.findById(accomPlace.getId()).get();
        PaymentInfoDetail paymentInfoDetail = paymentInfoDetailRepository.findById(accomPlace.getId()).get();

        Float valuePercentPerStep = Float.valueOf(100 / 7);
        Float valuePercentAll = 0F;
        boolean isDoneGeneralInfo = false;
        boolean isDoneAddress = false;
        boolean isDoneFacility = false;
        boolean isDoneGallery = false;
        boolean isDoneRoomSetting = false;
        boolean isDonePolicy = false;
        boolean isDonePayment = false;


        String accomName = accomPlace.getAccomName();
        String addressDetail = accomPlace.getAddressDetail();
        Set<Facility> facilitiesOfAccom = accomPlace.getFacilitySet();
        Set<ImageAccom> imageAccoms = accomPlace.getImageAccoms();
        Integer numBedRoom = accomPlace.getNumBedRoom();

        if (accomName != null && !accomName.isBlank()) {
            isDoneGeneralInfo = true;
            valuePercentAll += valuePercentPerStep;
        }
        if (addressDetail != null && !addressDetail.isBlank()) {
            isDoneAddress = true;
            valuePercentAll += valuePercentPerStep;
        }
        if (!facilitiesOfAccom.isEmpty()) {
            isDoneFacility = true;
            valuePercentAll += valuePercentPerStep;
        }
        if (!imageAccoms.isEmpty()) {
            isDoneGallery = true;
            valuePercentAll += valuePercentPerStep;
        }
        if (numBedRoom != null && numBedRoom > 0) {
            isDoneRoomSetting = true;
            valuePercentAll += valuePercentPerStep;
        }
        if (generalPolicyDetail.getAllowPet() != null &&
                generalPolicyDetail.getAllowEvent() != null &&
                generalPolicyDetail.getAllowSmoking() != null) {
            isDonePolicy = true;
            valuePercentAll += valuePercentPerStep;
        }
        if (paymentInfoDetail.getAccountNameHost() != null &&
                paymentInfoDetail.getAccountNumber() != null &&
                paymentInfoDetail.getSwiftCode() != null) {
            isDonePayment = true;
            valuePercentAll += valuePercentPerStep;
        }
        PercentCreateAccomResponse percentCreateAccomResponse = PercentCreateAccomResponse.builder()
                .accomId(accomId)
                .isDoneGeneralInfo(isDoneGeneralInfo)
                .isDoneAddress(isDoneAddress)
                .isDoneFacility(isDoneFacility)
                .isDoneGallery(isDoneGallery)
                .isDoneRoomSetting(isDoneRoomSetting)
                .isDonePolicy(isDonePolicy)
                .isDonePayment(isDonePayment)
                .percent(Utilities.getInstance().roundNearestMultipleOf10(valuePercentAll))
                .build();
        return percentCreateAccomResponse;
    }

    @Override
    @Transactional
    public BasePagingData<GetAccomPlaceResponse> getAllAccomPlaceWithPaging(Integer pageNum, Integer pageSize, String sortType, String field) {
        Pageable paging = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.fromString(sortType), field));
        Page<AccomPlace> accomPlacePage = accomPlaceRepository.getAllWithPaging(paging, AccomStatusEnum.APPROVED);
        List<AccomPlace> accomPlaceList = accomPlacePage.stream().toList();
        List<GetAccomPlaceResponse> responseList = accomPlaceList.stream().map(accomPlace -> accomPlaceMapper.toGetAccomPlaceResponse(accomPlace))
                .collect(Collectors.toList());
        return new BasePagingData<>(responseList, accomPlacePage.getNumber(), accomPlacePage.getSize(), accomPlacePage.getTotalElements());
    }

    @Override
    @Transactional
    public BasePagingData<GetAccomPlaceResponse> getFilterByKeyWord(String keyword, Integer pageNum, Integer pageSize, String sortType, String field) {
        Pageable paging = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.fromString(sortType), field));
        Page<AccomPlace> accomPlacePage = accomPlaceRepository.getFilterByKeyWord(keyword, paging);
        List<AccomPlace> accomPlaceList = accomPlacePage.stream().toList();
        List<GetAccomPlaceResponse> responseList = accomPlaceList.stream().map(accomPlace -> accomPlaceMapper.toGetAccomPlaceResponse(accomPlace))
                .collect(Collectors.toList());
        return new BasePagingData<>(responseList, accomPlacePage.getNumber(), accomPlacePage.getSize(), accomPlacePage.getTotalElements());
    }

    @Override
    @Transactional
    public BasePagingData<GetAccomPlaceResponse> getAccomPlaceFilterWithPaging(GetAccomPlaceFilterRequest filter, Integer pageNum, Integer pageSize, String sortType, String field) {
        int length = 0;
        if (filter.getFacilityCode() != null) {
            length = filter.getFacilityCode().size();
        }
        if (filter.getFacilityCode() == null || filter.getFacilityCode().isEmpty()) {
            filter.setFacilityCode(List.of(UUID.randomUUID().toString()));
        }
        log.error(String.valueOf(length));
        Pageable paging = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.valueOf(sortType), field));
        Page<AccomPlace> accomPlacePage = accomPlaceRepository.getPageWithFullFilter(filter.getAccomCateName(), filter.getProvinceCode(), filter.getDistrictCode(), filter.getWardCode(), filter.getPriceFrom(), filter.getPriceTo(), filter.getFacilityCode(), length, filter.getNumBathroom(), filter.getNumPeople(), filter.getNumBedRoom(), paging);
        List<AccomPlace> accomPlaceList = accomPlacePage.stream().toList();
        List<GetAccomPlaceResponse> responseList = accomPlaceList.stream().map(accomPlace -> accomPlaceMapper.toGetAccomPlaceResponse(accomPlace))
                .collect(Collectors.toList());
        return new BasePagingData<>(responseList, accomPlacePage.getNumber(), accomPlacePage.getSize(), accomPlacePage.getTotalElements());
    }

    @Override
    @Transactional
    public GetAccomPlaceDetailResponse getAccomPlaceApprovedDetails(Long id) {
        AccomPlace accomPlace = accomPlaceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("accom place")));

        if (!accomPlace.getStatus().equals(AccomStatusEnum.APPROVED))
            throw new ForbiddenException("Forbidden");

        LocalDate dateNow = LocalDate.now();
        List<Booking> bookingOfRangeDate = bookingRepository.findBookingByRangeDateStartFromCurrent(accomPlace.getId(), dateNow);
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
        Long numView = accomPlace.getNumView();
        accomPlace.setNumView(numView + 1);
        accomPlace = accomPlaceRepository.save(accomPlace);
        return accomPlaceMapper.toGetAccomPlaceDetailResponse(accomPlace);
    }

    @Override
    @Transactional
    public GetAccomPlaceDetailResponse getAccomPlaceDetails(Long id) {
        AccomPlace accomPlace = accomPlaceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("accom place")));

        LocalDate dateNow = LocalDate.now();
        List<Booking> bookingOfRangeDate = bookingRepository.findBookingByRangeDateStartFromCurrent(accomPlace.getId(), dateNow);
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
        return accomPlaceMapper.toGetAccomPlaceDetailResponse(accomPlace);
    }

    @Override
    @Transactional
    public BasePagingData<GetAccomPlaceResponse> getTopAccomPlaceByField(Integer pageNum, Integer pageSize, String sortType, String field) {
        Pageable paging = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.valueOf(sortType), field));
        Page<AccomPlace> accomPlacePage = accomPlaceRepository.getAllWithPaging(paging, AccomStatusEnum.APPROVED);
        List<AccomPlace> accomPlaceList = accomPlacePage.stream().toList();
        List<GetAccomPlaceResponse> responseList = accomPlaceList.stream()
                .map(accomPlace -> accomPlaceMapper.toGetAccomPlaceResponse(accomPlace))
                .collect(Collectors.toList());
        return new BasePagingData<>(responseList,
                accomPlacePage.getNumber(),
                accomPlacePage.getSize(),
                accomPlacePage.getTotalElements());
    }

    @Override
    @Transactional
    public BasePagingData<GetAccomPlaceResponse> getListAccomPlaceApprovedOfPartner(String hostMail,
                                                                                    Integer pageNum,
                                                                                    Integer pageSize,
                                                                                    String sortType,
                                                                                    String field) {
        User user = userRepository.findByMail(hostMail).orElseThrow(() -> new ResourceNotFoundException("user"));
        Pageable paging = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.valueOf(sortType), field));
        Page<AccomPlace> accomPlacePage = accomPlaceRepository.getListAccomPlaceOfPartner(user.getId(), AccomStatusEnum.APPROVED.toString(), paging);
        List<AccomPlace> accomPlaceList = accomPlacePage.stream().toList();
        List<GetAccomPlaceResponse> responseList = accomPlaceList.stream()
                .map(accomPlace -> accomPlaceMapper.toGetAccomPlaceResponse(accomPlace))
                .collect(Collectors.toList());
        return new BasePagingData<>(responseList,
                accomPlacePage.getNumber(),
                accomPlacePage.getSize(),
                accomPlacePage.getTotalElements());
    }

    @Override
    @Transactional
    public BasePagingData<GetPriceCustomAccomResponse> getListPriceCustomAccomOfPartner(String hostMail,
                                                                                        Integer pageNum,
                                                                                        Integer pageSize,
                                                                                        String sortType,
                                                                                        String field) {
        User user = userRepository.findByMail(hostMail).orElseThrow(() -> new ResourceNotFoundException("user"));
        Pageable paging = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.valueOf(sortType), field));
        Page<AccomPlace> accomPlacePage = accomPlaceRepository.getListAccomPlaceOfPartner(user.getId(), AccomStatusEnum.APPROVED.toString(), paging);
        List<AccomPlace> accomPlaceList = accomPlacePage.stream().toList();
        List<GetPriceCustomAccomResponse> responseList = accomPlaceList.stream()
                .map(accomPlace -> accomPlaceMapper.toGetPriceCustomAccom(accomPlace))
                .collect(Collectors.toList());
        return new BasePagingData<>(responseList,
                accomPlacePage.getNumber(),
                accomPlacePage.getSize(),
                accomPlacePage.getTotalElements());
    }

    @Override
    @Transactional
    public BasePagingData<GetRangeDateBookingAccomResponse> getListRangeDateAccomOfPartner(String hostMail,
                                                                                           Integer pageNum,
                                                                                           Integer pageSize,
                                                                                           String sortType,
                                                                                           String field) {
        User user = userRepository.findByMail(hostMail).orElseThrow(() -> new ResourceNotFoundException("user"));
        Pageable paging = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.valueOf(sortType), field));
        Page<AccomPlace> accomPlacePage = accomPlaceRepository.getListAccomPlaceOfPartner(user.getId(), AccomStatusEnum.APPROVED.toString(), paging);
        List<AccomPlace> accomPlaceList = accomPlacePage.stream().toList();
        List<GetRangeDateBookingAccomResponse> responseList = accomPlaceList.stream()
                .map(accomPlace -> accomPlaceMapper.toGetRangeDateBookingAccomResponse(accomPlace))
                .collect(Collectors.toList());
        return new BasePagingData<>(responseList,
                accomPlacePage.getNumber(),
                accomPlacePage.getSize(),
                accomPlacePage.getTotalElements());
    }

    @Override
    @Transactional
    public BasePagingData<AccomPlaceGeneralResponse> getAllAcommPlaceWaitingApprovalWithPaging(Integer pageNum,
                                                                                               Integer pageSize,
                                                                                               String sortType,
                                                                                               String field) {
        Pageable paging = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.valueOf(sortType), field));
        Page<AccomPlace> accomPlacePage = accomPlaceRepository.getAllWithPaging(
                paging, AccomStatusEnum.WAITING_FOR_APPROVAL);
        List<AccomPlace> accomPlaceList = accomPlacePage.stream().toList();
        List<AccomPlaceGeneralResponse> responseList = new LinkedList<>();
        for (AccomPlace place : accomPlaceList) {
            PercentCreateAccomResponse percentProgess = getPercentCreateAccom(place.getId());
            Set<ImageAccom> imageAccoms = place.getImageAccoms();
            responseList.add(AccomPlaceGeneralResponse.builder()
                    .accomId(place.getId())
                    .accomName(place.getAccomName())
                    .logo(imageAccoms.size() > 0 ? imageAccoms.stream().findFirst().get().getImgAccomLink() : null)
                    .progress(percentProgess.getPercent())
                    .addressDetail(place.getAddressDetail())
                    .status(place.getStatus())
                    .build());
        }
        return new BasePagingData<>(responseList,
                accomPlacePage.getNumber(),
                accomPlacePage.getSize(),
                accomPlacePage.getTotalElements());
    }

    @Override
    @Transactional
    public BasePagingData<AccomPlaceGeneralResponse> getListAccomPlaceWaitingOfPartner(String hostMail,
                                                                                       Integer pageNum,
                                                                                       Integer pageSize,
                                                                                       String sortType,
                                                                                       String field) {
        User user = userRepository.findByMail(hostMail).orElseThrow(() -> new ResourceNotFoundException("user"));
        Pageable paging = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.valueOf(sortType), field));
        Page<AccomPlace> accomPlacePage = accomPlaceRepository.getListAccomPlaceOfPartner(
                user.getId(),
                AccomStatusEnum.WAITING_FOR_COMPLETE.toString(),
                paging);
        List<AccomPlace> accomPlaceList = accomPlacePage.stream().toList();
        List<AccomPlaceGeneralResponse> responseList = new LinkedList<>();
        for (AccomPlace place : accomPlaceList) {
            PercentCreateAccomResponse percentProgess = getPercentCreateAccom(place.getId());
            Set<ImageAccom> imageAccoms = place.getImageAccoms();
            responseList.add(AccomPlaceGeneralResponse.builder()
                    .accomId(place.getId())
                    .accomName(place.getAccomName())
                    .logo(imageAccoms.size() > 0 ? imageAccoms.stream().findFirst().get().getImgAccomLink() : null)
                    .progress(percentProgess.getPercent())
                    .addressDetail(place.getAddressDetail())
                    .status(place.getStatus())
                    .build());
        }
        return new BasePagingData<>(responseList,
                accomPlacePage.getNumber(),
                accomPlacePage.getSize(),
                accomPlacePage.getTotalElements());
    }

    @Override
    @Transactional
    public BaseMessageData changeStatusAccomPlace(Long id, String status) {
        AccomPlace accomPlace = accomPlaceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("Accom place")));
        accomPlaceRepository.changeStatusAccomPlace(id, status);
        return new BaseMessageData(AppContants.UPDATE_SUCCESS_MESSAGE("accom place"));
    }

    @Override
    @Transactional
    public BaseMessageData approveAccomPlace(Long id) {
        AccomPlace accomPlace = accomPlaceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("Accom place")));
        if (accomPlace.getStatus().equals(AccomStatusEnum.WAITING_FOR_APPROVAL)) {
            accomPlaceRepository.changeStatusAccomPlace(id, AccomStatusEnum.APPROVED.toString());
            return new BaseMessageData(AppContants.UPDATE_SUCCESS_MESSAGE("accom place"));
        }
        throw new ForbiddenException("Accom place is approved");
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

    @Override
    @Transactional
    public GetGeneralInfoAccomResponse getGeneralInfoAccom(Long accomId) {
        AccomPlace accomPlace = accomPlaceRepository.findById(accomId)
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("Accom place")));
        AccommodationCategories categories = accommodationCategoriesRepository.findById(accomPlace.getAccomCateId()).get();

//        Lấy danh sách phụ phí của accom place
        List<SurchargeOfAccom> surchargeOfAccoms = surchargeOfAccomRepository.findByAccomPlaceId(accomId);
        List<ItemSurchargeResponse> surcharges = new ArrayList<>();
        for (SurchargeOfAccom surchargeOfAccom : surchargeOfAccoms) {
            surcharges.add(ItemSurchargeResponse.builder()
                    .surchargeName(surchargeOfAccom.getSurchargeCategory().getSurchargeCateName())
                    .surchargeCode(surchargeOfAccom.getSurchargeCategory().getSurchargeCode())
                    .cost(surchargeOfAccom.getCost()).build());
        }

        GetGeneralInfoAccomResponse response = GetGeneralInfoAccomResponse.builder()
                .nameAccom(accomPlace.getAccomName())
                .accomCateName(categories.getAccomCateName())
                .description(accomPlace.getDescription())
                .guide(accomPlace.getGuide())
                .acreage(accomPlace.getAcreage())
                .pricePerNight(accomPlace.getPricePerNight())
                .checkInFrom(accomPlace.getCheckInFrom())
                .checkOutTo(accomPlace.getCheckOutTo())
                .surchargeList(surcharges)
                .discountPercent(accomPlace.getDiscount())
                .build();
        return response;
    }

    @Override
    @Transactional
    public GetAddressAccomResponse getAddressAccom(Long accomId) {
        AccomPlace accomPlace = accomPlaceRepository.findById(accomId)
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("Accom place")));
        Optional<District> district = districtRepository.findByDistrictCode(accomPlace.getDistrictCode());
        Optional<Ward> ward = wardRepository.findByWardCode(accomPlace.getWardCode());
        Optional<Province> province = provinceRepository.findByProvinceCode(accomPlace.getProvinceCode());

        String districtCode = district.isPresent() ? district.get().getDistrictCode() : null;
        String districtName = district.isPresent() ? district.get().getDistrictName() : null;
        String wardCode = ward.isPresent() ? ward.get().getWardCode() : null;
        String wardName = ward.isPresent() ? ward.get().getWardName() : null;
        String provinceCode = province.isPresent() ? province.get().getProvinceCode() : null;
        String provinceName = province.isPresent() ? province.get().getProvinceName() : null;
        String addressDetail = accomPlace.getAddressDetail();

        String[] arrSplitAddress = addressDetail != null ? addressDetail.split(",") : null;
        GetAddressAccomResponse response = new GetAddressAccomResponse(arrSplitAddress != null ? arrSplitAddress[0] : null,
                districtCode,
                districtName,
                wardCode,
                wardName,
                provinceCode,
                provinceName,
                accomPlace.getLongitude(),
                accomPlace.getLatitude(),
                accomPlace.getGuide()
        );
        return response;
    }

    @Override
    @Transactional
    public GetFacilityAccomResponse getFacilityAccom(Long accomId) {
        AccomPlace accomPlace = accomPlaceRepository.findById(accomId)
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("Accom place")));
        List<Facility> facilities = accomPlace.getFacilitySet().stream().toList();
        List<FacilityResponse> facilityResponses = facilities.stream().map(
                facilityMapper::toFacilityResponse).collect(Collectors.toList());
        GetFacilityAccomResponse response = GetFacilityAccomResponse.builder()
                .total(facilityResponses.size())
                .facilities(facilityResponses)
                .build();
        return response;
    }

    @Override
    @Transactional
    public GetGalleryAccomResponse getGalleryAccom(Long accomId) {
        AccomPlace accomPlace = accomPlaceRepository.findById(accomId)
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("Accom place")));
        List<ImageAccom> imageAccoms = accomPlace.getImageAccoms().stream().toList();
        List<String> imageAccomUrls = new LinkedList<>();
        for (ImageAccom imageAccom : imageAccoms) {
            imageAccomUrls.add(imageAccom.getImgAccomLink());
        }
        return GetGalleryAccomResponse.builder()
                .imageAccomUrls(imageAccomUrls)
                .cldVideoId(accomPlace.getCldVideoId())
                .build();
    }

    @Override
    @Transactional
    public GetRoomSettingAccomResponse getRoomSettingAccom(Long accomId) {
        AccomPlace accomPlace = accomPlaceRepository.findById(accomId)
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("Accom place")));
        AccommodationCategories accommodationCategories = accommodationCategoriesRepository.findById(accomPlace.getAccomCateId()).get();
        String accomCateName = accommodationCategories.getAccomCateName();
        Long accomCateId = accommodationCategories.getId();
        List<BedRoom> bedRooms = accomPlace.getBedRoomSet().stream().toList();
        List<TypeBedOfRoom> typeBedOfRooms = new LinkedList<>();
        for (BedRoom bedRoom : bedRooms) {
            TypeBed typeBedOfRoom = typeBedRepository.findByTypeBedCode(bedRoom.getTypeBedCode()).get();
            TypeBedOfRoom bedOfRoom = typeBedMapper.toTypeBedOfRoom(typeBedOfRoom);
            typeBedOfRooms.add(bedOfRoom);
        }
        BedRoomResponse bedRoomResponse = BedRoomResponse.builder()
                .total(typeBedOfRooms.size())
                .typeBeds(typeBedOfRooms)
                .build();
        return GetRoomSettingAccomResponse.builder()
                .accomCateId(accomCateId)
                .accomCateName(accomCateName)
                .numKitchen(accomPlace.getNumKitchen())
                .numBathRoom(accomPlace.getNumBathRoom())
                .bedRooms(bedRoomResponse)
                .numPeople(accomPlace.getNumPeople())
                .build();
    }

    @Override
    @Transactional
    public GetPolicyAccomResponse getPolicyAccom(Long accomId) {
        AccomPlace accomPlace = accomPlaceRepository.findById(accomId)
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("Accom place")));

        String cancelPolicyCode = accomPlace.getCancellationPolicy() != null ? accomPlace.getCancellationPolicy().toString() : null;
        GeneralPolicyDetail generalPolicyDetail = generalPolicyDetailRepository.findById(accomId).get();
        GetPolicyAccomResponse response = new GetPolicyAccomResponse(
                cancelPolicyCode,
                accomPlace.getCancellationFeeRate(),
                generalPolicyDetail.getAllowEvent(),
                generalPolicyDetail.getAllowPet(),
                generalPolicyDetail.getAllowSmoking());
        return response;
    }

    @Override
    @Transactional
    public GetPaymentAccomResponse getPaymentAccom(Long accomId) {
        AccomPlace accomPlace = accomPlaceRepository.findById(accomId)
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("Accom place")));
        PaymentInfoDetail paymentInfoDetail = paymentInfoDetailRepository.findById(accomId).get();
        return GetPaymentAccomResponse.builder()
                .bankId(paymentInfoDetail.getBankId())
                .accountNumber(paymentInfoDetail.getAccountNumber())
                .accountNameHost(paymentInfoDetail.getAccountNameHost())
                .swiftCode(paymentInfoDetail.getSwiftCode())
                .build();
    }
}
