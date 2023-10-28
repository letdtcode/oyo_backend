package com.mascara.oyo_booking_backend.dtos.request.accommodation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 25/10/2023
 * Time      : 5:09 CH
 * Filename  : AddAccommodationRequest
 */
@Data
@AllArgsConstructor
public class AddAccommodationRequest {
    @NotBlank
    @NotNull
    private String accomName;

    @NotBlank
    @NotNull
    private String description;

    @NotBlank
    @NotNull
    private String numHouseAndStreetName;

    @NotNull
    @NotBlank
    private String accomCateName;

    @NotNull
    @NotBlank
    private String provinceCode;

    @NotNull
    @NotBlank
    private String districtCode;

    @NotNull
    @NotBlank
    private String wardCode;

    @NotNull
    private Float acreage;

    @NotNull
    @Size(min = 1, message = "Num people should not be less than 1")
    private Integer numPeople;

    @NotNull
    @Size(min = 0, message = "Num bath room should not be less than 0")
    private Integer numBathRoom;

    @NotNull
    @Size(min = 0, message = "Num bed should not be less than 0")
    private Integer numBed;

    @NotNull
    @Size(message = "Price per night should not be less than 0")
    private BigDecimal pricePerNight;

    @NotNull
    private List<String> facilityNameList;
}
