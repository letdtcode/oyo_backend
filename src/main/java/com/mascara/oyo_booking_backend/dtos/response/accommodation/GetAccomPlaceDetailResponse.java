package com.mascara.oyo_booking_backend.dtos.response.accommodation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mascara.oyo_booking_backend.dtos.response.facility.GetFacilityCategoryResponse;
import com.mascara.oyo_booking_backend.dtos.response.surcharge.GetSurchargeOfAccomResponse;
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
    private Long userId;
    private String nameHost;
    private String accomCateName;
    private String addressGeneral;
    private List<String> imageAccomsUrls;
    private Float acreage;
    private Integer numPeople;
    private Integer numBathRoom;
    private Integer numBedRoom;
    private Integer numView;
    private Float gradeRate;
    private Long numReview;
    private List<String> bedRooms;
    private Double pricePerNight;
    private List<GetFacilityCategoryResponse> facilityCategoryList;
    private List<GetSurchargeOfAccomResponse> surchargeList;
    private String lastModifiedDate;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private List<LocalDate> bookedDates;
    private String status;
}
