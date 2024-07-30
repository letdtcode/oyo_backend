package com.mascara.oyo_booking_backend.controllers.media;

import com.mascara.oyo_booking_backend.common.constant.MessageConstant;
import com.mascara.oyo_booking_backend.dtos.base.BaseResponse;
import com.mascara.oyo_booking_backend.common.exceptions.ResourceNotFoundException;
import com.mascara.oyo_booking_backend.external_modules.storage.cloudinary.CloudUploader;
import com.mascara.oyo_booking_backend.external_modules.storage.cloudinary.CloudinaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 04/12/2023
 * Time      : 1:32 SA
 * Filename  : MediaCloudinaryController
 */
@Tag(name = "Media API", description = "Media APIs")
@RestController
@RequestMapping("/api/v1/media/cloud")
@RequiredArgsConstructor
public class MediaCloudinaryController {
    @Autowired
    private CloudinaryService cloudinaryService;

    @Operation(summary = "Upload file hình ảnh lên cloudinary", description = "Upload file hình ảnh lên cloudinary")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BaseResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            throw new ResourceNotFoundException(MessageConstant.FILE_IS_NULL);
        }
        CloudUploader cloudUploader = cloudinaryService.store(file);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, cloudUploader));
    }

    @Operation(summary = "Upload file hình ảnh lên cloudinary", description = "Upload file hình ảnh lên cloudinary")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BaseResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PostMapping("/upload-multil")
    public ResponseEntity<?> uploadMultipleFile(@RequestParam("files") List<MultipartFile> files) {
        if (files.isEmpty()) {
            throw new ResourceNotFoundException(MessageConstant.FILE_IS_NULL);
        }
        List<CloudUploader> cloudUploaders = cloudinaryService.storeMultiple(files);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, cloudUploaders));
    }
}
