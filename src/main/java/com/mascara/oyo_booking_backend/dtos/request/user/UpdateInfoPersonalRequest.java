package com.mascara.oyo_booking_backend.dtos.request.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
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
    private String userName;
    private String firstName;
    private String lastName;
    private Integer gender;

    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateOfBirth;
    private String address;
    private String phone;
}
