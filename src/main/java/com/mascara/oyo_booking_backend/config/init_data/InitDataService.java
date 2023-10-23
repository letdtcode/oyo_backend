package com.mascara.oyo_booking_backend.config.init_data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mascara.oyo_booking_backend.config.init_data.models.InitDbDistrictModel;
import com.mascara.oyo_booking_backend.config.init_data.models.InitDbProvinceModel;
import com.mascara.oyo_booking_backend.config.init_data.models.InitDbWardModel;
import com.mascara.oyo_booking_backend.entities.District;
import com.mascara.oyo_booking_backend.entities.Province;
import com.mascara.oyo_booking_backend.entities.Role;
import com.mascara.oyo_booking_backend.entities.Ward;
import com.mascara.oyo_booking_backend.enums.RoleEnum;
import com.mascara.oyo_booking_backend.exceptions.ResourceNotFoundException;
import com.mascara.oyo_booking_backend.repositories.DistrictRepository;
import com.mascara.oyo_booking_backend.repositories.ProvinceRepository;
import com.mascara.oyo_booking_backend.repositories.RoleRepository;
import com.mascara.oyo_booking_backend.repositories.WardRepository;
import com.mascara.oyo_booking_backend.utils.AppContants;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

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
public class InitDataService implements CommandLineRunner {
    private final ProvinceRepository provinceRepository;
    private final DistrictRepository districtRepository;
    private final WardRepository wardRepository;
    private final RoleRepository roleRepository;

    public InitDataService(ProvinceRepository provinceRepository, DistrictRepository districtRepository, WardRepository wardRepository, RoleRepository roleRepository) {
        this.provinceRepository = provinceRepository;
        this.districtRepository = districtRepository;
        this.wardRepository = wardRepository;
        this.roleRepository = roleRepository;
    }

    public void initDataUser() {
        Optional<Role> roleAdmin = roleRepository.findByRoleName(RoleEnum.ROLE_ADMIN);
        if (!roleAdmin.isPresent()) {
            Role admin = Role.builder().roleName(RoleEnum.ROLE_ADMIN).build();
            roleRepository.save(admin);
        }

        Optional<Role> rolePartner = roleRepository.findByRoleName(RoleEnum.ROLE_PARTNER);
        if (!rolePartner.isPresent()) {
            Role partner = Role.builder().roleName(RoleEnum.ROLE_PARTNER).build();
            roleRepository.save(partner);
        }

        Optional<Role> roleClient = roleRepository.findByRoleName(RoleEnum.ROLE_CLIENT);
        if (!roleClient.isPresent()) {
            Role client = Role.builder().roleName(RoleEnum.ROLE_CLIENT).build();
            roleRepository.save(client);
        }
    }

    public void implementInitDataMenuActionDistrict() {
        List<District> checkList = districtRepository.checkExistData();
        try {
            if (checkList.isEmpty()) {
                File file = new File(
                        Objects.requireNonNull(this.getClass().getClassLoader().getResource("initDbDistrict.json")).getFile()
                );
                ObjectMapper mapper = new ObjectMapper();
                InitDbDistrictModel initModel = mapper.readValue(file, InitDbDistrictModel.class);
                List<District> districtList = initModel.getData();
                for (int i = 0; i < districtList.size(); i++) {
                    District district = districtList.get(i);
                    district.setProvince(provinceRepository.findByProvinceCode(district.getProvinceCode())
                            .orElseThrow(() -> new ResourceNotFoundException(AppContants.PROVINCE_NOT_FOUND)));
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
                        Objects.requireNonNull(this.getClass().getClassLoader().getResource("initDbProvince.json")).getFile()
                );

                ObjectMapper mapper = new ObjectMapper();
                InitDbProvinceModel initModel = mapper.readValue(file, InitDbProvinceModel.class);
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
                        Objects.requireNonNull(this.getClass().getClassLoader().getResource("initDbWard.json")).getFile()
                );

                ObjectMapper mapper = new ObjectMapper();
                InitDbWardModel initModel = mapper.readValue(file, InitDbWardModel.class);

                List<Ward> wardList = initModel.getData();
                for (int i = 0; i < wardList.size(); i++) {
                    Ward ward = wardList.get(i);
                    ward.setDistrict(districtRepository.findByDistrictCode(ward.getDistrictCode())
                            .orElseThrow(() -> new ResourceNotFoundException(AppContants.DISTRICT_NOT_FOUND)));
                    wardList.set(i, ward);
                }
                wardRepository.saveAll(wardList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(String... args) throws Exception {
        initDataUser();
        implementInitDataMenuActionProvince();
        implementInitDataMenuActionDistrict();
        implementInitDataMenuActionWard();
    }
}
