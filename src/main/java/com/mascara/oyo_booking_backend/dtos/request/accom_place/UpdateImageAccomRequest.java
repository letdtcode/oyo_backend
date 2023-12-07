package com.mascara.oyo_booking_backend.dtos.request.accom_place;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 07/12/2023
 * Time      : 2:43 CH
 * Filename  : UpdateImageAccomRequest
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateImageAccomRequest {
    @NotNull
    private List<String> imageAccomUrls;
}
