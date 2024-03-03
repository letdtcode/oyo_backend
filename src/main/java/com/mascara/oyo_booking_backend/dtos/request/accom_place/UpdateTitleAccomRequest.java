package com.mascara.oyo_booking_backend.dtos.request.accom_place;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class UpdateTitleAccomRequest {
    @NotNull
    @NotBlank
    private String nameAccom;

    @NotNull
    @NotBlank
    private String description;

    @NotNull
    @NotBlank
    private String guide;
}
