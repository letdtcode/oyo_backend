package com.mascara.oyo_booking_backend.services.user;

import com.mascara.oyo_booking_backend.dtos.request.user.UserDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 09/10/2023
 * Time      : 6:01 CH
 * Filename  : IUserService
 */
public interface IUserService {
    UserDTO findByMail(String email);

    UserDTO createUser(UserDTO userDTO, String passwordEncode, Set role);

    List<UserDTO> getAllUser();

    UserDTO findUserById(Long id);

    UserDTO updateUser(UserDTO userDTO, Long id);

    UserDTO updateImageProfile(MultipartFile file, Long id);

    UserDTO changePassword(String mail, String passwordNew);
}
