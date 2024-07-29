package com.mascara.oyo_booking_backend.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 22/06/2024
 * Time      : 9:29 CH
 * Filename  : TestController
 */

@Tag(name = "Public AccomPlace Category Data", description = "Get Data Accom Category with Info")
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
@Slf4j
public class TestController {

    @PostMapping("/test-post")
    public ResponseEntity<?> getAllAccomCategoryInfo() {
        return ResponseEntity.ok("Haha");
    }
}
