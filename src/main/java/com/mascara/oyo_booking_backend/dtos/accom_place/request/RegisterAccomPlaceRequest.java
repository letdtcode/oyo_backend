package com.mascara.oyo_booking_backend.dtos.accom_place.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 25/10/2023
 * Time      : 5:09 CH
 * Filename  : AddAccommodationRequest
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterAccomPlaceRequest {
    @NotNull
    @NotBlank
    private String accomCateName;
}
