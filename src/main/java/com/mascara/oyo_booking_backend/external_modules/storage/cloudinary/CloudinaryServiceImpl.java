package com.mascara.oyo_booking_backend.external_modules.storage.cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.mascara.oyo_booking_backend.common.exceptions.StorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 24/10/2023
 * Time      : 3:36 CH
 * Filename  : CloudinaryServiceImpl
 */
@Service
public class CloudinaryServiceImpl implements CloudinaryService {
    @Autowired
    private Cloudinary cloudinary;

    @Override
    public CloudUploader store(MultipartFile file) {
        try {
            Map r = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("resource_type", "auto",
                    "folder", "oyo_booking"));
            String imgUrl = (String) r.get("secure_url");
            String createdDateString = (String) r.get("created_at");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
            LocalDateTime createAt = LocalDateTime.parse(createdDateString, formatter);
            return CloudUploader.builder()
                    .imageUrl(imgUrl)
                    .createDate(createAt)
                    .build();
        } catch (IOException exception) {
            throw new StorageException("Failed to store file: ", exception);
        }
    }

    @Override
    public List<CloudUploader> storeMultiple(List<MultipartFile> files) {
        List<CloudUploader> cloudUploaders = new ArrayList<>();
        try {
            for (MultipartFile file : files) {
                Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(),
                        ObjectUtils.asMap("resource_type", "auto", "folder", "oyo_booking"));
                String imageUrl = (String) uploadResult.get("secure_url");
                String createdDateString = (String) uploadResult.get("created_at");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
                LocalDateTime createAt = LocalDateTime.parse(createdDateString, formatter);
                CloudUploader cloudUploader = CloudUploader.builder()
                        .imageUrl(imageUrl)
                        .createDate(createAt)
                        .build();
                cloudUploaders.add(cloudUploader);
            }
        } catch (IOException exception) {
            throw new StorageException("Failed to store files: ", exception);
        }
        return cloudUploaders;
    }
}
