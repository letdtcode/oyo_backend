package com.mascara.oyo_booking_backend.mapper.notification;

import com.mascara.oyo_booking_backend.dtos.notification.response.NotificationPayloadResponse;
import com.mascara.oyo_booking_backend.entities.notification.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 15/06/2024
 * Time      : 4:57 CH
 * Filename  : NotificationMapper
 */
@Mapper(componentModel = "spring")
public interface NotificationMapper {
    @Mapping(source = "createdDate", target = "dateTime")
    @Mapping(source = "id", target = "Id")
    NotificationPayloadResponse toNotificationPayloadResponse(Notification notification);
}
