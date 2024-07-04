package com.mascara.oyo_booking_backend.dtos.accom_place.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 04/07/2024
 * Time      : 6:26 CH
 * Filename  : UpdateImageAccomRequest
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateImageAccomRequest {
    @NotNull
    private List<String> imageAccomUrls;
}
