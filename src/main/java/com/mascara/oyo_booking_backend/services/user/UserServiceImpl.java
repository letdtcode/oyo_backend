package com.mascara.oyo_booking_backend.services.user;

import com.mascara.oyo_booking_backend.dtos.request.auth.RegisterRequest;
import com.mascara.oyo_booking_backend.dtos.request.auth.TokenRefreshRequest;
import com.mascara.oyo_booking_backend.dtos.request.user.ChangePasswordRequest;
import com.mascara.oyo_booking_backend.dtos.request.user.UpdateInfoPersonalRequest;
import com.mascara.oyo_booking_backend.dtos.response.auth.TokenRefreshResponse;
import com.mascara.oyo_booking_backend.dtos.response.general.MessageResponse;
import com.mascara.oyo_booking_backend.dtos.response.user.UpdateInfoPersonalReponse;
import com.mascara.oyo_booking_backend.entities.*;
import com.mascara.oyo_booking_backend.enums.RoleEnum;
import com.mascara.oyo_booking_backend.enums.UserStatusEnum;
import com.mascara.oyo_booking_backend.exceptions.ResourceNotFoundException;
import com.mascara.oyo_booking_backend.exceptions.TokenRefreshException;
import com.mascara.oyo_booking_backend.repositories.RefreshTokenRepository;
import com.mascara.oyo_booking_backend.repositories.RoleRepository;
import com.mascara.oyo_booking_backend.repositories.UserRepository;
import com.mascara.oyo_booking_backend.securities.jwt.JwtUtils;
import com.mascara.oyo_booking_backend.utils.AppContants;
import com.mascara.oyo_booking_backend.utils.PasswordValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 09/10/2023
 * Time      : 6:01 CH
 * Filename  : UserService
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    @Transactional
    public User addUser(RegisterRequest request, String passwordEncode) {
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .mail(request.getEmail())
                .password(passwordEncode)
                .gender(2)
                .status(UserStatusEnum.PEDING)
                .build();

        Set<String> strRoles = request.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByRoleName(RoleEnum.ROLE_CLIENT)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByRoleName(RoleEnum.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;
                    case "partner":
                        Role modRole = roleRepository.findByRoleName(RoleEnum.ROLE_PARTNER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByRoleName(RoleEnum.ROLE_CLIENT)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }
        WishList wishList = WishList.builder().user(user).build();

        BookingList bookingList = BookingList.builder().user(user).build();

        ReviewList reviewList = ReviewList.builder().user(user).build();

        CommisionList commisionList = CommisionList.builder().user(user).build();

        Cart cart = Cart.builder().totalPrice(BigDecimal.valueOf(0)).user(user).build();

        int desiredLength = 7;
        String randomUsername = UUID.randomUUID()
                .toString()
                .substring(0, desiredLength);
        user.setUserName("user-" + randomUsername);
        user.setRoleSet(roles);
        user.setWishList(wishList);
        user.setBookingList(bookingList);
        user.setReviewList(reviewList);
        user.setCommisionList(commisionList);
        user.setCart(cart);
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public TokenRefreshResponse refreshJwtToken(TokenRefreshRequest tokenRefreshRequest) {
        String refreshToken = tokenRefreshRequest.getTokenRefresh();
        RefreshToken token = refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.REFRESH_TOKEN_NOT_FOUND));
        if (jwtUtils.validateJwtToken(refreshToken)) {
            User user = userRepository.findByRefreshToken(refreshToken).orElseThrow(() -> new ResourceNotFoundException(AppContants.USER_NOT_FOUND_WITH_REFRESH_TOKEN + refreshToken));
            token.incrementRefreshCount();
            refreshTokenRepository.save(token);
            String accessTokenNew = jwtUtils.generateAccessJwtToken(user.getMail());
            return new TokenRefreshResponse(accessTokenNew);
        }
        throw new TokenRefreshException(refreshToken, "Missing refresh token in database. Please login again");
    }

    @Override
    @Transactional
    public UpdateInfoPersonalReponse updateInfoPersonal(UpdateInfoPersonalRequest request, String email) {
        User user = userRepository.findByMail(email)
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.USER_NOT_FOUND));
        user.setUserName(request.getUserName());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setGender(request.getGender());
        user.setDateOfBirth(request.getDateOfBirth());
        user.setAddress(request.getAddress());
        user.setPhone(request.getPhone());
        user = userRepository.save(user);
        return mapper.map(user, UpdateInfoPersonalReponse.class);
    }

    @Override
    public MessageResponse changePassword(ChangePasswordRequest request) {
        User user = userRepository.findByMail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.USER_NOT_FOUND));
        String oldPassRequest = request.getOldPassword();
        if (encoder.matches(user.getPassword(), oldPassRequest)) {
            if(PasswordValidator.isValid(request.getNewPassword())) {
                String newPassEncoded = encoder.encode(request.getNewPassword());
                user.setPassword(newPassEncoded);
                return new MessageResponse(AppContants.CHANGE_PASSWORD_SUCCESS);
            }
            return new MessageResponse(AppContants.NEW_PASSWORD_NOT_MATCH_PATTERN);
        }
        return new MessageResponse(AppContants.PASSWORD_INCORRECT);
    }
}