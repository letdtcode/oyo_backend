package com.mascara.oyo_booking_backend.services.user;

import com.mascara.oyo_booking_backend.dtos.BaseMessageData;
import com.mascara.oyo_booking_backend.dtos.request.auth.RegisterRequest;
import com.mascara.oyo_booking_backend.dtos.request.auth.TokenRefreshRequest;
import com.mascara.oyo_booking_backend.dtos.request.user.ChangePasswordRequest;
import com.mascara.oyo_booking_backend.dtos.request.user.UpdateInfoPersonalRequest;
import com.mascara.oyo_booking_backend.dtos.response.auth.TokenRefreshResponse;
import com.mascara.oyo_booking_backend.dtos.response.paging.BasePagingData;
import com.mascara.oyo_booking_backend.dtos.response.user.InfoUserResponse;
import com.mascara.oyo_booking_backend.entities.*;
import com.mascara.oyo_booking_backend.enums.RoleEnum;
import com.mascara.oyo_booking_backend.enums.UserStatusEnum;
import com.mascara.oyo_booking_backend.exceptions.ResourceNotFoundException;
import com.mascara.oyo_booking_backend.exceptions.TokenRefreshException;
import com.mascara.oyo_booking_backend.external_modules.mail.EmailDetails;
import com.mascara.oyo_booking_backend.external_modules.mail.ResetPasswordInfo;
import com.mascara.oyo_booking_backend.external_modules.mail.service.EmailService;
import com.mascara.oyo_booking_backend.external_modules.storage.cloudinary.CloudinaryService;
import com.mascara.oyo_booking_backend.repositories.RefreshTokenRepository;
import com.mascara.oyo_booking_backend.repositories.RoleRepository;
import com.mascara.oyo_booking_backend.repositories.UserRepository;
import com.mascara.oyo_booking_backend.securities.jwt.JwtUtils;
import com.mascara.oyo_booking_backend.utils.AppContants;
import com.mascara.oyo_booking_backend.utils.RandomStringUtils;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private EmailService emailService;

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
            Role userRole = roleRepository.findByRoleName(RoleEnum.ROLE_CLIENT.toString())
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "Admin":
                        Role adminRole = roleRepository.findByRoleName(RoleEnum.ROLE_ADMIN.toString())
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;
                    case "Partner":
                        Role modRole = roleRepository.findByRoleName(RoleEnum.ROLE_PARTNER.toString())
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByRoleName(RoleEnum.ROLE_CLIENT.toString())
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }
        WishList wishList = WishList.builder().user(user).build();

        BookingList bookingList = BookingList.builder().user(user).build();

        ReviewList reviewList = ReviewList.builder().user(user).build();

        RevenueList revenueList = RevenueList.builder().discount(10F).user(user).build();

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
            revenueList.setCreatedBy("dev");
            revenueList.setLastModifiedBy("dev");

            user.setCreatedBy("dev");
            user.setLastModifiedBy("dev");
            user.setStatus(UserStatusEnum.ENABLE);
            user.setAddress("Tp.HCM");
            user.setAvatarUrl("https://res.cloudinary.com/dyv5zrsgj/image/upload/v1698163058/oyo_booking/nqxq12lb5gazvph6rwf7.png");
            user.setDateOfBirth(LocalDate.ofEpochDay(23 / 12 / 2002));
        }
        user.setUserName("user-" + randomUsername);
        user.setRoleSet(roles);
        user.setWishList(wishList);
        user.setBookingList(bookingList);
        user.setReviewList(reviewList);
        user.setRevenueList(revenueList);
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public TokenRefreshResponse refreshJwtToken(TokenRefreshRequest tokenRefreshRequest) {
        String refreshToken = tokenRefreshRequest.getRefreshToken();
        if (jwtUtils.isTokenExpired(refreshToken))
            throw new TokenRefreshException("Refresh token is expired");
        RefreshToken token = refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("refresh token")));
        String emailUser = jwtUtils.getUsernameFromToken(refreshToken);
        token.incrementRefreshCount();
        refreshTokenRepository.save(token);

        User user = userRepository.findByMail(emailUser)
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("user")));
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
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("user")));
        user.setUserName(request.getUserName());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setGender(request.getGender());
        user.setDateOfBirth(request.getDateOfBirth());
        user.setAddress(request.getAddress());
        user.setPhone(request.getPhone());
        user = userRepository.save(user);
        return mapper.map(user, InfoUserResponse.class);
    }

    @Override
    @Transactional
    public InfoUserResponse updateAvatar(MultipartFile file, String mail) {
        if (!file.isEmpty()) {
            User user = userRepository.findByMail(mail).orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("user")));
            String pathImg = cloudinaryService.store(file).getImageUrl();
            user.setAvatarUrl(pathImg);
            userRepository.save(user);
            return mapper.map(user, InfoUserResponse.class);
        }
        throw new ResourceNotFoundException(AppContants.FILE_IS_NULL);
    }

    @Override
    @Transactional
    public BaseMessageData changePassword(ChangePasswordRequest request, String mailUser) {
        if (!request.getEmail().equals(mailUser)) {
            return new BaseMessageData(AppContants.NOT_PERMI_CHANGE_PASSWORD);
        }
        User user = userRepository.findByMail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("user")));
        String oldPassRequest = request.getOldPassword();
        if (encoder.matches(oldPassRequest, user.getPassword())) {
            String newPassEncoded = encoder.encode(request.getNewPassword());
            user.setPassword(newPassEncoded);
            userRepository.save(user);
            return new BaseMessageData(AppContants.CHANGE_PASSWORD_SUCCESS);
        }
        return new BaseMessageData(AppContants.PASSWORD_INCORRECT);
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
        List<InfoUserResponse> responseList = userList.stream().map(user -> mapper.map(user,
                InfoUserResponse.class)).collect(Collectors.toList());
        return new BasePagingData<>(responseList,
                userPage.getNumber(),
                userPage.getSize(),
                userPage.getTotalElements());
    }

    @Override
    @Transactional
    public BaseMessageData changeStatusUser(String email, String status) {
        User user = userRepository.findByMail(email)
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("User")));
        userRepository.changeStatusUser(email, status);
        return new BaseMessageData(AppContants.UPDATE_SUCCESS_MESSAGE("user"));
    }

    @Override
    @Transactional
    public BaseMessageData<String> resetPasswordUser(String mail) throws MessagingException, TemplateException, IOException {
        User user = userRepository.findByMail(mail)
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("User")));
        String newPassword = RandomStringUtils.generateRandomString();
        String hasedPassword = encoder.encode(newPassword);
        user.setPassword(hasedPassword);
        userRepository.save(user);

        ResetPasswordInfo info = ResetPasswordInfo.builder()
                .fullName(user.getFirstName() + " " + user.getLastName())
                .newPassword(newPassword)
                .build();
        EmailDetails<Object> emailDetails = EmailDetails.builder()
                .recipient(mail)
                .subject("Khôi phục mật khẩu")
                .msgBody(info).build();
        emailService.sendMailWithTemplate(emailDetails, "Email_Reset_Password.ftl");
        return new BaseMessageData(AppContants.RESET_PASSWORD_SUCESS);
    }

    @Override
    @Transactional
    public BaseMessageData deleteUser(String email) {
        User user = userRepository.findByMail(email)
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("User")));
        user.setDeleted(true);
        userRepository.save(user);
        return new BaseMessageData(AppContants.DELETE_SUCCESS_MESSAGE("user"));
    }
}