package com.mascara.oyo_booking_backend.config.payment.paypal;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 28/06/2024
 * Time      : 9:08 CH
 * Filename  : PaypalConfig
 */
@Data
@Configuration
@RequiredArgsConstructor
public class PaypalConfig {

    @Value("${app.payment.paypal.baseUrl}")
    private String baseUrl;

    @Value("${app.payment.paypal.clientId}")
    private String clientId;

    @Value("${app.payment.paypal.secret}")
    private String secret;
}
