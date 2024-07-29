package com.mascara.oyo_booking_backend.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 24/10/2023
 * Time      : 9:45 CH
 * Filename  : StorageProperties
 */
@Data
@ConfigurationProperties("storage")
public class StorageProperties {
    private String location;
}
