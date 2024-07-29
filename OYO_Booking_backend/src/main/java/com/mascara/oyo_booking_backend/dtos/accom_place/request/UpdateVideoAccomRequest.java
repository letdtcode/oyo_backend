package com.mascara.oyo_booking_backend.dtos.accom_place.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 04/07/2024
 * Time      : 6:27 CH
 * Filename  : UpdateVideoAccomRequest
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateVideoAccomRequest {
    @NotNull
    private String cldVideoId;
}
