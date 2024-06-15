package com.mascara.oyo_booking_backend.dtos.notification.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 13/06/2024
 * Time      : 8:49 CH
 * Filename  : NotificationResponse
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationPayloadResponse {
    private Long Id;
    private String title;
    private String content;
    private String recipientMail;
    private LocalDateTime dateTime;
}
