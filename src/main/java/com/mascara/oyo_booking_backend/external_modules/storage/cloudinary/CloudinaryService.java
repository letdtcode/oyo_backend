package com.mascara.oyo_booking_backend.external_modules.storage.cloudinary;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 24/10/2023
 * Time      : 3:36 CH
 * Filename  : CloudinaryService
 */
public interface CloudinaryService {
    CloudUploader store(MultipartFile file);

    List<CloudUploader> storeMultiple(List<MultipartFile> files);
}
