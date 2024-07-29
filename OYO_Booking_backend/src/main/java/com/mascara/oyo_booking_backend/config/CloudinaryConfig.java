package com.mascara.oyo_booking_backend.config;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 24/10/2023
 * Time      : 9:55 CH
 * Filename  : CloudinaryConfig
 */
@Configuration
public class CloudinaryConfig {

    @Value("${cloudinary.name}")
    private String CLOUD_NAME;

    @Value("${cloudinary.api_key}")
    private String API_KEY;

    @Value("${cloudinary.api_secret}")
    private String API_SECRET;

    @Value("${cloudinary.secure_mode}")
    private boolean SECURE_MODE;

    @Bean
    public Cloudinary cloudinary() {
        Map config = new HashMap();
        config.put("cloud_name", CLOUD_NAME);
        config.put("api_key", API_KEY);
        config.put("api_secret", API_SECRET);
        config.put("secure", SECURE_MODE);
        return new Cloudinary(config);
    }
}
