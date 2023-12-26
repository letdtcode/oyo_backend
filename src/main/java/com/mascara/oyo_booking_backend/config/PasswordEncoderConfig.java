package com.mascara.oyo_booking_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 17/12/2023
 * Time      : 8:33 CH
 * Filename  : PasswordEncoderConfig
 */

@Configuration
public class PasswordEncoderConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
