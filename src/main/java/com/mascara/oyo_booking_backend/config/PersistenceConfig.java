package com.mascara.oyo_booking_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Optional;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 09/10/2023
 * Time      : 3:29 CH
 * Filename  : AuditConfig
 */
@Configuration
@EnableTransactionManagement
public class PersistenceConfig {

    @Bean
    AuditorAware<String> auditorProvider() {
        return new AuditorAwareImpl();
    }

    public class AuditorAwareImpl implements AuditorAware<String> {

        @Override
        public Optional<String> getCurrentAuditor() {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                return Optional.of("Anonymous");
            }
            return Optional.of(authentication.getName());
        }
    }
}
