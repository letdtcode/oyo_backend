package com.mascara.oyo_booking_backend.dtos.recommender_system.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 01/07/2024
 * Time      : 6:47 CH
 * Filename  : RecommenderAccomPlaceResponse
 */
@Data
@Builder
public class RecommenderAccomPlaceResponse {
    private Long accomId;
    private String accomName;
    private String accomCateName;
    private String addressGeneral;
    private List<String> imageAccomsUrls;
    private Integer numView;
    private Float gradeRate;
    private Double pricePerNight;
    private Double similarity;
    private String status;
}
