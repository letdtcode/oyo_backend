package com.mascara.oyo_booking_backend.dtos.response.surcharge;

import com.mascara.oyo_booking_backend.entities.SurchargeOfAccom;
import com.mascara.oyo_booking_backend.enums.CommonStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.Set;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 07/12/2023
 * Time      : 8:56 CH
 * Filename  : SurchargeCategoryResponse
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetSurchargeCategoryResponse {
    private String surchargeCateName;
    private String surchargeCode;
    private String status;
}
