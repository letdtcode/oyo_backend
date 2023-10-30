package com.mascara.oyo_booking_backend.dtos.request.accom_category;

import com.mascara.oyo_booking_backend.enums.CommonStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 25/10/2023
 * Time      : 4:40 CH
 * Filename  : UpdateAccomCategoryRequest
 */
@Data
@AllArgsConstructor
public class UpdateAccomCategoryRequest {
    private String accomCateName;
    private String description;
    private CommonStatusEnum status;
}
