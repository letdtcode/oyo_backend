package com.mascara.oyo_booking_backend.dtos.accom_category.response;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 27/10/2023
 * Time      : 4:52 CH
 * Filename  : GetAllAccomCategoryResponse
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAccomCategoryResponse {
    private Long id;
    private String accomCateName;
    private String description;
    private String icon;
    private String status;
}
