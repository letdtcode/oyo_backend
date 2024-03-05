package com.mascara.oyo_booking_backend.dtos.response.accommodation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mascara.oyo_booking_backend.dtos.response.facility_category.GetFacilityCategorWithFacilityListResponse;
import com.mascara.oyo_booking_backend.dtos.response.surcharge.GetSurchargeOfAccomResponse;
import com.mascara.oyo_booking_backend.dtos.response.type_bed.GetTypeBedResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 05/12/2023
 * Time      : 5:47 CH
 * Filename  : GetAccomPlaceDetailResponse
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAccomPlaceDetailResponse {
    private Long Id;
    private String accomName;
    private String description;
    private String addressDetail;
    private String addressGeneral;
    private String cldVideoId;
    private Float acreage;
    private Integer numPeople;
    private Integer numBathRoom;
    private Integer numBedRoom;
    private Integer numKitchen;
    private Integer numView;
    private Float gradeRate;
    private Long numReview;
    private Double pricePerNight;
    private Double discount;
    private String guide;
    private String cancellationPolicy;
    private Integer cancellationFeeRate;
    private List<String> imageAccomsUrls;
    private Long userId;

    //    Addtion fields
    private String nameHost;
    private String accomCateName;
    private List<GetTypeBedResponse> bedRooms;
    private List<GetFacilityCategorWithFacilityListResponse> facilityCategoryList;
    private List<GetSurchargeOfAccomResponse> surchargeList;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private List<LocalDate> bookedDates;
    private String status;
}
