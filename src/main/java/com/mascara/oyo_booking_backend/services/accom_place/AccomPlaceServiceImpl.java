package com.mascara.oyo_booking_backend.services.accom_place;

import com.mascara.oyo_booking_backend.dtos.BaseMessageData;
import com.mascara.oyo_booking_backend.dtos.request.accom_place.*;
import com.mascara.oyo_booking_backend.dtos.response.accommodation.GetAccomPlaceDetailResponse;
import com.mascara.oyo_booking_backend.dtos.response.accommodation.GetAccomPlaceResponse;
import com.mascara.oyo_booking_backend.dtos.response.paging.BasePagingData;
import com.mascara.oyo_booking_backend.entities.*;
import com.mascara.oyo_booking_backend.enums.AccomStatusEnum;
import com.mascara.oyo_booking_backend.exceptions.NotCredentialException;
import com.mascara.oyo_booking_backend.exceptions.ResourceNotFoundException;
import com.mascara.oyo_booking_backend.external_modules.storage.cloudinary.CloudinaryService;
import com.mascara.oyo_booking_backend.mapper.AccomPlaceMapper;
import com.mascara.oyo_booking_backend.repositories.*;
import com.mascara.oyo_booking_backend.utils.AppContants;
import com.mascara.oyo_booking_backend.utils.SlugsUtils;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
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
    private AccomPlaceMapper accomPlaceMapper;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private ImageAccomRepository imageAccomRepository;

    @Autowired
    private TypeBedRepository typeBedRepository;

    @Autowired
    private BedRoomRepository bedRoomRepository;

    @Autowired
    private SurchargeOfAccomRepository surchargeOfAccomRepository;

    @Autowired
    private SurchargeCategoryRepository surchargeCategoryRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Override
    @Transactional
    public GetAccomPlaceResponse addAccomPlace(AddAccomPlaceRequest request) {
        Province province = provinceRepository.findByProvinceCode(request.getProvinceCode()).orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("province")));
        District district = districtRepository.findByDistrictCode(request.getDistrictCode()).orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("district")));
        Ward ward = wardRepository.findByWardCode(request.getWardCode()).orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("ward")));

        AccommodationCategories accomCategories = accommodationCategoriesRepository.findByAccomCateName(request.getAccomCateName()).orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("accommodation category")));

        Set<Facility> facilitySet = new HashSet<>();
        for (String facilityName : request.getFacilityNameList()) {
            facilitySet.add(facilityRepository.findByFacilityName(facilityName).orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("facility"))));
        }
        String addressDetail = request.getNumHouseAndStreetName() + ", " + ward.getWardName() + ", " + district.getDistrictName() + ", " + province.getProvinceName();
        String slugs = SlugsUtils.toSlug(request.getAccomName());
        AccomPlace accomPlace = AccomPlace.builder()
                .accomName(request.getAccomName())
                .description(request.getDescription())
                .addressDetail(addressDetail)
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
                .refundPolicy("Trả trước 7 ngày")
                .pricePerNight(request.getPricePerNight())
                .facilitySet(facilitySet)
                .status(AccomStatusEnum.ENABLE).build();
        int numRoom = accomPlace.getNumBedRoom();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            User user = userRepository.findByMail(authentication.getName()).get();
            accomPlace.setUser(user);
            accomPlace.setGuide("Đến là đón");
            accomPlace.setRefundPolicy("Trả trước 7 ngày");
            accomPlace.setUserId(user.getId());
        } else {
            User user = userRepository.findByMail("client1@gmail.com")
                    .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("User")));
            accomPlace.setUser(user);
            accomPlace.setUserId(user.getId());
            accomPlace.setGuide("Giờ mở cửa và thông tin về các hoạt động có sẵn tại trung tâm.");
            accomPlace.setCreatedBy("dev");
            accomPlace.setLastModifiedBy("dev");
        }
        accomPlace = accomPlaceRepository.save(accomPlace);
        TypeBed typeBedDefault = typeBedRepository.findByTypeBedCode("TYPE_BED_003")
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("Type bed")));
        List<BedRoom> bedRooms = new ArrayList<>();
        for (int i = 0; i < numRoom; i++) {
            BedRoom bedRoom = BedRoom.builder()
                    .accomPlace(accomPlace)
                    .accomId(accomPlace.getId())
                    .typeBed(typeBedDefault)
                    .typeBedCode(typeBedDefault.getTypeBedCode())
                    .build();
            if (authentication == null) {
                bedRoom.setCreatedBy("dev");
                bedRoom.setLastModifiedBy("dev");
            }
            bedRooms.add(bedRoom);
        }
        bedRoomRepository.saveAll(bedRooms);

        if (authentication == null) {
            List<SurchargeCategory> surchargeCategoryList = surchargeCategoryRepository.findAll();
            for (SurchargeCategory surchargeCategory : surchargeCategoryList) {
                SurchargeOfAccom surcharge = SurchargeOfAccom.builder()
                        .cost(20000D)
                        .accomPlace(accomPlace)
                        .accomPlaceId(accomPlace.getId())
                        .surchargeCategory(surchargeCategory)
                        .surchargeCateId(surchargeCategory.getId())
                        .createdBy("dev")
                        .lastModifiedBy("dev")
                        .build();
                surchargeOfAccomRepository.save(surcharge);
            }
        }
        GetAccomPlaceResponse response = accomPlaceMapper.toGetAccomPlaceResponse(accomPlace);
        return response;
    }

    @Override
    public GetAccomPlaceResponse addImageAccomPlace(List<MultipartFile> files, Long id, String hostMail) {
        AccomPlace accomPlace = accomPlaceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("Accom Place")));
        User host = userRepository.findByUserId(accomPlace.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("user")));
        host = (User) Hibernate.unproxy(host);
        if (!host.getMail().equals(hostMail)) {
            throw new NotCredentialException("Not permit to edit image for accom place");
        }
        if (!files.isEmpty()) {
            for (int i = 0; i < files.size(); i++) {
                String pathImg = cloudinaryService.store(files.get(i)).getImageUrl();
                ImageAccom imageAccom = ImageAccom.builder().imgAccomLink(pathImg)
                        .accomPlace(accomPlace).accomPlaceId(accomPlace.getId()).build();
                imageAccomRepository.save(imageAccom);
            }
            GetAccomPlaceResponse response = accomPlaceMapper.toGetAccomPlaceResponse(accomPlace);
            return response;
        }
        throw new ResourceNotFoundException(AppContants.FILE_IS_NULL);
    }

    @Override
    @Transactional
    public BasePagingData<GetAccomPlaceResponse> getAllAccomPlaceWithPaging(Integer pageNum, Integer pageSize, String sortType, String field) {
        Pageable paging = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.fromString(sortType), field));
        Page<AccomPlace> accomPlacePage = accomPlaceRepository.getAllWithPaging(paging);
        List<AccomPlace> accomPlaceList = accomPlacePage.stream().toList();
        List<GetAccomPlaceResponse> responseList = accomPlaceList.stream().map(accomPlace -> accomPlaceMapper.toGetAccomPlaceResponse(accomPlace)).collect(Collectors.toList());
        return new BasePagingData<>(responseList, accomPlacePage.getNumber(), accomPlacePage.getSize(), accomPlacePage.getTotalElements());
    }

    @Override
    @Transactional
    public BasePagingData<GetAccomPlaceResponse> getFilterByKeyWord(String keyword, Integer pageNum, Integer pageSize, String sortType, String field) {
        Pageable paging = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.fromString(sortType), field));
        Page<AccomPlace> accomPlacePage = accomPlaceRepository.getFilterByKeyWord(keyword, paging);
        List<AccomPlace> accomPlaceList = accomPlacePage.stream().toList();
        List<GetAccomPlaceResponse> responseList = accomPlaceList.stream().map(accomPlace -> accomPlaceMapper.toGetAccomPlaceResponse(accomPlace)).collect(Collectors.toList());
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
        List<GetAccomPlaceResponse> responseList = accomPlaceList.stream().map(accomPlace -> accomPlaceMapper.toGetAccomPlaceResponse(accomPlace)).collect(Collectors.toList());
        return new BasePagingData<>(responseList, accomPlacePage.getNumber(), accomPlacePage.getSize(), accomPlacePage.getTotalElements());
    }

    @Override
    @Transactional
    public GetAccomPlaceDetailResponse getAccomPlaceDetails(Long id) {
        AccomPlace accomPlace = accomPlaceRepository.findByIdByClient(id)
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
        Long numView = accomPlace.getNumView();
        accomPlace.setNumView(numView + 1);
        accomPlace = accomPlaceRepository.save(accomPlace);
        return accomPlaceMapper.toGetAccomPlaceDetailResponse(accomPlace);
    }

    @Override
    @Transactional
    public BasePagingData<GetAccomPlaceResponse> getTopAccomPlaceByField(Integer pageNum, Integer pageSize, String sortType, String field) {
        Pageable paging = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.valueOf(sortType), field));
        Page<AccomPlace> accomPlacePage = accomPlaceRepository.getAllWithPaging(paging);
        List<AccomPlace> accomPlaceList = accomPlacePage.stream().toList();
        List<GetAccomPlaceResponse> responseList = accomPlaceList.stream().map(accomPlace -> accomPlaceMapper.toGetAccomPlaceResponse(accomPlace)).collect(Collectors.toList());
        return new BasePagingData<>(responseList,
                accomPlacePage.getNumber(),
                accomPlacePage.getSize(),
                accomPlacePage.getTotalElements());
    }

    @Override
    @Transactional
    public BasePagingData<GetAccomPlaceResponse> getListAccomPlaceOfPartner(String hostMail, Integer pageNum, Integer pageSize, String sortType, String field) {
        User user = userRepository.findByMail(hostMail).orElseThrow(() -> new ResourceNotFoundException("user"));
        Pageable paging = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.valueOf(sortType), field));
        Page<AccomPlace> accomPlacePage = accomPlaceRepository.getListAccomPlaceOfPartner(user.getId(), paging);
        List<AccomPlace> accomPlaceList = accomPlacePage.stream().toList();
        List<GetAccomPlaceResponse> responseList = accomPlaceList.stream()
                .map(accomPlace -> accomPlaceMapper.toGetAccomPlaceResponse(accomPlace)).collect(Collectors.toList());
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
    public BaseMessageData deleteAccomPlace(Long id) {
        AccomPlace accomPlace = accomPlaceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("Accom place")));
        accomPlace.setDeleted(true);
        accomPlaceRepository.save(accomPlace);
        return new BaseMessageData(AppContants.DELETE_SUCCESS_MESSAGE("accom place"));
    }


    @Override
    @Transactional
    public GetAccomPlaceDetailResponse updateTitleAccom(UpdateTitleAccomRequest request, Long accomId) {
        AccomPlace accomPlace = accomPlaceRepository.findById(accomId)
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("Accom place")));
        accomPlace.setAccomName(request.getNameAccom());
        accomPlace.setDescription(request.getDescription());
        accomPlace.setGuide(request.getGuide());
        accomPlace.setRefundPolicy(request.getRefundPolicy());
        accomPlace = accomPlaceRepository.save(accomPlace);
        return accomPlaceMapper.toGetAccomPlaceDetailResponse(accomPlace);
    }

    @Override
    @Transactional
    public GetAccomPlaceDetailResponse updateFacilityAccom(UpdateFacilityAccomRequest request, Long accomId) {
        AccomPlace accomPlace = accomPlaceRepository.findById(accomId)
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("Accom place")));
        Set<Facility> facilitySet = new HashSet<>();
        for (String facilityCode : request.getFacilityCodes()) {
            Facility facility = facilityRepository.findByFacilityCode(facilityCode).get();
            facilitySet.add(facility);
        }
        accomPlace.setFacilitySet(facilitySet);
        accomPlace = accomPlaceRepository.save(accomPlace);
        return accomPlaceMapper.toGetAccomPlaceDetailResponse(accomPlace);
    }

    @Override
    @Transactional
    public GetAccomPlaceDetailResponse updateRoomAccom(UpdateRoomAccomRequest request, Long accomId) {
        AccomPlace accomPlace = accomPlaceRepository.findById(accomId)
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("Accom place")));
        AccommodationCategories accomCate = accommodationCategoriesRepository.findByAccomCateName(request.getAccomCateName())
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("Accom category")));

        accomPlace.setNumBathRoom(request.getNumBathRoom());
        accomPlace.setNumKitchen(request.getNumKitchen());
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
        accomPlace = accomPlaceRepository.save(accomPlace);
        return accomPlaceMapper.toGetAccomPlaceDetailResponse(accomPlace);
    }

    @Override
    @Transactional
    public GetAccomPlaceDetailResponse updateImageAccom(UpdateImageAccomRequest request, Long accomId) {
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
        accomPlace = accomPlaceRepository.save(accomPlace);
        return accomPlaceMapper.toGetAccomPlaceDetailResponse(accomPlace);
    }

    @Override
    @Transactional
    public GetAccomPlaceDetailResponse updateAddressAccom(UpdateAddressAccomRequest request, Long accomId) {
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
        String addressDetail = request.getAddressDetail() + ", " + ward.getWardName() + ", " + district.getDistrictName() + ", " + province.getProvinceName();
        accomPlace.setDistrictCode(request.getDistrictCode());
        accomPlace.setWardCode(request.getWardCode());
        accomPlace.setProvince(province);
        accomPlace.setProvinceCode(request.getProvinceCode());
        accomPlace.setAddressDetail(addressDetail);
        accomPlace = accomPlaceRepository.save(accomPlace);
        return accomPlaceMapper.toGetAccomPlaceDetailResponse(accomPlace);
    }

    @Override
    @Transactional
    public GetAccomPlaceDetailResponse updateSurchargeAccom(UpdateSurchargeAccomRequest request, Long accomId) {
        AccomPlace accomPlace = accomPlaceRepository.findById(accomId)
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("Accom place")));

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
        accomPlace = accomPlaceRepository.findById(accomId).get();
        return accomPlaceMapper.toGetAccomPlaceDetailResponse(accomPlace);
    }
    @Override
    @Transactional
    public GetAccomPlaceDetailResponse changePriceAccom(Double pricePerNight, Long accomId) {
        AccomPlace accomPlace = accomPlaceRepository.findById(accomId)
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("Accom place")));
        accomPlace.setPricePerNight(pricePerNight);
        accomPlace = accomPlaceRepository.save(accomPlace);
        return accomPlaceMapper.toGetAccomPlaceDetailResponse(accomPlace);
    }

    @Override
    @Transactional
    public GetAccomPlaceDetailResponse updateDiscountAccom(Double discountPercent, Long accomId) {
        AccomPlace accomPlace = accomPlaceRepository.findById(accomId)
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("Accom place")));
//        Double newPrice = accomPlace.getPricePerNight() - ((accomPlace.getPricePerNight() * discountPercent) / 100);
        accomPlace.setDiscount(discountPercent);
//        accomPlace.setPricePerNight(newPrice);
        accomPlace = accomPlaceRepository.save(accomPlace);
        return accomPlaceMapper.toGetAccomPlaceDetailResponse(accomPlace);
    }
}
