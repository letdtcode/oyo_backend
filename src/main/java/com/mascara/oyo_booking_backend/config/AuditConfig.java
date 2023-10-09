package com.mascara.oyo_booking_backend.config;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 09/10/2023
 * Time      : 3:29 CH
 * Filename  : AuditConfig
 */
public class AuditConfig implements AuditorAware {
    @Override
    public Optional getCurrentAuditor() {
        return Optional.empty();
    }
}
