package com.mascara.oyo_booking_backend.dtos.request.user;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 09/10/2023
 * Time      : 6:02 CH
 * Filename  : UserDTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long Id;
    @Length(min = 5)
    private String userName;
    private String password;
    @NotEmpty
    private String firstName;
    private String lastName;
    private Integer gender;
    private LocalDate dateOfBirth;
    private String mail;
    private String address;
    private String phone;
    private String avatar;
    private List<String> roleName;
}
