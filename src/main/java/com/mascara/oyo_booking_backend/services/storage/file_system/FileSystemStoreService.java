package com.mascara.oyo_booking_backend.services.storage.file_system;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 24/10/2023
 * Time      : 3:37 CH
 * Filename  : FileSystemStoreService
 */
public interface FileSystemStoreService {
    String getStorageFilename(MultipartFile file, String id);

    void store(MultipartFile file, String storeFilename);

    Resource loadAsResource(String fileName);

    Path load(String fileName);

    void delete(String storeFileName) throws IOException;

    void init();
}
