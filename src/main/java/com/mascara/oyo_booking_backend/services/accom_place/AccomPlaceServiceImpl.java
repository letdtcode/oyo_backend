package com.mascara.oyo_booking_backend.services.accom_place;

import com.mascara.oyo_booking_backend.dtos.BaseMessageData;
import com.mascara.oyo_booking_backend.dtos.request.accom_place.AddAccomPlaceRequest;
import com.mascara.oyo_booking_backend.dtos.request.accom_place.GetAccomPlaceFilterRequest;
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
                .status(AccomStatusEnum.ENABLE).build();
        int numRoom = accomPlace.getNumBedRoom();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            User user = userRepository.findByMail(authentication.getName()).get();
            accomPlace.setUser(user);
            accomPlace.setUserId(user.getId());
        } else {
            User user = userRepository.findByMail("client1@gmail.com").orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("User")));
            accomPlace.setUser(user);
            accomPlace.setUserId(user.getId());
            accomPlace.setCreatedBy("dev");
            accomPlace.setLastModifiedBy("dev");
        }
        accomPlace.setNumBooking(0L);
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
    public BasePagingData<GetAccomPlaceResponse> getAccomPlaceFilterWithPaging(GetAccomPlaceFilterRequest filter, Integer pageNum, Integer pageSize, String sortType, String field) {
        int length = 0;
        if (filter.getFacilityName() != null) {
            length = filter.getFacilityName().size();
        }
        if (filter.getFacilityName() == null || filter.getFacilityName().isEmpty()) {
            filter.setFacilityName(List.of(UUID.randomUUID().toString()));
        }
        Pageable paging = PageRequest.of(pageNum, 10, Sort.by(Sort.Direction.valueOf(sortType), field));
        Page<AccomPlace> accomPlacePage = accomPlaceRepository.getPageWithFullFilter(filter.getProvinceCode(), filter.getDistrictCode(), filter.getWardCode(), filter.getPriceFrom(), filter.getPriceTo(), filter.getFacilityName(), length, filter.getNumBathroom(), filter.getNumPeople(), filter.getNumBed(), paging);
        List<AccomPlace> accomPlaceList = accomPlacePage.stream().toList();
        List<GetAccomPlaceResponse> responseList = accomPlaceList.stream().map(accomPlace -> accomPlaceMapper.toGetAccomPlaceResponse(accomPlace)).collect(Collectors.toList());
        return new BasePagingData<>(responseList, accomPlacePage.getNumber(), accomPlacePage.getSize(), accomPlacePage.getTotalElements());
    }

    @Override
    @Transactional
    public GetAccomPlaceResponse getAccomPlaceDetails(Long id) {
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
        return accomPlaceMapper.toGetAccomPlaceResponse(accomPlace);
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
        AccomPlace accomPlace = accomPlaceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("Accom place")));
        accomPlace.setDeleted(true);
        accomPlaceRepository.save(accomPlace);
        return new BaseMessageData(AppContants.DELETE_SUCCESS_MESSAGE("accom place"));
    }
}
