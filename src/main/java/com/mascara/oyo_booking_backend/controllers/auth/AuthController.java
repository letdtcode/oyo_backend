package com.mascara.oyo_booking_backend.controllers.auth;

import com.mascara.oyo_booking_backend.dtos.BaseMessageData;
import com.mascara.oyo_booking_backend.dtos.request.auth.LoginRequest;
import com.mascara.oyo_booking_backend.dtos.request.auth.RegisterRequest;
import com.mascara.oyo_booking_backend.dtos.request.auth.TokenRefreshRequest;
import com.mascara.oyo_booking_backend.dtos.response.BaseResponse;
import com.mascara.oyo_booking_backend.dtos.response.auth.LoginResponse;
import com.mascara.oyo_booking_backend.dtos.response.auth.TokenRefreshResponse;
import com.mascara.oyo_booking_backend.dtos.response.user.InfoUserResponse;
import com.mascara.oyo_booking_backend.entities.RefreshToken;
import com.mascara.oyo_booking_backend.entities.Role;
import com.mascara.oyo_booking_backend.entities.User;
import com.mascara.oyo_booking_backend.enums.UserStatusEnum;
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
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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
@Validated
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

    @Value("${avatar.default}")
    private String avatar_default;

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

        Optional<User> optionalUser = userRepository.findByMail(userMail);
        if (!optionalUser.isPresent()) {
            BaseMessageData response = new BaseMessageData("Mail not exist");
            return ResponseEntity.status(HttpStatusCode.valueOf(204)).body(new BaseResponse<>(true, 202, response));
        }
        User user = optionalUser.get();
        if (user.getStatus() == UserStatusEnum.PEDING) {
            BaseMessageData response = new BaseMessageData("User is peding");
            return ResponseEntity.status(HttpStatusCode.valueOf(202)).body(new BaseResponse<>(true, 202, response));
        }
        if (user.getStatus() == UserStatusEnum.BANNED) {
            BaseMessageData response = new BaseMessageData("User is banned");
            return ResponseEntity.status(HttpStatusCode.valueOf(203)).body(new BaseResponse<>(true, 202, response));
        }
        Set<Role> rolesOfUser = user.getRoleSet();
        Set<String> rolesName = rolesOfUser.stream().map(role -> role.getRoleName().toString()).collect(Collectors.toSet());
        String accessToken = jwtUtils.generateAccessJwtToken(userMail, rolesName);
        String refreshToken = jwtUtils.generateRefreshJwtToken(userMail);

        LocalDateTime expiredToken = jwtUtils.getExpirationDateFromToken(refreshToken)
                .toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        RefreshToken refreshTokenUser = RefreshToken.builder()
                .refreshToken(refreshToken)
                .user(user)
                .userId(user.getId())
                .expiryDate(expiredToken)
                .build();
        refreshTokenRepository.save(refreshTokenUser);

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());
        InfoUserResponse infoUser = mapper.map(user, InfoUserResponse.class);
        if (infoUser.getAvatarUrl() == null) {
            infoUser.setAvatarUrl(avatar_default);
        }
        LoginResponse response = new LoginResponse(accessToken, refreshToken, roles, infoUser);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @Operation(summary = "Sign up", description = "Api for Sign up")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BaseResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) throws MessagingException, TemplateException, IOException {
        if (userRepository.existsByMail(registerRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new BaseResponse(false, 400, "Error: Email is already exist!"));
        }
        String passwordEncoded = encoder.encode(registerRequest.getPassword());
        User user = userService.addUser(registerRequest, passwordEncoded);
        verifyTokenService.sendMailVerifyToken(user);
        BaseMessageData response = new BaseMessageData("User registered successfully!");
        return ResponseEntity.ok(new BaseResponse(true, 200, response));
    }

    @Operation(summary = "Refresh token", description = "Api for Refresh token")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = TokenRefreshResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody TokenRefreshRequest tokenRefreshRequest) {
        TokenRefreshResponse response = userService.refreshJwtToken(tokenRefreshRequest);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }

    @Operation(summary = "Verify Token", description = "Api for Verify Token")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BaseResponse.class),
                    mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PostMapping("/verify")
    public ResponseEntity<?> verifyTokenMail(@RequestParam("email") String email,
                                             @RequestParam("token") String token) throws MessagingException, TemplateException, IOException {
        BaseMessageData<String> messageResponse = verifyTokenService.verifyMailUser(email, token);
        switch (messageResponse.getMessage()) {
            case AppContants.TOKEN_ACTIVE_MAIL_INVALID:
                return ResponseEntity.status(HttpStatusCode.valueOf(407)).body(new BaseResponse(false, 407, messageResponse));
            case AppContants.ACTIVE_USER_TOKEN_EXPIRED:
                return ResponseEntity.status(HttpStatusCode.valueOf(408)).body(new BaseResponse(false, 408, messageResponse));
        }
        return ResponseEntity.ok(new BaseResponse(true, 200, messageResponse));
    }

    @Operation(summary = "Reset password API", description = "Auth Api for reset password of user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BaseResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam("mail") @Email @NotNull String mail) throws MessagingException, TemplateException, IOException {
        BaseMessageData response = userService.resetPasswordUser(mail);
        return ResponseEntity.ok(new BaseResponse<>(true, 200, response));
    }
}

