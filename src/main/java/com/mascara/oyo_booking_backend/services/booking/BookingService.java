package com.mascara.oyo_booking_backend.services.booking;

import com.mascara.oyo_booking_backend.dtos.request.booking.BookingRequest;
import com.mascara.oyo_booking_backend.dtos.response.general.MessageResponse;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 22/10/2023
 * Time      : 7:44 CH
 * Filename  : BookingService
 */
public interface BookingService {
    @Transactional
    String bookingAccomPlace(BookingRequest request);
}
