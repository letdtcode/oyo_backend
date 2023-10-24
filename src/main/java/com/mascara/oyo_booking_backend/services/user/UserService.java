package com.mascara.oyo_booking_backend.services.user;

import com.mascara.oyo_booking_backend.dtos.request.auth.RegisterRequest;
import com.mascara.oyo_booking_backend.dtos.request.auth.TokenRefreshRequest;
import com.mascara.oyo_booking_backend.dtos.request.user.ChangePasswordRequest;
import com.mascara.oyo_booking_backend.dtos.request.user.UpdateInfoPersonalRequest;
import com.mascara.oyo_booking_backend.dtos.response.auth.TokenRefreshResponse;
import com.mascara.oyo_booking_backend.dtos.response.general.MessageResponse;
import com.mascara.oyo_booking_backend.dtos.response.user.UpdateInfoPersonalReponse;
import com.mascara.oyo_booking_backend.entities.User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 09/10/2023
 * Time      : 6:01 CH
 * Filename  : IUserService
 */
public interface UserService {
    User addUser(RegisterRequest request, String passwordEncode);

    @Transactional
    TokenRefreshResponse refreshJwtToken(TokenRefreshRequest tokenRefreshRequest);

    @Transactional
    UpdateInfoPersonalReponse updateInfoPersonal(UpdateInfoPersonalRequest request, String email);

    @Transactional
    UpdateInfoPersonalReponse updateAvatar(MultipartFile file, String mail);

    MessageResponse changePassword(ChangePasswordRequest request);
}
