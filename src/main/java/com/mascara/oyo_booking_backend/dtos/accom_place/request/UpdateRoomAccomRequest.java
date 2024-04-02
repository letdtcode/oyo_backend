package com.mascara.oyo_booking_backend.dtos.accom_place.request;

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
 * Date      : 07/12/2023
 * Time      : 2:28 CH
 * Filename  : UpdateRoomAccomRequest
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRoomAccomRequest {
    @NotNull
    private List<String> typeBedCodes;

    @NotNull
    @Min(value = 0, message = "Num bath room should not be less than 0")
    private Integer numBathRoom;

    @NotNull
    @Min(value = 0, message = "Num kitchen should not be less than 0")
    private Integer numKitchen;

    @NotNull
    @NotBlank
    private String accomCateName;
}
