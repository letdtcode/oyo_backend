package com.mascara.oyo_booking_backend.dtos.response.surcharge;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 02/12/2023
 * Time      : 3:02 SA
 * Filename  : GetSurchargeOfAccom
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetSurchargeOfAccomResponse {
    private Double cost;
    private String surchargeName;
}
