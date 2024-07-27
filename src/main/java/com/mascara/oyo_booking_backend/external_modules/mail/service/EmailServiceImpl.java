package com.mascara.oyo_booking_backend.external_modules.mail.service;

import com.mascara.oyo_booking_backend.constant.MessageConstant;
import com.mascara.oyo_booking_backend.external_modules.mail.EmailDetails;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.IOException;
import java.util.Map;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 18/10/2023
 * Time      : 8:41 CH
 * Filename  : EmailServiceImpl
 */
@Service
@Slf4j
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private FreeMarkerConfigurer freemarkerConfigurer;

    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public String sendSimpleMessage(EmailDetails<String> emailDetails) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sender);
        message.setTo(emailDetails.getRecipient());
        message.setSubject(emailDetails.getSubject());
        message.setText(emailDetails.getMsgBody());
        emailSender.send(message);
        return MessageConstant.SEND_MAIL_SUCCESS;
    }

    @Override
    public void sendMailWithTemplate(EmailDetails emailDetails, String template, Map<String, Object> templateModel) {
        MimeMessage message = emailSender.createMimeMessage();
        try {
            Template freemarkerTemplate = freemarkerConfigurer.getConfiguration()
                    .getTemplate(template);
            String htmlBody = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerTemplate, templateModel);
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(emailDetails.getRecipient());
            helper.setSubject(emailDetails.getSubject());
            helper.setFrom(sender);
            helper.setText(htmlBody, true);
            emailSender.send(message);
        } catch (MessagingException | IOException | TemplateException e) {
            e.printStackTrace();
            log.error("ERROR SEND EMAIL SUCCESS BOOKING: {}  -- {}", e.getClass(), e.getMessage());
        }
    }
}
