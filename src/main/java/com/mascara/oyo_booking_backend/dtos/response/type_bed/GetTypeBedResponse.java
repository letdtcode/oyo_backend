package com.mascara.oyo_booking_backend.dtos.response.type_bed;

import com.mascara.oyo_booking_backend.entities.BedRoom;
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
