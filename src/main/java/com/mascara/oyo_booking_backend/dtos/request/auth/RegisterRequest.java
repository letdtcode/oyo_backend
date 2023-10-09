package com.mascara.oyo_booking_backend.dtos.request.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 01/10/2023
 * Time      : 4:13 CH
 * Filename  : RegisterRequest
 */
@Data
public class RegisterRequest {
    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String mail;

    @NotBlank
    private String password;

    @NotBlank
    private String address;

    @NotBlank
    private String phone;

    @NotBlank
    private Set<String> roles = new HashSet<>(Arrays.asList("Client"));
}
