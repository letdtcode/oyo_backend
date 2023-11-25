package com.mascara.oyo_booking_backend.dtos.request.facility;

import com.mascara.oyo_booking_backend.entities.AccomPlace;
import com.mascara.oyo_booking_backend.entities.FacilityCategories;
import com.mascara.oyo_booking_backend.enums.CommonStatusEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 25/11/2023
 * Time      : 6:56 CH
 * Filename  : AddFacilityRequest
 */
@Data
@AllArgsConstructor
public class AddFacilityRequest {
    @NotNull
    @NotBlank
    private String facilityName;

    @NotNull
    @NotBlank
    private String facilityCateCode;

    @NotNull
    @NotBlank
    private String imageUrl;

    @NotNull
    @NotBlank
    @Pattern(regexp = "(?i)Enable|Disable", message = "Status must be 'Enable' or 'Disable'")
    private String status;
}
