package com.mascara.oyo_booking_backend.mail.service;

import com.mascara.oyo_booking_backend.dtos.response.general.MessageResponse;
import com.mascara.oyo_booking_backend.mail.EmailDetails;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;

import java.io.IOException;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 18/10/2023
 * Time      : 8:42 CH
 * Filename  : EmailService
 */
public interface EmailService {
    MessageResponse sendSimpleMessage(EmailDetails emailDetails);

    void sendMailWithTemplate(EmailDetails emailDetails) throws MessagingException, IOException, TemplateException;
}