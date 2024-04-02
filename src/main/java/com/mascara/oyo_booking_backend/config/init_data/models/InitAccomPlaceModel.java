package com.mascara.oyo_booking_backend.config.init_data.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 02/04/2024
 * Time      : 3:39 CH
 * Filename  : InitAccomPlace
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InitAccomPlaceModel {
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

    private String cldVideoId;

    @NotNull
    private Float acreage;

    @NotNull
    @Min(value = 1, message = "Num people should not be less than 1")
    private Integer numPeople;

    @NotNull
    @Min(value = 0, message = "Num bath room should not be less than 0")
    private Integer numBathRoom;

    @NotNull
    @Min(value = 0, message = "Num bed room should not be less than 0")
    private Integer numBedRoom;

    @NotNull
    @Min(value = 0, message = "Num kitchen should not be less than 0")
    private Integer numKitchen;

    @NotNull
    @Min(value = 0, message = "Price per night should not be less than 0")
    private Double pricePerNight;

    @NotNull
    private List<String> facilityNameList;

    private String guide;
}
