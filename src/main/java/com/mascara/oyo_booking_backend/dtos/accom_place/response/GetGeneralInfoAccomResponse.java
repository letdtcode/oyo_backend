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
    private String nameAccom;
    private String description;
    private String guide;
    private Float acreage;
    private Double pricePerNight;
    private String checkInFrom;
    private String checkOutTo;
    private List<ItemSurchargeResponse> surchargeList;
    private Double discountPercent;
}


