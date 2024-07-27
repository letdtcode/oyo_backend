package com.mascara.oyo_booking_backend.dtos.booking.response;

import com.mascara.oyo_booking_backend.enums.order.PaymentMethodEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 27/07/2024
 * Time      : 5:10 CH
 * Filename  : ClientConfirmBookingResponse
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientConfirmBookingResponse {
    private String bookingCode;
    private PaymentMethodEnum paymentMethod;
    @Nullable
    private String bookingPaypalCheckoutLink;
}
