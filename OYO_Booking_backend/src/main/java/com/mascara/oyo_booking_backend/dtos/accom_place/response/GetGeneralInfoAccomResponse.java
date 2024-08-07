package com.mascara.oyo_booking_backend.dtos.accom_place.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 04/04/2024
 * Time      : 6:47 CH
 * Filename  : GetGeneralInfoAccomResponse
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetGeneralInfoAccomResponse {
    private String accomCateName;
    private String accomName;
    private String description;
    private Double pricePerNight;
    private Float acreage;
    private String checkInFrom;
    private String checkOutTo;
    private Double discountPercent;
    private List<ItemSurchargeResponse> surchargeList;
}


