package com.mascara.oyo_booking_backend.securities.auth;

import com.mascara.oyo_booking_backend.securities.CustomUserDetailsService;
import com.mascara.oyo_booking_backend.securities.jwt.JwtServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 01/10/2023
 * Time      : 4:19 CH
 * Filename  : AuthenticationServiceImpl
 */
public class AuthenticationServiceImpl implements IAuthenticationService {
    @Autowired
    private JwtServiceImpl jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IRoleService roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse register(@RequestBody RegisterRequest request) {
        if (userRepository.existsByMail(request.getMail())) {
            throw new ResourceExistException(AppConstant.USER_EXIST);
        }
        UserDTO user = new UserDTO();
        Set<RoleDTO> roles = new HashSet<>();
        Cart cart = new Cart();
        AuthResponse authResponse = new AuthResponse();

        for (String role : request.getRoles()) {
            roles.add(roleService.findByRoleName(role));
        }
        BeanUtils.copyProperties(request, user, "password");
        String passwordEncode = passwordEncoder.encode(request.getPassword());

        user = userService.createUser(user, passwordEncode, roles);

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(user.getMail());

        String access_token = jwtService.generateToken(userDetails);
        String refresh_token = jwtService.generateRefreshToken(userDetails);

        authResponse.setAccessToken(access_token);
        authResponse.setRefreshToken(refresh_token);
        authResponse.setRoles(request.getRoles());
        authResponse.setUserId(user.getId());
        return authResponse;
    }

    @Override
    public AuthResponse authenticate(LoginRequest request) {
        AuthResponse authResponse = new AuthResponse();
        String email = request.getEmail();
        String password = request.getPassword();
        UsernamePasswordAuthenticationToken authReq
                = new UsernamePasswordAuthenticationToken(email, password);
        Authentication authentication = authenticationManager.authenticate(authReq);
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
        UserDTO user = userService.findByMail(request.getEmail()); //

        String access_token = jwtService.generateToken(userDetails);
        String refresh_token = jwtService.generateRefreshToken(userDetails);

        authResponse.setAccessToken(access_token);
        authResponse.setRefreshToken(refresh_token);
        authResponse.setRoles(getRoleUser(access_token));
        authResponse.setUserId(user.getId());
        return authResponse;
    }

    @Override
    public AuthResponse refresh(TokenRefreshRequest request) {
        AuthResponse authResponse = new AuthResponse();
        String refresh_token = request.getTokenRefresh();
        String access_token = null;

        //Mail
        String mail = jwtService.extractUsername(refresh_token);
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(mail);

        access_token = jwtService.generateToken(userDetails);
        refresh_token = jwtService.generateRefreshToken(userDetails);

        authResponse.setAccessToken(access_token);
        authResponse.setRefreshToken(refresh_token);
        authResponse.setRoles(getRoleUser(access_token));
        return authResponse;
    }

    public Set<String> getRoleUser(String token) {
        Set<String> roles = new HashSet<>();
        String roleUser = jwtService.extractRoles(token);
        for (String role : roleUser.substring(1, roleUser.length() - 1).split(", ")) {
            roles.add(role);
        }
        return roles;
    }
}
