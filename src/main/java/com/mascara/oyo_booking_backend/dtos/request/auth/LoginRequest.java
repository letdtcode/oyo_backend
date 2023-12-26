package com.mascara.oyo_booking_backend.dtos.request.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 01/10/2023
 * Time      : 4:13 CH
 * Filename  : LoginRequest
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
