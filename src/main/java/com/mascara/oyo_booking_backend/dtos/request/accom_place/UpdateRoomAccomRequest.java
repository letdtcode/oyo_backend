package com.mascara.oyo_booking_backend.dtos.request.accom_place;

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
    private Integer numBathRoom;

    @NotNull
    private Integer numBedRoom;

    @NotNull
    private Integer numKitchen;

    @NotNull
    @NotBlank
    private String accomCateName;
}
