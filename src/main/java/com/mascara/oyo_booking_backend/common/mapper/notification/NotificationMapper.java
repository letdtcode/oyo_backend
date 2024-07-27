package com.mascara.oyo_booking_backend.common.mapper.notification;

import com.mascara.oyo_booking_backend.dtos.notification.response.NotificationPayloadResponse;
import com.mascara.oyo_booking_backend.entities.notification.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 15/06/2024
 * Time      : 4:57 CH
 * Filename  : NotificationMapper
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NotificationMapper {
    @Mapping(source = "createdDate", target = "dateTime")
    @Mapping(source = "id", target = "Id")
    NotificationPayloadResponse toNotificationPayloadResponse(Notification notification);
}
