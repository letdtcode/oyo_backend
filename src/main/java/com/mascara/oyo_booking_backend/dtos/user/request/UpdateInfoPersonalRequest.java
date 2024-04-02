package com.mascara.oyo_booking_backend.dtos.user.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 24/10/2023
 * Time      : 3:00 SA
 * Filename  : UpdateInfoPersonalRequest
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateInfoPersonalRequest {
    @NotNull
    @NotBlank
    private String userName;
    @NotNull
    @NotBlank
    private String firstName;
    @NotNull
    @NotBlank
    private String lastName;

    @NotNull
    @Min(0)
    @Max(2)
    private Integer gender;

    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateOfBirth;

    @NotNull
    @NotBlank
    private String address;

    @NotNull
    @NotBlank
    private String phone;
}
