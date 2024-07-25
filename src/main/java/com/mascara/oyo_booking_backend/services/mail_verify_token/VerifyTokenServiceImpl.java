package com.mascara.oyo_booking_backend.services.mail_verify_token;

import com.mascara.oyo_booking_backend.dtos.base.BaseMessageData;
import com.mascara.oyo_booking_backend.entities.authentication.MailConfirmToken;
import com.mascara.oyo_booking_backend.entities.authentication.User;
import com.mascara.oyo_booking_backend.enums.user.UserStatusEnum;
import com.mascara.oyo_booking_backend.exceptions.ResourceNotFoundException;
import com.mascara.oyo_booking_backend.external_modules.mail.EmailDetails;
import com.mascara.oyo_booking_backend.external_modules.mail.service.EmailService;
import com.mascara.oyo_booking_backend.repositories.MailConfirmTokenRepository;
import com.mascara.oyo_booking_backend.repositories.UserRepository;
import com.mascara.oyo_booking_backend.utils.AppContants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 19/10/2023
 * Time      : 3:02 SA
 * Filename  : VerifyTokenServiceImpl
 */
@Service
public class VerifyTokenServiceImpl implements VerifyTokenService {

    @Value("${token.expired}")
    private Integer EXPIRATION_TIME;

    @Autowired
    private MailConfirmTokenRepository mailConfirmTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Override
    public MailConfirmToken generateTokenConfirmMail(String token, User user) {
        if (mailConfirmTokenRepository.existsByUserId(user.getId())) {
            MailConfirmToken mailConfirmToken = mailConfirmTokenRepository.findByUserId(user.getId()).get();
            mailConfirmToken.setVerifyToken(token);
            mailConfirmToken.setDateExpired(mailConfirmToken.getTokenExpirationTime(EXPIRATION_TIME));
            mailConfirmTokenRepository.save(mailConfirmToken);
            return null;
        }
        MailConfirmToken mailConfirmToken = new MailConfirmToken(token, user, EXPIRATION_TIME);
        return mailConfirmTokenRepository.save(mailConfirmToken);
    }

    @Override
    public BaseMessageData verifyMailUser(String mail, String token) {
        MailConfirmToken mailConfirmToken = mailConfirmTokenRepository.findByVerifyToken(token)
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("confirm token")));
        User user = userRepository.findByMailConfirmTokenId(mailConfirmToken.getId())
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("user")));
        if (!mailConfirmToken.getDateExpired().isBefore(LocalDateTime.now())) {
            if (user.getMail().equals(mail)) {
                user.setStatus(UserStatusEnum.ENABLE);
                userRepository.save(user);
                return new BaseMessageData(AppContants.ACTIVE_USER_SUCCESS);
            }
            return new BaseMessageData(AppContants.TOKEN_ACTIVE_MAIL_INVALID);
        }
        if (user.getStatus().equals(UserStatusEnum.ENABLE)) {
            return new BaseMessageData(AppContants.TOKEN_ACTIVE_MAIL_INVALID);
        }
        sendMailVerifyToken(user);
        return new BaseMessageData(AppContants.ACTIVE_USER_TOKEN_EXPIRED);
    }

    @Override
    public void sendMailVerifyToken(User user) {
        String codeConfirm = getRandomNumberString();
        generateTokenConfirmMail(codeConfirm, user);
        String objectSend = "email=" + user.getMail() + "&token=" + codeConfirm;
        String baseURL = "http://localhost:5173/active-account?";
        String message = baseURL + objectSend;
        Map<String, Object> mesage = new HashMap<>();
        mesage.put("link_active_user", message);
        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(user.getMail())
                .subject("Xác nhận đăng kí").build();
        emailService.sendMailWithTemplate(emailDetails, "Email_Active_Account.ftl", mesage);
    }

    public static String getRandomNumberString() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        return String.format("%06d", number);
    }
}
