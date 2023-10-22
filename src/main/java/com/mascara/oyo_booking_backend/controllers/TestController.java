package com.mascara.oyo_booking_backend.controllers;

import com.mascara.oyo_booking_backend.entities.Role;
import com.mascara.oyo_booking_backend.enums.RoleEnum;
import com.mascara.oyo_booking_backend.mail.EmailDetails;
import com.mascara.oyo_booking_backend.mail.service.EmailServiceImpl;
import com.mascara.oyo_booking_backend.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 17/10/2023
 * Time      : 2:39 SA
 * Filename  : TestController
 */
//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/test")
public class TestController {
    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('CLIENT') or hasRole('PARTNER') or hasRole('ADMIN')")
    public String userAccess() {
        return "User Content.";
    }

    @GetMapping("/partner")
    @PreAuthorize("hasRole('PARTNER')")
    public String moderatorAccess() {
        return "Moderator Board.";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "Admin Board.";
    }

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private EmailServiceImpl emailService;

    @GetMapping("/add")
    public ResponseEntity<?> addRole() {
        Role role = Role.builder().roleName(RoleEnum.ROLE_PARTNER).build();
        return ResponseEntity.ok(roleRepository.save(role));
    }

    @GetMapping("/email")
    public ResponseEntity<?> testMail(@RequestBody EmailDetails emailDetails) {
        return ResponseEntity.ok(emailService.sendSimpleMessage(emailDetails));
    }
}
