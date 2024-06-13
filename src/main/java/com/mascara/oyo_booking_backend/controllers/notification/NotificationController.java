package com.mascara.oyo_booking_backend.controllers.notification;

import com.mascara.oyo_booking_backend.dtos.notification.response.NotificationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 13/06/2024
 * Time      : 8:20 CH
 * Filename  : NotificationController
 */
@Controller
@RequiredArgsConstructor
public class NotificationController {
//    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/booking-noti")
    @SendTo("/topic/public")
    public NotificationResponse bookingNoti() {
        NotificationResponse response = NotificationResponse.builder()
                .message("Thanh cong").build();
        return response;
    }
}
