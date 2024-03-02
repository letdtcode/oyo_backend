package com.mascara.oyo_booking_backend.dtos.request.booking;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 02/03/2024
 * Time      : 11:31 CH
 * Filename  : CancelBookingRequest
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CancelBookingRequest {
    @NotNull
    @NotBlank
    private String bookingCode;

    private String cancelReason;
}
