package com.mascara.oyo_booking_backend.securities.auth;

import com.mascara.oyo_booking_backend.dtos.request.auth.LoginRequest;
import com.mascara.oyo_booking_backend.dtos.request.auth.RegisterRequest;
import com.mascara.oyo_booking_backend.dtos.request.auth.TokenRefreshRequest;
import com.mascara.oyo_booking_backend.dtos.request.user.CreateUserRequest;
import com.mascara.oyo_booking_backend.dtos.response.auth.LoginResponse;
import com.mascara.oyo_booking_backend.dtos.response.auth.RegisterResponse;
import com.mascara.oyo_booking_backend.dtos.response.auth.TokenRefreshResponse;
import com.mascara.oyo_booking_backend.dtos.response.user.CreateUserResponse;
import com.mascara.oyo_booking_backend.enums.RoleEnum;
import com.mascara.oyo_booking_backend.exceptions.ResourceExistException;
import com.mascara.oyo_booking_backend.repositories.UserRepository;
import com.mascara.oyo_booking_backend.services.user.IUserService;
import com.mascara.oyo_booking_backend.utils.AppContants;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 01/10/2023
 * Time      : 4:19 CH
 * Filename  : AuthenticationServiceImpl
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IUserService userService;

    @Autowired
    private ModelMapper mapper;

    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {
        if (userRepository.existsByMail(registerRequest.getMail())) {
            throw new ResourceExistException(AppContants.USER_EXIST);
        }
//        User user = User.builder().build();
//        user.setFirstName(request.getFirstName());
//        user.setLastName(request.getLastName());
//        user.setMail(request.getMail());
//        user.setRoleSet(request.getRoles());
        CreateUserRequest createUserRequest = CreateUserRequest.builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .mail(registerRequest.getMail())
                .roles(new HashSet<>(Arrays.asList(RoleEnum.ROLE_CLIENT)))
                .build();
        String passwordEncode = passwordEncoder.encode(registerRequest.getPassword());
        CreateUserResponse userResponse = userService.createUser(createUserRequest, passwordEncode);
        return mapper.map(userResponse, RegisterResponse.class);
    }

    @Override
    public LoginResponse authenticate(LoginRequest request) {
        return null;
    }

    @Override
    public TokenRefreshResponse refresh(TokenRefreshRequest request) {
        return null;
    }
}
