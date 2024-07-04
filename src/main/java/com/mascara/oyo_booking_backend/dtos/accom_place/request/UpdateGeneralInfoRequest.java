package com.mascara.oyo_booking_backend.dtos.accom_place.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 07/12/2023
 * Time      : 1:22 CH
 * Filename  : UpdateTitleAccomRequest
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateGeneralInfoRequest {
    @NotNull
    @NotBlank
    private String accomName;

    @NotNull
    @NotBlank
    private String description;

    @NotNull
    private Float acreage;

    @NotNull
    @Min(value = 0, message = "Price per night should not be less than 0")
    private Double pricePerNight;

    @NotNull
    @JsonFormat(pattern = "HH:mm")
    private String checkInFrom;

    @NotNull
    @JsonFormat(pattern = "HH:mm")
    private String checkOutTo;

    @NotNull
    private List<ItemSurcharge> surchargeList;

    @NotNull
    private Double discountPercent;
}
