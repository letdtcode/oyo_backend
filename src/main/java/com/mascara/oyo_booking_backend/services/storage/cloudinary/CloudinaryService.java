package com.mascara.oyo_booking_backend.services.storage.cloudinary;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 24/10/2023
 * Time      : 3:36 CH
 * Filename  : CloudinaryService
 */
public interface CloudinaryService {
    String store(MultipartFile file);
}
