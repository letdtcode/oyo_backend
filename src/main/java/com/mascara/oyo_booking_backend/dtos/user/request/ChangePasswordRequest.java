package com.mascara.oyo_booking_backend.dtos.user.request;

import com.mascara.oyo_booking_backend.utils.validation.Password;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 24/10/2023
 * Time      : 3:14 SA
 * Filename  : ChangePasswordRequest
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordRequest {
    @NotNull
    @Email(message = "Invalid email")
    private String email;

    @NotNull
    private String oldPassword;

    @Password(message = "Password must be at least 8 characters long, " +
            "with at least 1 uppercase letter, 1 lowercase letter, 1 digit, and 1 special character")
    private String newPassword;
}
