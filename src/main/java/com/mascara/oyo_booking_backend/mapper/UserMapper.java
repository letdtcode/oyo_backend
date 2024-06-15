package com.mascara.oyo_booking_backend.mapper;

import com.mascara.oyo_booking_backend.dtos.user.response.InfoUserResponse;
import com.mascara.oyo_booking_backend.entities.authentication.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 14/06/2024
 * Time      : 5:21 CH
 * Filename  : UserMapper
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "id", target = "userId")
    InfoUserResponse toInfoUserResponse(User user);
}
