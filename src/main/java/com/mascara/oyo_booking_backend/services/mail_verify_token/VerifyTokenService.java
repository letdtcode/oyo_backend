package com.mascara.oyo_booking_backend.services.mail_verify_token;

import com.mascara.oyo_booking_backend.dtos.response.general.MessageResponse;
import com.mascara.oyo_booking_backend.entities.MailConfirmToken;
import com.mascara.oyo_booking_backend.entities.User;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;

import java.io.IOException;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 19/10/2023
 * Time      : 3:02 SA
 * Filename  : VerifyTokenService
 */
public interface VerifyTokenService {
    MailConfirmToken generateTokenConfirmMail(String token, User user);

    String verifyMailUser(String mail, String token) throws MessagingException, TemplateException, IOException;

    void sendMailVerifyToken(User user) throws MessagingException, TemplateException, IOException;
}
