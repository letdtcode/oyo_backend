package com.mascara.oyo_booking_backend.services.user;

import com.mascara.oyo_booking_backend.common.constant.MessageConstant;
import com.mascara.oyo_booking_backend.common.enums.user.AuthProviderEnum;
import com.mascara.oyo_booking_backend.common.enums.user.RoleEnum;
import com.mascara.oyo_booking_backend.common.enums.user.UserStatusEnum;
import com.mascara.oyo_booking_backend.common.exceptions.ResourceNotFoundException;
import com.mascara.oyo_booking_backend.common.exceptions.TokenRefreshException;
import com.mascara.oyo_booking_backend.common.mapper.authentication.UserMapper;
import com.mascara.oyo_booking_backend.dtos.auth.request.RegisterRequest;
import com.mascara.oyo_booking_backend.dtos.auth.request.TokenRefreshRequest;
import com.mascara.oyo_booking_backend.dtos.auth.response.TokenRefreshResponse;
import com.mascara.oyo_booking_backend.dtos.base.BaseMessageData;
import com.mascara.oyo_booking_backend.dtos.base.BasePagingData;
import com.mascara.oyo_booking_backend.dtos.user.request.ChangePasswordRequest;
import com.mascara.oyo_booking_backend.dtos.user.request.UpdateInfoPersonalRequest;
import com.mascara.oyo_booking_backend.dtos.user.response.InfoUserResponse;
import com.mascara.oyo_booking_backend.entities.authentication.RefreshToken;
import com.mascara.oyo_booking_backend.entities.authentication.Role;
import com.mascara.oyo_booking_backend.entities.authentication.User;
import com.mascara.oyo_booking_backend.entities.booking.BookingList;
import com.mascara.oyo_booking_backend.entities.review.ReviewList;
import com.mascara.oyo_booking_backend.entities.wish.WishList;
import com.mascara.oyo_booking_backend.external_modules.mail.EmailDetails;
import com.mascara.oyo_booking_backend.external_modules.mail.model_mail.ResetPasswordInfo;
import com.mascara.oyo_booking_backend.external_modules.mail.service.EmailService;
import com.mascara.oyo_booking_backend.external_modules.storage.cloudinary.CloudinaryService;
import com.mascara.oyo_booking_backend.repositories.RefreshTokenRepository;
import com.mascara.oyo_booking_backend.repositories.RoleRepository;
import com.mascara.oyo_booking_backend.repositories.UserRepository;
import com.mascara.oyo_booking_backend.securities.jwt.JwtUtils;
import com.mascara.oyo_booking_backend.securities.oauth2.user.OAuth2UserInfo;
import com.mascara.oyo_booking_backend.utils.RandomStringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 09/10/2023
 * Time      : 6:01 CH
 * Filename  : UserService
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder encoder;
    private final CloudinaryService cloudinaryService;
    private final EmailService emailService;

    @Override
    @Transactional
    public User createUserOauth2(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
        String fullName = oAuth2UserInfo.getName();
        String firstName = "";
        String lastName = "";
        int indexSpaceInFullName = fullName.indexOf(" ");
        if (indexSpaceInFullName != -1) {
            firstName = fullName.substring(0, indexSpaceInFullName);
            lastName = fullName.substring(indexSpaceInFullName + 1);
        } else {
            firstName = fullName;
        }
        User user = User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .mail(oAuth2UserInfo.getEmail())
                .avatarUrl(oAuth2UserInfo.getImageUrl())
                .provider(AuthProviderEnum.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))
                .providerId(oAuth2UserInfo.getId())
                .status(UserStatusEnum.ENABLE)
                .build();

        Set<String> strRoles = new HashSet<>(Arrays.asList("Client", "Partner"));
        Set<Role> roles = new HashSet<>();
        strRoles.forEach(role -> {
            switch (role) {
                case "Admin":
                    Role adminRole = roleRepository.findByRoleName(RoleEnum.ADMIN.toString())
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(adminRole);
                    break;
                case "Partner":
                    Role modRole = roleRepository.findByRoleName(RoleEnum.PARTNER.toString())
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(modRole);
                    break;
                default:
                    Role userRole = roleRepository.findByRoleName(RoleEnum.CLIENT.toString())
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(userRole);
            }
        });
        WishList wishList = WishList.builder().user(user).build();

        BookingList bookingList = BookingList.builder().user(user).build();

        ReviewList reviewList = ReviewList.builder().user(user).build();

        int desiredLength = 7;
        String randomUsername = UUID.randomUUID()
                .toString()
                .substring(0, desiredLength);

        user.setUserName("user-" + randomUsername);
        user.setRoleSet(roles);
        user.setWishList(wishList);
        user.setBookingList(bookingList);
        user.setReviewList(reviewList);
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User addUser(RegisterRequest request, String passwordEncode) {
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .mail(request.getEmail())
                .password(passwordEncode)
                .gender(2)
                .provider(AuthProviderEnum.local)
                .status(UserStatusEnum.PEDING)
                .build();

        Set<String> strRoles = request.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByRoleName(RoleEnum.CLIENT.toString())
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "Admin":
                        Role adminRole = roleRepository.findByRoleName(RoleEnum.ADMIN.toString())
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;
                    case "Partner":
                        Role modRole = roleRepository.findByRoleName(RoleEnum.PARTNER.toString())
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByRoleName(RoleEnum.CLIENT.toString())
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }
        WishList wishList = WishList.builder().user(user).build();

        BookingList bookingList = BookingList.builder().user(user).build();

        ReviewList reviewList = ReviewList.builder().user(user).build();

        int desiredLength = 7;
        String randomUsername = UUID.randomUUID()
                .toString()
                .substring(0, desiredLength);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            wishList.setCreatedBy("dev");
            wishList.setLastModifiedBy("dev");
            bookingList.setCreatedBy("dev");
            bookingList.setLastModifiedBy("dev");
            reviewList.setCreatedBy("dev");
            reviewList.setLastModifiedBy("dev");

            user.setCreatedBy("dev");
            user.setLastModifiedBy("dev");
            user.setStatus(UserStatusEnum.ENABLE);
            user.setAddress("Tp.HCM");
            user.setDateOfBirth(LocalDate.ofEpochDay(23 / 12 / 2002));
        }
        user.setUserName("user-" + randomUsername);
        user.setRoleSet(roles);
        user.setWishList(wishList);
        user.setBookingList(bookingList);
        user.setReviewList(reviewList);
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public TokenRefreshResponse refreshJwtToken(TokenRefreshRequest tokenRefreshRequest) {
        String refreshToken = tokenRefreshRequest.getRefreshToken();
        if (jwtUtils.isTokenExpired(refreshToken))
            throw new TokenRefreshException("Refresh token is expired");
        RefreshToken token = refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstant.NOT_FOUND_MESSAGE("refresh token")));
        String emailUser = jwtUtils.getUsernameFromToken(refreshToken);
        token.incrementRefreshCount();
        refreshTokenRepository.save(token);

        User user = userRepository.findByMail(emailUser)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstant.NOT_FOUND_MESSAGE("user")));
        Set<Role> rolesOfUser = user.getRoleSet();
        Set<String> rolesName = rolesOfUser.stream().map(role -> role.getRoleName().toString())
                .collect(Collectors.toSet());

        String accessTokenNew = jwtUtils.generateAccessJwtToken(emailUser, rolesName);
        return new TokenRefreshResponse(accessTokenNew);

    }

    @Override
    @Transactional
    public InfoUserResponse updateInfoPersonal(UpdateInfoPersonalRequest request, String email) {
        User user = userRepository.findByMail(email)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstant.NOT_FOUND_MESSAGE("user")));
        user.setUserName(request.getUserName());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setGender(request.getGender());
        user.setDateOfBirth(request.getDateOfBirth());
        user.setAddress(request.getAddress());
        user.setPhone(request.getPhone());
        user = userRepository.save(user);
        return userMapper.toInfoUserResponse(user);
    }

    @Override
    @Transactional
    public InfoUserResponse updateAvatar(MultipartFile file, String mail) {
        if (!file.isEmpty()) {
            User user = userRepository.findByMail(mail).orElseThrow(() -> new ResourceNotFoundException(MessageConstant.NOT_FOUND_MESSAGE("user")));
            String pathImg = cloudinaryService.store(file).getImageUrl();
            user.setAvatarUrl(pathImg);
            userRepository.save(user);
            return userMapper.toInfoUserResponse(user);
        }
        throw new ResourceNotFoundException(MessageConstant.FILE_IS_NULL);
    }

    @Override
    @Transactional
    public BaseMessageData changePassword(ChangePasswordRequest request, String mailUser) {
        if (!request.getEmail().equals(mailUser)) {
            return new BaseMessageData(MessageConstant.NOT_PERMIT);
        }
        User user = userRepository.findByMail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstant.NOT_FOUND_MESSAGE("user")));
        String oldPassRequest = request.getOldPassword();
        if (encoder.matches(oldPassRequest, user.getPassword())) {
            String newPassEncoded = encoder.encode(request.getNewPassword());
            user.setPassword(newPassEncoded);
            userRepository.save(user);
            return new BaseMessageData(MessageConstant.CHANGE_PASSWORD_SUCCESS);
        }
        return new BaseMessageData(MessageConstant.PASSWORD_INCORRECT);
    }

    @Override
    @Transactional
    public BasePagingData<InfoUserResponse> getAllUserWithPaging(Integer pageNumber,
                                                                 Integer pageSize,
                                                                 String sortType,
                                                                 String field) {
        Pageable paging = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.fromString(sortType), field));
        Page<User> userPage = userRepository.getAllWithPaging(paging);
        List<User> userList = userPage.stream().toList();
        List<InfoUserResponse> responseList = userList.stream().map(user -> userMapper.toInfoUserResponse(user)).collect(Collectors.toList());
        return new BasePagingData<>(responseList,
                userPage.getNumber(),
                userPage.getSize(),
                userPage.getTotalElements());
    }

    @Override
    @Transactional
    public BaseMessageData changeStatusUser(String email, String status) {
        User user = userRepository.findByMail(email)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstant.NOT_FOUND_MESSAGE("User")));
        userRepository.changeStatusUser(email, status);
        return new BaseMessageData(MessageConstant.UPDATE_SUCCESS_MESSAGE("user"));
    }

    @Override
    @Transactional
    public BaseMessageData<String> resetPasswordUser(String mail) {
        User user = userRepository.findByMail(mail)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstant.NOT_FOUND_MESSAGE("User")));
        String newPassword = RandomStringUtils.generateRandomString();
        String hasedPassword = encoder.encode(newPassword);
        user.setPassword(hasedPassword);
        userRepository.save(user);

        ResetPasswordInfo info = ResetPasswordInfo.builder()
                .fullName(user.getFirstName() + " " + user.getLastName())
                .newPassword(newPassword)
                .build();
        Map<String, Object> model = new HashMap<>();
        model.put("fullName", info.getFullName());
        model.put("newPassword", info.getNewPassword());
        EmailDetails<Object> emailDetails = EmailDetails.builder()
                .recipient(mail)
                .subject("Khôi phục mật khẩu")
                .build();
        emailService.sendMailWithTemplate(emailDetails, "Email_Reset_Password.ftl", model);
        return new BaseMessageData(MessageConstant.RESET_PASSWORD_SUCESS);
    }

    @Override
    @Transactional
    public BaseMessageData deleteUser(String email) {
        User user = userRepository.findByMail(email)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstant.NOT_FOUND_MESSAGE("User")));
        user.setDeleted(true);
        userRepository.save(user);
        return new BaseMessageData(MessageConstant.DELETE_SUCCESS_MESSAGE("user"));
    }
}