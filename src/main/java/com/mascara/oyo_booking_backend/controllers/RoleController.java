//package com.mascara.oyo_booking_backend.controllers;
//
//import com.mascara.oyo_booking_backend.entities.Role;
//import com.mascara.oyo_booking_backend.enums.RoleEnum;
//import com.mascara.oyo_booking_backend.repositories.RoleRepository;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
///**
// * Created by: IntelliJ IDEA
// * User      : boyng
// * Date      : 17/10/2023
// * Time      : 2:54 SA
// * Filename  : RoleController
// */
//@CrossOrigin(origins = "*", maxAge = 3600)
//@RestController
//@RequestMapping("/api/v1/test")
//public class RoleController {
//    RoleRepository roleRepository;
//
//    @PostMapping("/create")
//    public ResponseEntity<?> createRole() {
//        Role role = Role.builder().roleName(RoleEnum.ROLE_CLIENT).build();
//        return ResponseEntity.ok(roleRepository.save(role));
//    }
//}
