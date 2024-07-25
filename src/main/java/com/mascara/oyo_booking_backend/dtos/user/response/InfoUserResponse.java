package com.mascara.oyo_booking_backend.dtos.user.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mascara.oyo_booking_backend.enums.user.UserStatusEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
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
    private Long userId;
    private String userName;
    private String firstName;
    private String lastName;
    private Integer gender;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateOfBirth;
    private String mail;
    private String address;
    private String phone;
    private String avatarUrl;
    @Enumerated(EnumType.STRING)
    private UserStatusEnum status;
}
