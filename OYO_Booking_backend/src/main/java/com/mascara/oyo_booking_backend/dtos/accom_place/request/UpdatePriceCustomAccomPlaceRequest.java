package com.mascara.oyo_booking_backend.dtos.accom_place.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 18/05/2024
 * Time      : 1:53 CH
 * Filename  : UpdateDiscountAccomPlaceRequest
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePriceCustomAccomPlaceRequest {
    @NotNull
    private Long accomId;

    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateApply;

    @NotNull
    private Double priceApply;
}
