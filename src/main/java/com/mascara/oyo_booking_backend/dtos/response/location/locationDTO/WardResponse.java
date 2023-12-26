package com.mascara.oyo_booking_backend.dtos.response.location.locationDTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mascara.oyo_booking_backend.entities.District;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 24/11/2023
 * Time      : 11:28 CH
 * Filename  : WardResponse
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WardResponse {
    private String wardCode;
    private String wardName;
    private String districtCode;
}
