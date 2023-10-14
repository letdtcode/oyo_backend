package com.mascara.oyo_booking_backend.dtos.response.user;

import com.mascara.oyo_booking_backend.entities.Role;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 13/10/2023
 * Time      : 9:43 CH
 * Filename  : CreateUserResponse
 */
@Data
public class CreateUserResponse {
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
