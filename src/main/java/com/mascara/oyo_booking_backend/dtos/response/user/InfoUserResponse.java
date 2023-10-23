package com.mascara.oyo_booking_backend.dtos.response.user;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 24/10/2023
 * Time      : 2:39 SA
 * Filename  : GetInfoUserResponse
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InfoUserResponse {
    private Long Id;
    private String userName;
    private String firstName;
    private String lastName;
    private Integer gender;
    private LocalDate dateOfBirth;
    private String mail;
    private String address;
    private String phone;
}
