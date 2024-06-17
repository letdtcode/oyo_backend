package com.mascara.oyo_booking_backend.mapper.helper;

import com.mascara.oyo_booking_backend.dtos.facility.response.GetFacilityResponse;
import com.mascara.oyo_booking_backend.dtos.facility_category.response.GetFacilityCategorWithFacilityListResponse;
import com.mascara.oyo_booking_backend.entities.facility.Facility;
import com.mascara.oyo_booking_backend.entities.facility.FacilityCategories;
import com.mascara.oyo_booking_backend.repositories.FacilityCategoriesRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 17/06/2024
 * Time      : 9:41 CH
 * Filename  : FacilityHelperMapper
 */
@Component
@RequiredArgsConstructor
public class FacilityHelperMapper {
    private final FacilityCategoriesRepository facilityCategoriesRepository;

    //    Convert Set Facility
    @Named("setFacilityToFacilityCateDetails")
    public List<GetFacilityCategorWithFacilityListResponse> setFacilityToFacilityCateDetails(Set<Facility> facilitySet) {
        if (facilitySet != null) {
            List<GetFacilityResponse> facilityList = new LinkedList<>();
            List<GetFacilityCategorWithFacilityListResponse> facilityCategoryList = new LinkedList();
            for (Facility facility : facilitySet) {
                GetFacilityResponse facilityResponse = facilityToGetFacilityResponse(facility);
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
                    GetFacilityCategorWithFacilityListResponse faciCateResponse =
                            faciCateToGetFacilityCategorWithFacilityListResponse(facilityCategories);
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
    }

    @Named("facilityCateCodeToFacilityCateName")
    public String facilityCateCodeToFacilityCateName(String facilityCateCode) {
        if (facilityCateCode != null) {
            FacilityCategories facilityCategories = facilityCategoriesRepository.findByFaciCateCode(facilityCateCode).get();
            return facilityCategories.getFaciCateName();
        }
        return null;
    }

    ;

    private GetFacilityResponse facilityToGetFacilityResponse(Facility facility) {
        return GetFacilityResponse.builder()
                .id(facility.getId())
                .facilityCode(facility.getFacilityCode())
                .facilityName(facility.getFacilityName())
                .facilityCateCode(facility.getFacilityCateCode())
                .facilityCateName(facility.getFacilityName())
                .imageUrl(facility.getImageUrl())
                .status(facility.getStatus().toString())
                .build();
    }

    private GetFacilityCategorWithFacilityListResponse faciCateToGetFacilityCategorWithFacilityListResponse(FacilityCategories facilityCategories) {
        return GetFacilityCategorWithFacilityListResponse.builder()
                .faciCateName(facilityCategories.getFaciCateName())
                .faciCateCode(facilityCategories.getFaciCateCode())
                .description(facilityCategories.getDescription())
                .status(facilityCategories.getStatus().toString())
                .build();
    }
}
