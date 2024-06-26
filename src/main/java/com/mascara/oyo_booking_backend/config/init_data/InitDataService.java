package com.mascara.oyo_booking_backend.config.init_data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mascara.oyo_booking_backend.config.init_data.models.InitAccomPlaceModel;
import com.mascara.oyo_booking_backend.config.init_data.models.InitDbModel;
import com.mascara.oyo_booking_backend.config.init_data.service.InitAccomPlaceService;
import com.mascara.oyo_booking_backend.dtos.auth.request.RegisterRequest;
import com.mascara.oyo_booking_backend.entities.accommodation.AccomPlace;
import com.mascara.oyo_booking_backend.entities.accommodation.AccommodationCategories;
import com.mascara.oyo_booking_backend.entities.accommodation.ImageAccom;
import com.mascara.oyo_booking_backend.entities.address.District;
import com.mascara.oyo_booking_backend.entities.address.Province;
import com.mascara.oyo_booking_backend.entities.address.Ward;
import com.mascara.oyo_booking_backend.entities.authentication.Role;
import com.mascara.oyo_booking_backend.entities.authentication.User;
import com.mascara.oyo_booking_backend.entities.bank.Bank;
import com.mascara.oyo_booking_backend.entities.facility.Facility;
import com.mascara.oyo_booking_backend.entities.facility.FacilityCategories;
import com.mascara.oyo_booking_backend.entities.recommend.ItemFeature;
import com.mascara.oyo_booking_backend.entities.surcharge.SurchargeCategory;
import com.mascara.oyo_booking_backend.entities.type_bed.TypeBed;
import com.mascara.oyo_booking_backend.enums.CommonStatusEnum;
import com.mascara.oyo_booking_backend.enums.RoleEnum;
import com.mascara.oyo_booking_backend.exceptions.ResourceNotFoundException;
import com.mascara.oyo_booking_backend.repositories.*;
import com.mascara.oyo_booking_backend.services.user.UserService;
import com.mascara.oyo_booking_backend.utils.AppContants;
import com.mascara.oyo_booking_backend.utils.SlugsUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 23/10/2023
 * Time      : 2:05 SA
 * Filename  : InitDbProvinceService
 */
@Component
@RequiredArgsConstructor
public class InitDataService implements CommandLineRunner {
    private final ProvinceRepository provinceRepository;
    private final DistrictRepository districtRepository;
    private final WardRepository wardRepository;
    private final RoleRepository roleRepository;
    private final FacilityCategoriesRepository facilityCategoriesRepository;
    private final FacilityRepository facilityRepository;
    private final AccommodationCategoriesRepository accommodationCategoriesRepository;
    private final TypeBedRepository typeBedRepository;
    private final AccomPlaceRepository accomPlaceRepository;
    private final InitAccomPlaceService initAccomPlaceService;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final UserService userService;
    private final SurchargeCategoryRepository surchargeCategoryRepository;
    private final ImageAccomRepository imageAccomRepository;
    private final BankRepository bankRepository;
    private final ItemFeatureRepository itemFeatureRepository;

    @PersistenceContext
    private final EntityManager entityManager;

    public void initDataUser() {
        Optional<Role> roleAdmin = roleRepository.findByRoleName(RoleEnum.ROLE_ADMIN.toString());
        if (!roleAdmin.isPresent()) {
            Role admin = Role.builder().roleName(RoleEnum.ROLE_ADMIN).build();
            admin.setCreatedBy("dev");
            roleRepository.save(admin);
        }

        Optional<Role> rolePartner = roleRepository.findByRoleName(RoleEnum.ROLE_PARTNER.toString());
        if (!rolePartner.isPresent()) {
            Role partner = Role.builder().roleName(RoleEnum.ROLE_PARTNER).build();
            partner.setCreatedBy("dev");
            partner.setLastModifiedBy("dev");
            roleRepository.save(partner);
        }

        Optional<Role> roleClient = roleRepository.findByRoleName(RoleEnum.ROLE_CLIENT.toString());
        if (!roleClient.isPresent()) {
            Role client = Role.builder().roleName(RoleEnum.ROLE_CLIENT).build();
            client.setCreatedBy("dev");
            roleRepository.save(client);
        }

        List<User> userList = userRepository.checkExistData();
        try {
            if (userList.isEmpty()) {
                File file = new File(
                        Objects.requireNonNull(this.getClass().getClassLoader().getResource("initData/json/initDbUser.json")).getFile()
                );
                ObjectMapper mapper = new ObjectMapper();
                InitDbModel<RegisterRequest> initModel = mapper.readValue(file, new TypeReference<>() {
                });
                List<RegisterRequest> registerRequestList = initModel.getData();
                for (RegisterRequest registerRequest : registerRequestList) {
                    String passwordEncoded = encoder.encode(registerRequest.getPassword());
                    userService.addUser(registerRequest, passwordEncoded);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void implementInitDataMenuActionDistrict() {
        List<District> checkList = districtRepository.checkExistData();
        try {
            if (checkList.isEmpty()) {
                File file = new File(
                        Objects.requireNonNull(this.getClass().getClassLoader().getResource("initData/json/initDbDistrict.json")).getFile()
                );
                ObjectMapper mapper = new ObjectMapper();
                InitDbModel<District> initModel = mapper.readValue(file, new TypeReference<>() {
                });
                List<District> districtList = initModel.getData();
                for (int i = 0; i < districtList.size(); i++) {
                    District district = districtList.get(i);
                    district.setProvince(provinceRepository.findByProvinceCode(district.getProvinceCode())
                            .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("province"))));
                    district.setCreatedBy("dev");
                    district.setLastModifiedBy("dev");
                    districtList.set(i, district);
                }
                districtRepository.saveAll(districtList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void implementInitDataMenuActionProvince() {
        List<Province> checkList = provinceRepository.checkExistData();
        try {
            if (checkList.isEmpty()) {
                File file = new File(
                        Objects.requireNonNull(this.getClass().getClassLoader().getResource("initData/json/initDbProvince.json")).getFile()
                );

                ObjectMapper mapper = new ObjectMapper();
                InitDbModel<Province> initModel = mapper.readValue(file, new TypeReference<>() {
                });
                for (Province province : initModel.getData()) {
                    province.setSlugs(SlugsUtils.toSlug(province.getProvinceName()));
                    province.setCreatedBy("dev");
                    province.setNumBooking(0L);
                    province.setLastModifiedBy("dev");
                }
                provinceRepository.saveAll(initModel.getData());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void implementInitDataMenuActionWard() {
        List<Ward> checkList = wardRepository.checkExistData();
        try {
            if (checkList.isEmpty()) {
                File file = new File(
                        Objects.requireNonNull(this.getClass().getClassLoader().getResource("initData/json/initDbWard.json")).getFile()
                );

                ObjectMapper mapper = new ObjectMapper();
                InitDbModel<Ward> initModel = mapper.readValue(file, new TypeReference<>() {
                });

                List<Ward> wardList = initModel.getData();
                for (int i = 0; i < wardList.size(); i++) {
                    Ward ward = wardList.get(i);
                    ward.setDistrict(districtRepository.findByDistrictCode(ward.getDistrictCode())
                            .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("district"))));
                    ward.setCreatedBy("dev");
                    ward.setLastModifiedBy("dev");
                    wardList.set(i, ward);
                }
                wardRepository.saveAll(wardList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void implementInitDataMenuActionFacilityCategory() {
        List<FacilityCategories> checkList = facilityCategoriesRepository.checkExistData();
        try {
            if (checkList.isEmpty()) {
                File file = new File(
                        Objects.requireNonNull(this.getClass().getClassLoader().getResource("initData/json/initDbFacilityCategory.json")).getFile()
                );

                ObjectMapper mapper = new ObjectMapper();
                InitDbModel<FacilityCategories> initModel = mapper.readValue(file, new TypeReference<>() {
                });
                for (FacilityCategories facilityCate : initModel.getData()) {
                    facilityCate.setStatus(CommonStatusEnum.ENABLE);
                    facilityCate.setCreatedBy("dev");
                    facilityCate.setLastModifiedBy("dev");
                }
                List<FacilityCategories> facilityCategoriesList = initModel.getData();
                facilityCategoriesRepository.saveAll(facilityCategoriesList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void implementInitDataMenuActionFacility() {
        List<Facility> checkList = facilityRepository.checkExistData();
        try {
            if (checkList.isEmpty()) {
                File file = new File(
                        Objects.requireNonNull(this.getClass().getClassLoader().getResource("initData/json/initDbFacility.json")).getFile()
                );

                ObjectMapper mapper = new ObjectMapper();
                InitDbModel<Facility> initModel = mapper.readValue(file, new TypeReference<>() {
                });

                List<Facility> facilityList = initModel.getData();
                for (int i = 0; i < facilityList.size(); i++) {
                    Facility facility = facilityList.get(i);
                    facility.setFacilityCategories(facilityCategoriesRepository.findByFaciCateCode(facility.getFacilityCateCode())
                            .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("facility category"))));
                    facility.setStatus(CommonStatusEnum.ENABLE);
                    facility.setCreatedBy("dev");
                    facility.setLastModifiedBy("dev");
                    facilityList.set(i, facility);
                }
                facilityRepository.saveAll(facilityList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void implementInitDataMenuActionAccomCategory() {
        List<AccommodationCategories> checkList = accommodationCategoriesRepository.checkExistData();
        try {
            if (checkList.isEmpty()) {
                File file = new File(
                        Objects.requireNonNull(this.getClass().getClassLoader().getResource("initData/json/initDbAccomCategory.json")).getFile()
                );

                ObjectMapper mapper = new ObjectMapper();
                InitDbModel<AccommodationCategories> initModel = mapper.readValue(file, new TypeReference<>() {
                });
                List<AccommodationCategories> accommodationCategoriesList = initModel.getData();
                for (AccommodationCategories accomCate : accommodationCategoriesList) {
                    accomCate.setStatus(CommonStatusEnum.ENABLE);
                    accomCate.setCreatedBy("dev");
                    accomCate.setLastModifiedBy("dev");
                }
                accommodationCategoriesRepository.saveAll(accommodationCategoriesList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void implementInitDataMenuActionTypeBed() {
        List<TypeBed> checkList = typeBedRepository.checkExistData();
        try {
            if (checkList.isEmpty()) {
                File file = new File(
                        Objects.requireNonNull(this.getClass().getClassLoader().getResource("initData/json/initDbTypeBed.json")).getFile()
                );

                ObjectMapper mapper = new ObjectMapper();
                InitDbModel<TypeBed> initModel = mapper.readValue(file, new TypeReference<>() {
                });
                List<TypeBed> typeBedList = initModel.getData();
                for (TypeBed typeBed : typeBedList) {
                    typeBed.setStatus(CommonStatusEnum.ENABLE);
                    typeBed.setCreatedBy("dev");
                    typeBed.setLastModifiedBy("dev");
                }
                typeBedRepository.saveAll(typeBedList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void implementInitDataAccomPlace() {
        List<AccomPlace> checkList = accomPlaceRepository.checkExistData();
        try {
            if (checkList.isEmpty()) {
                File file = new File(
                        Objects.requireNonNull(this.getClass().getClassLoader().getResource("initData/json/initDbAccomPlace.json")).getFile()
                );

                ObjectMapper mapper = new ObjectMapper();
                InitDbModel<InitAccomPlaceModel> initModel = mapper.readValue(file, new TypeReference<>() {
                });
                List<InitAccomPlaceModel> accomPlaceRequestList = initModel.getData();
                for (InitAccomPlaceModel accomPlace : accomPlaceRequestList) {
                    initAccomPlaceService.addAccomPlace(accomPlace, "client1@gmail.com");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //    For recommendation
    public void implementInitDataItemFeature() {
        List<ItemFeature> checkList = itemFeatureRepository.checkExistData();
        try {
            if (checkList.isEmpty()) {
                List<AccomPlace> accomPlaces = accomPlaceRepository.findAll();
                for (AccomPlace item : accomPlaces) {
                    String accomCategoryName = entityManager.createQuery("select ac.accomCateName from AccommodationCategories ac where ac.id = :accomCateId")
                            .setParameter("accomCateId", item.getAccomCateId())
                            .getSingleResult().
                            toString();
                    String provinceName = entityManager.createQuery("select p.provinceName from Province p where p.provinceCode = :provinceCode")
                            .setParameter("provinceCode", item.getProvinceCode())
                            .getSingleResult()
                            .toString();
                    double pricePerNight = item.getPricePerNight();

                    ItemFeature itemFeature = ItemFeature.builder()
                            .id(item.getId())
                            .accomCategoryName(accomCategoryName)
                            .provinceName(provinceName)
                            .pricePerNight(pricePerNight)
                            .build();
                    itemFeatureRepository.save(itemFeature);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void implementInitDataMenuActionSurcharge() {
        List<SurchargeCategory> checkList = surchargeCategoryRepository.checkExistData();
        try {
            if (checkList.isEmpty()) {
                File file = new File(
                        Objects.requireNonNull(this.getClass().getClassLoader().getResource("initData/json/initDbSurcharge.json")).getFile()
                );

                ObjectMapper mapper = new ObjectMapper();
                InitDbModel<SurchargeCategory> initModel = mapper.readValue(file, new TypeReference<>() {
                });

                List<SurchargeCategory> surchargeCategoryList = initModel.getData();
                for (int i = 0; i < surchargeCategoryList.size(); i++) {
                    SurchargeCategory surchargeCategory = surchargeCategoryList.get(i);
                    surchargeCategory.setStatus(CommonStatusEnum.ENABLE);
                    surchargeCategory.setCreatedBy("dev");
                    surchargeCategory.setLastModifiedBy("dev");
                    surchargeCategoryList.set(i, surchargeCategory);
                }
                surchargeCategoryRepository.saveAll(surchargeCategoryList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void implementInitDataMenuActionImageAccom() {
        List<ImageAccom> checkList = imageAccomRepository.checkExistData();
        try {
            if (checkList.isEmpty()) {
                File file = new File(
                        Objects.requireNonNull(this.getClass().getClassLoader().getResource("initData/json/initDbImageAccom.json")).getFile()
                );

                ObjectMapper mapper = new ObjectMapper();
                InitDbModel<ImageAccom> initModel = mapper.readValue(file, new TypeReference<>() {
                });

                List<ImageAccom> imageAccomList = initModel.getData();
                for (int i = 0; i < imageAccomList.size(); i++) {
                    Long accomId = imageAccomList.get(i).getAccomPlaceId();
                    AccomPlace accomPlace = accomPlaceRepository.findById(accomId).get();
                    ImageAccom imageAccom = imageAccomList.get(i);
                    imageAccom.setAccomPlace(accomPlace);
                    imageAccom.setCreatedBy("dev");
                    imageAccom.setLastModifiedBy("dev");
                    imageAccomList.set(i, imageAccom);
                }
                imageAccomRepository.saveAll(imageAccomList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void implementInitDataBankInfo() {
        List<Bank> checkList = bankRepository.checkExistData();
        try {
            if (checkList.isEmpty()) {
                File file = new File(
                        Objects.requireNonNull(this.getClass().getClassLoader().getResource("initData/json/initDbBank.json")).getFile()
                );
                ObjectMapper mapper = new ObjectMapper();
                InitDbModel<Bank> initModel = mapper.readValue(file, new TypeReference<>() {
                });
                List<Bank> bankList = initModel.getData();
                bankRepository.saveAll(bankList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(String... args) throws Exception {
        initDataUser();
        implementInitDataBankInfo();
        implementInitDataMenuActionProvince();
        implementInitDataMenuActionDistrict();
        implementInitDataMenuActionWard();
        implementInitDataMenuActionAccomCategory();
        implementInitDataMenuActionFacilityCategory();
        implementInitDataMenuActionFacility();
        implementInitDataMenuActionTypeBed();
        implementInitDataMenuActionSurcharge();
        implementInitDataAccomPlace();
        implementInitDataMenuActionImageAccom();
        implementInitDataItemFeature();
    }
}
