package com.mascara.oyo_booking_backend.dtos.response.type_bed;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 25/11/2023
 * Time      : 5:41 CH
 * Filename  : GetTypeBedResponse
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetTypeBedResponse {
    private Long id;
    private String typeBedName;
    private String typeBedCode;
    private String status;
}
