package com.mascara.oyo_booking_backend.services.user;

import com.mascara.oyo_booking_backend.dtos.request.user.CreateUserRequest;
import com.mascara.oyo_booking_backend.dtos.response.user.CreateUserResponse;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 09/10/2023
 * Time      : 6:01 CH
 * Filename  : IUserService
 */
public interface IUserService {
    CreateUserResponse createUser(CreateUserRequest request, String passwordEncode);
}
