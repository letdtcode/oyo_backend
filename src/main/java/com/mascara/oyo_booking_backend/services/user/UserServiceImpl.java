package com.mascara.oyo_booking_backend.services.user;

import com.mascara.oyo_booking_backend.dtos.request.auth.RegisterRequest;
import com.mascara.oyo_booking_backend.dtos.request.auth.TokenRefreshRequest;
import com.mascara.oyo_booking_backend.dtos.response.auth.TokenRefreshResponse;
import com.mascara.oyo_booking_backend.entities.*;
import com.mascara.oyo_booking_backend.enums.RoleEnum;
import com.mascara.oyo_booking_backend.enums.UserStatusEnum;
import com.mascara.oyo_booking_backend.exceptions.ResourceExistException;
import com.mascara.oyo_booking_backend.exceptions.ResourceNotFoundException;
import com.mascara.oyo_booking_backend.exceptions.TokenRefreshException;
import com.mascara.oyo_booking_backend.repositories.RefreshTokenRepository;
import com.mascara.oyo_booking_backend.repositories.RoleRepository;
import com.mascara.oyo_booking_backend.repositories.UserRepository;
import com.mascara.oyo_booking_backend.securities.jwt.JwtUtils;
import com.mascara.oyo_booking_backend.utils.AppContants;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

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
    JwtUtils jwtUtils;

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
}