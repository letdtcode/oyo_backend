package com.mascara.oyo_booking_backend.dtos.response.location;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mascara.oyo_booking_backend.entities.AccomPlace;
import com.mascara.oyo_booking_backend.entities.District;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.Set;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 27/11/2023
 * Time      : 6:53 CH
 * Filename  : GetProvinceResponse
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetProvinceResponse {
    private String provinceName;
    private String thumbnail;
    private String provinceCode;
    private String divisionType;
    private String slugs;
    private Long numBooking;
}
