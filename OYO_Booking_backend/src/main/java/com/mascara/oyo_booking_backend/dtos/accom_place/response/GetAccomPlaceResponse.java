package com.mascara.oyo_booking_backend.dtos.accom_place.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 29/10/2023
 * Time      : 5:12 CH
 * Filename  : GetAccomPlaceResponse
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAccomPlaceResponse {
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
    private Integer numKitchen;
    private Integer numView;
    private Float gradeRate;
    private Long numReview;
    private Double pricePerNight;
    private String guide;
    private String lastModifiedDate;
    private String status;
}
