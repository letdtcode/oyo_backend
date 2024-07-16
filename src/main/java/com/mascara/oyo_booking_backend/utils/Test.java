//package com.mascara.oyo_booking_backend.utils;
//
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.mascara.oyo_booking_backend.config.init_data.models.InitDbModel;
//import com.mascara.oyo_booking_backend.entities.address.District;
//import com.mascara.oyo_booking_backend.entities.address.Ward;
//import com.mascara.oyo_booking_backend.entities.facility.Facility;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Objects;
//import java.util.Random;
//
///**
// * Created by: IntelliJ IDEA
// * User      : boyng
// * Date      : 16/07/2024
// * Time      : 10:31 CH
// * Filename  : Test
// */
//public class Test {
//    private Random random;
//
//    public Test() {
//        random = new Random();
//    }
//
//    public void exportDistrictAndWard(String provinceCode) {
//        File file = new File(
//                Objects.requireNonNull(this.getClass().getClassLoader().getResource("initData/json/initDbDistrict.json")).getFile()
//        );
//        ObjectMapper mapper = new ObjectMapper();
//        try {
//            InitDbModel<District> initModel = mapper.readValue(file, new TypeReference<>() {
//            });
//            List<District> districtList = initModel.getData();
//            List<District> districts = new LinkedList<>();
//            for (District item : districtList) {
//                if (item.getProvinceCode().equals(provinceCode)) {
//                    districts.add(item);
//                }
//            }
//
//            int indexDistrict = random.nextInt(districts.size());
//            District district = districts.get(indexDistrict);
//
////            Get ward
//            File file2 = new File(
//                    Objects.requireNonNull(this.getClass().getClassLoader().getResource("initData/json/initDbWard.json")).getFile()
//            );
//
//            InitDbModel<Ward> initModel2 = mapper.readValue(file2, new TypeReference<>() {
//            });
//            List<Ward> wardList = initModel2.getData();
//            List<Ward> wards = new LinkedList<>();
//            for (Ward item : wardList) {
//                if (item.getDistrictCode().equals(district.getDistrictCode())) {
//                    wards.add(item);
//                }
//            }
//
//            int indexWard = random.nextInt(wards.size());
//            Ward ward = wards.get(indexWard);
//            System.out.println("Province code: " + district.getProvinceCode());
//            System.out.println("District code: " + district.getDistrictCode());
//            System.out.println("Ward code: " + ward.getWardCode());
//        } catch (IOException ex) {
//            System.out.println(ex.getMessage());
//        }
//
//
//    }
//
//    public void export(String facilityCategoryCode) {
//        File file = new File(
//                Objects.requireNonNull(this.getClass().getClassLoader().getResource("initData/json/initDbFacility.json")).getFile()
//        );
//        ObjectMapper mapper = new ObjectMapper();
//
//        try {
//            InitDbModel<Facility> initModel = mapper.readValue(file, new TypeReference<>() {
//            });
//            List<Facility> facilityList = initModel.getData();
//            List<Facility> facilities = new LinkedList<>();
//            List<Facility> result = new LinkedList<>();
//            for (Facility item : facilityList) {
//                if (item.getFacilityCateCode().equals(facilityCategoryCode))
//                    facilities.add(item);
//            }
//
//            int index1 = random.nextInt(facilities.size());
//            int index2 = random.nextInt(facilities.size());
//            while (index1 == index2) {
//                index2 = random.nextInt(facilities.size());
//            }
//            Facility randomFacility1 = facilities.get(index1);
//            Facility randomFacility2 = facilities.get(index2);
//            System.out.println('"' + randomFacility1.getFacilityName() + '"' + ',');
//            System.out.println('"' + randomFacility2.getFacilityName() + '"' + ',');
//
//            if (facilityCategoryCode.equals("FACI_CATE_008")) {
//                int index3 = random.nextInt(facilities.size());
//                while (index3 == index1 || index3 == index2) {
//                    index3 = random.nextInt(facilities.size());
//                }
//                Facility randomFacility3 = facilities.get(index3);
//                System.out.println('"' + randomFacility3.getFacilityName() + '"' + ',');
//            }
//        } catch (IOException ex) {
//            System.out.println(ex.getMessage());
//        }
//    }
//
//    public static void main(String[] args) {
//        Test test = new Test();
////        test.export("FACI_CATE_001");
////        test.export("FACI_CATE_002");
////        test.export("FACI_CATE_003");
////        test.export("FACI_CATE_004");
////        test.export("FACI_CATE_005");
////        test.export("FACI_CATE_006");
////        test.export("FACI_CATE_007");
////        test.export("FACI_CATE_008");
////        test.export("FACI_CATE_009");
////        test.export("FACI_CATE_010");
//        test.exportDistrictAndWard("27");
//    }
//}
