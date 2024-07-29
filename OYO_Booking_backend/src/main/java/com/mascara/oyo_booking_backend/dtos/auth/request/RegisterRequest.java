package com.mascara.oyo_booking_backend.dtos.auth.request;

import com.mascara.oyo_booking_backend.utils.validation.Password;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 01/10/2023
 * Time      : 4:13 CH
 * Filename  : RegisterRequest
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotBlank
    @Size(max = 20)
    private String firstName;

    @NotBlank
    @Size(max = 20)
    private String lastName;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotEmpty
    private Set<String> role;

    @Password(message = "Password must be at least 8 characters long, " +
            "with at least 1 uppercase letter, 1 lowercase letter, 1 digit, and 1 special character")
    private String password;
}
