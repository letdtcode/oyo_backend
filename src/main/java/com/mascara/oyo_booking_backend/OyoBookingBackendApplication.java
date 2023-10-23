package com.mascara.oyo_booking_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity
@SpringBootApplication
public class OyoBookingBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(OyoBookingBackendApplication.class, args);
    }

}
