package com.mascara.oyo_booking_backend.dtos.booking.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 29/11/2023
 * Time      : 1:57 SA
 * Filename  : CheckBookingRequest
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckBookingRequest {

    @NotNull
    @Positive
    private Long accomId;

    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate checkIn;

    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate checkOut;

    @NotNull
    @Min(value = 0,message = "numAdult must greater than 0")
    private Integer numAdult;
}
