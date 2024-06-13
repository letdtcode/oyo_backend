package com.mascara.oyo_booking_backend.dtos.notification.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class NotificationResponse {
    //    private Long Id;
//    private Long senderId;
//    private Long recipientId;
//    private String content;
//    private String title;
//    private String imageUrl;
    private String message;
}
