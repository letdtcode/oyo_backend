package com.mascara.oyo_booking_backend.services.notification;

import com.mascara.oyo_booking_backend.dtos.base.BaseMessageData;
import com.mascara.oyo_booking_backend.dtos.base.BasePagingData;
import com.mascara.oyo_booking_backend.dtos.notification.response.NotificationPayloadResponse;
import com.mascara.oyo_booking_backend.entities.notification.Notification;
import com.mascara.oyo_booking_backend.mapper.NotificationMapper;
import com.mascara.oyo_booking_backend.repositories.NotificationRepository;
import com.mascara.oyo_booking_backend.utils.AppContants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 15/06/2024
 * Time      : 9:32 CH
 * Filename  : NotificationServiceImpl
 */
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;

    private final NotificationMapper notificationMapper;

    @Override
    @Transactional
    public BasePagingData<NotificationPayloadResponse> getDataNotificationOfUser(Boolean viewed, Integer pageNum, Integer pageSize, String sortType, String field, String userMail) {
        Pageable paging = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.valueOf(sortType), field));
        Page<Notification> notificationPage = null;
        if (viewed == null) {
            notificationPage = notificationRepository.getAllWithPaging(userMail, paging);
        } else {
            notificationPage = notificationRepository.getAllByViewWithPaging(viewed, userMail, paging);
        }
        List<Notification> notifications = notificationPage.stream().toList();
        List<NotificationPayloadResponse> responses = notifications.stream()
                .map(notification -> notificationMapper.toNotificationPayloadResponse(notification)).collect(Collectors.toList());
        return new BasePagingData<>(responses,
                notificationPage.getNumber(),
                notificationPage.getSize(),
                notificationPage.getTotalElements());
    }

    @Override
    @Transactional
    public BaseMessageData<String> resetAllNotification(String userMail) {
        notificationRepository.updateViewNotificationForUser(true, userMail);
        return new BaseMessageData<>(AppContants.UPDATE_SUCCESS_MESSAGE("Notification"));
    }
}
