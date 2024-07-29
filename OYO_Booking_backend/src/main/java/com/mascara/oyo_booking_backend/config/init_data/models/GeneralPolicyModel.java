package com.mascara.oyo_booking_backend.config.init_data.models;

import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 11/04/2024
 * Time      : 3:37 CH
 * Filename  : GeneralPolicyModel
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeneralPolicyModel {
    @NotNull
    private Boolean allowEvent;

    @NotNull
    private Boolean allowPet;

    @NotNull
    private Boolean allowSmoking;
}