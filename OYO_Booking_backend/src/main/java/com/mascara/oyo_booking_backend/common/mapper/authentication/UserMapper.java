package com.mascara.oyo_booking_backend.common.mapper.authentication;

import com.mascara.oyo_booking_backend.dtos.user.response.InfoUserResponse;
import com.mascara.oyo_booking_backend.entities.authentication.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 14/06/2024
 * Time      : 5:21 CH
 * Filename  : UserMapper
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(source = "id", target = "userId")
    InfoUserResponse toInfoUserResponse(User user);
}
