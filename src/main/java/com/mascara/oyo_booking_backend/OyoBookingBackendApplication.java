package com.mascara.oyo_booking_backend;

import com.mascara.oyo_booking_backend.config.StorageProperties;
import com.mascara.oyo_booking_backend.external_modules.storage.file_system.FileSystemStoreService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

@EnableConfigurationProperties(StorageProperties.class)
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
@EnableWebSecurity
@SpringBootApplication
public class OyoBookingBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(OyoBookingBackendApplication.class, args);
    }

    @Bean
    CommandLineRunner init(FileSystemStoreService storageService) {
        return (args -> {
            storageService.init();
        });
    }

    @Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }
}
