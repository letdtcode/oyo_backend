package com.mascara.oyo_booking_backend.dtos.response.auth;

import com.mascara.oyo_booking_backend.entities.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 13/10/2023
 * Time      : 4:09 CH
 * Filename  : RegisterResponse
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterResponse {
    private UUID id;

    private String userName;

    private String password;

    private String firstName;

    private String lastName;

    private Integer gender;

    private LocalDate dateOfBirth;

    private String mail;

    private String address;

    private String phone;

    private Set<Role> roleSet;
}
