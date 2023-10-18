package com.mascara.oyo_booking_backend.controllers.auth;

import com.mascara.oyo_booking_backend.dtos.request.auth.LoginRequest;
import com.mascara.oyo_booking_backend.dtos.request.auth.RegisterRequest;
import com.mascara.oyo_booking_backend.dtos.request.auth.TokenRefreshRequest;
import com.mascara.oyo_booking_backend.dtos.response.MessageResponse;
import com.mascara.oyo_booking_backend.dtos.response.auth.LoginResponse;
import com.mascara.oyo_booking_backend.dtos.response.auth.TokenRefreshResponse;
import com.mascara.oyo_booking_backend.entities.RefreshToken;
import com.mascara.oyo_booking_backend.entities.User;
import com.mascara.oyo_booking_backend.repositories.RefreshTokenRepository;
import com.mascara.oyo_booking_backend.repositories.UserRepository;
import com.mascara.oyo_booking_backend.securities.jwt.JwtUtils;
import com.mascara.oyo_booking_backend.securities.service.CustomUserDetails;
import com.mascara.oyo_booking_backend.services.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 13/10/2023
 * Time      : 4:01 CH
 * Filename  : AuthController
 */
@Tag(name = "AuthController", description = "Authentication APIs")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Operation(summary = "Sign in", description = "Api for Sign in")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = LoginResponse.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        CustomUserDetails userPrincipal = (CustomUserDetails) authentication.getPrincipal();
        String userMail = (userPrincipal.getEmail());
        String accessToken = jwtUtils.generateAccessJwtToken(userMail);
        String refreshToken = jwtUtils.generateRefreshJwtToken(userMail);

//        User user = userRepository.findByMail(userMail).orElseThrow(() -> new ResourceNotFoundException(AppContants.USER_NOT_FOUND));
//        RefreshToken tokenRefresh = refreshTokenRepository.findByUserMail(userMail).orElseThrow(() -> new ResourceNotFoundException(AppContants.USER_NOT_FOUND));

        Optional<RefreshToken> refreshTokenOptional = refreshTokenRepository.findByUserMail(userMail);
        if (refreshTokenOptional.isPresent()) {
            refreshTokenOptional.get().setRefreshToken(refreshToken);
            refreshTokenRepository.save(refreshTokenOptional.get());
        } else {
            User user = userRepository.findByMail(userMail).get();
            Instant expiredToken = jwtUtils.extractExpiration(refreshToken);
            RefreshToken refreshOfUser = RefreshToken.builder()
                    .refreshToken(refreshToken)
                    .user(user)
                    .refreshCount(0L)
                    .expiryDate(expiredToken)
                    .build();
            refreshTokenRepository.save(refreshOfUser);
        }

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());

        return ResponseEntity.ok(new LoginResponse(accessToken, refreshToken, userDetails.getUsername(), userDetails.getEmail(), roles));
    }

    @Operation(summary = "Sign up", description = "Api for Sign up")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = MessageResponse.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        if (userRepository.existsByMail(registerRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already exist!"));
        }
        String passwordEncoded = encoder.encode(registerRequest.getPassword());
        userService.createUser(registerRequest, passwordEncoded);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @Operation(summary = "Refresh token", description = "Api for Refresh token")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = TokenRefreshResponse.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @PostMapping("/refreshToken")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody TokenRefreshRequest tokenRefreshRequest) {
        return ResponseEntity.ok(userService.refreshJwtToken(tokenRefreshRequest));
    }
}
