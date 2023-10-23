package com.mascara.oyo_booking_backend.controllers.auth;

import com.mascara.oyo_booking_backend.dtos.request.auth.LoginRequest;
import com.mascara.oyo_booking_backend.dtos.request.auth.RegisterRequest;
import com.mascara.oyo_booking_backend.dtos.request.auth.TokenRefreshRequest;
import com.mascara.oyo_booking_backend.dtos.response.general.MessageResponse;
import com.mascara.oyo_booking_backend.dtos.response.auth.LoginResponse;
import com.mascara.oyo_booking_backend.dtos.response.auth.TokenRefreshResponse;
import com.mascara.oyo_booking_backend.dtos.response.user.InfoUserResponse;
import com.mascara.oyo_booking_backend.entities.RefreshToken;
import com.mascara.oyo_booking_backend.entities.User;
import com.mascara.oyo_booking_backend.enums.UserStatusEnum;
import com.mascara.oyo_booking_backend.exceptions.ResourceNotFoundException;
import com.mascara.oyo_booking_backend.repositories.RefreshTokenRepository;
import com.mascara.oyo_booking_backend.repositories.UserRepository;
import com.mascara.oyo_booking_backend.securities.jwt.JwtUtils;
import com.mascara.oyo_booking_backend.securities.service.CustomUserDetails;
import com.mascara.oyo_booking_backend.services.mail_verify_token.VerifyTokenService;
import com.mascara.oyo_booking_backend.services.user.UserService;
import com.mascara.oyo_booking_backend.utils.AppContants;
import freemarker.template.TemplateException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
//@CrossOrigin(origins = "http://localhost:3000")
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

    @Autowired
    private VerifyTokenService verifyTokenService;

    @Autowired
    private ModelMapper mapper;

    @Operation(summary = "Sign in", description = "Api for Sign in")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = LoginResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        CustomUserDetails userPrincipal = (CustomUserDetails) authentication.getPrincipal();
        String userMail = (userPrincipal.getEmail());

        User user = userRepository.findByMail(userMail).orElseThrow(() -> new ResourceNotFoundException(AppContants.USER_NOT_FOUND));
        if (user.getStatus() == UserStatusEnum.PEDING) {
            return ResponseEntity.status(HttpStatusCode.valueOf(408)).body(new MessageResponse("User is peding"));
        }
        String accessToken = jwtUtils.generateAccessJwtToken(userMail);
        String refreshToken = jwtUtils.generateRefreshJwtToken(userMail);

        Optional<RefreshToken> refreshTokenOptional = refreshTokenRepository.findByUserMail(userMail);
        if (refreshTokenOptional.isPresent()) {
            refreshTokenOptional.get().setRefreshToken(refreshToken);
            refreshTokenRepository.save(refreshTokenOptional.get());
        } else {
            LocalDateTime expiredToken = jwtUtils.extractExpiration(refreshToken)
                    .toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
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
        InfoUserResponse infoUser = mapper.map(user, InfoUserResponse.class);
        return ResponseEntity.ok(new LoginResponse(accessToken, refreshToken, roles, infoUser));
    }

    @Operation(summary = "Sign up", description = "Api for Sign up")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = MessageResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) throws MessagingException, TemplateException, IOException {
        if (userRepository.existsByMail(registerRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already exist!"));
        }
        String passwordEncoded = encoder.encode(registerRequest.getPassword());
        User user = userService.addUser(registerRequest, passwordEncoded);
        verifyTokenService.sendMailVerifyToken(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @Operation(summary = "Verify Token", description = "Api for Verify Token")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = MessageResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @GetMapping("/verify")
    public ResponseEntity<?> verifyTokenMail(@RequestParam("email") String email,
                                             @RequestParam("token") String token) throws MessagingException, TemplateException, IOException {
        MessageResponse messageResponse = verifyTokenService.verifyMailUser(email, token);
        switch (messageResponse.getMessage()) {
            case AppContants.TOKEN_ACTIVE_MAIL_INVALID:
                return ResponseEntity.status(HttpStatusCode.valueOf(407)).body(messageResponse);
            case AppContants.ACTIVE_USER_TOKEN_EXPIRED:
                return ResponseEntity.status(HttpStatusCode.valueOf(408)).body(messageResponse);
        }
        return ResponseEntity.ok(messageResponse);
    }

    @Operation(summary = "Refresh token", description = "Api for Refresh token")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = TokenRefreshResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PostMapping("/refreshToken")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody TokenRefreshRequest tokenRefreshRequest) {
        return ResponseEntity.ok(userService.refreshJwtToken(tokenRefreshRequest));
    }
}
