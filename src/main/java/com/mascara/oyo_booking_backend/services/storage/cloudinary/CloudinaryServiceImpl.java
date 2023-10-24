package com.mascara.oyo_booking_backend.services.storage.cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.mascara.oyo_booking_backend.exceptions.StorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    public String store(MultipartFile file) {
        try {
            Map r = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("resource_type", "auto",
                    "folder", "oyo_booking"));
            String imgUrl = (String) r.get("secure_url");
            return imgUrl;
        } catch (IOException exception) {
            throw new StorageException("Failed to store file: ", exception);
        }
    }
}
