package com.mascara.oyo_booking_backend.mapper.helper;

import com.mascara.oyo_booking_backend.constant.MessageConstant;
import com.mascara.oyo_booking_backend.entities.authentication.User;
import com.mascara.oyo_booking_backend.exceptions.ResourceNotFoundException;
import com.mascara.oyo_booking_backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 17/06/2024
 * Time      : 9:41 CH
 * Filename  : UserHelperMapper
 */
@Component
@RequiredArgsConstructor
public class UserHelperMapper {

    private final UserRepository userRepository;

    //    Convert user id to host name
    @Named("userIdToNameHost")
    public String userIdToNameHost(Long userId) {
        if (userId != null) {
            User host = userRepository.findByUserId(userId)
                    .orElseThrow(() -> new ResourceNotFoundException(MessageConstant.NOT_FOUND_MESSAGE("User")));
            return host.getFirstName() + " " + host.getLastName();
        }
        return null;
    }

    @Named("userIdToHostMail")
    public String userIdToHostMail(Long userId) {
        if (userId != null) {
            User host = userRepository.findByUserId(userId)
                    .orElseThrow(() -> new ResourceNotFoundException(MessageConstant.NOT_FOUND_MESSAGE("User")));
            return host.getMail();
        }
        return null;
    }
}
