package com.mascara.oyo_booking_backend.dtos.response.user;

import com.mascara.oyo_booking_backend.enums.UserStatusEnum;
import jakarta.persistence.*;
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
    private String userName;
    private String firstName;
    private String lastName;
    private Integer gender;
    private LocalDate dateOfBirth;
    private String mail;
    private String address;
    private String phone;
    private String avatarUrl;
    @Enumerated(EnumType.STRING)
    private UserStatusEnum status;
}
