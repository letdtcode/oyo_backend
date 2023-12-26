package com.mascara.oyo_booking_backend.external_modules.storage.cloudinary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 04/12/2023
 * Time      : 1:45 SA
 * Filename  : CloudUploader
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CloudUploader {
    private String imageUrl;
    private LocalDateTime createDate;
}
