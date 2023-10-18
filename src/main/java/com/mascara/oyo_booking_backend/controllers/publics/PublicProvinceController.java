package com.mascara.oyo_booking_backend.controllers.publics;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 17/10/2023
 * Time      : 10:03 CH
 * Filename  : PublicProvinceController
 */
@Tag(name = "Public Check Mail", description = "Authentication APIs")
@RestController
@RequestMapping("/api/v1/public/user")
@RequiredArgsConstructor
public class PublicProvinceController {
}
