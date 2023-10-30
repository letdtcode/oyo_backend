package com.mascara.oyo_booking_backend.dtos.response.accommodation;

import com.mascara.oyo_booking_backend.entities.*;
import com.mascara.oyo_booking_backend.enums.CommonStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.math.BigDecimal;
import java.util.Set;

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
    private Float gradeRate;
    private Long numReview;
    private Long userId;
    private String provinceCode;
    private String districtCode;
    private String wardCode;
    private Set<ImageAccom> imageAccoms;
    private Float acreage;
    private Integer numPeople;
    private Integer numBathRoom;
    private Integer numBed;
    private BigDecimal pricePerNight;
    private Set<Review> reviewSet;
    private Set<Facility> facilitySet;
}
