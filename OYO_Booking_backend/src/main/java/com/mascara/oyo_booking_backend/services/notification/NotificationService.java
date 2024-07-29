package com.mascara.oyo_booking_backend.services.notification;

import com.mascara.oyo_booking_backend.dtos.base.BaseMessageData;
import com.mascara.oyo_booking_backend.dtos.base.BasePagingData;
import com.mascara.oyo_booking_backend.dtos.notification.response.NotificationPayloadResponse;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 15/06/2024
 * Time      : 9:32 CH
 * Filename  : NotificationService
 */
public interface NotificationService {
    @Transactional
    BasePagingData<NotificationPayloadResponse> getDataNotificationOfUser(Boolean viewed, Integer pageNum, Integer pageSize, String sortType, String field, String userMail);

    @Transactional
    BaseMessageData<String> resetAllNotification(String userMail);
}
