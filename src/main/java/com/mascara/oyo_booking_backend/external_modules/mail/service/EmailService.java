package com.mascara.oyo_booking_backend.external_modules.mail.service;

import com.mascara.oyo_booking_backend.external_modules.mail.EmailDetails;

import java.util.Map;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 18/10/2023
 * Time      : 8:42 CH
 * Filename  : EmailService
 */
public interface EmailService {
    String sendSimpleMessage(EmailDetails<String> emailDetails);

    void sendMailWithTemplate(EmailDetails emailDetails, String template, Map<String, Object> templateModel);
}