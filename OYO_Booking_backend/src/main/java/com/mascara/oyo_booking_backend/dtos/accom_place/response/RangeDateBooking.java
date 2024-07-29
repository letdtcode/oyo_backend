package com.mascara.oyo_booking_backend.dtos.accom_place.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 21/05/2024
 * Time      : 11:17 CH
 * Filename  : RangeDateBooking
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RangeDateBooking {

    private String nameCustomer;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateStart;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateEnd;
}
