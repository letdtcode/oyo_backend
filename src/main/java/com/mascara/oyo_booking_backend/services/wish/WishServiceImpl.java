package com.mascara.oyo_booking_backend.services.wish;

import com.mascara.oyo_booking_backend.dtos.BaseMessageData;
import com.mascara.oyo_booking_backend.entities.User;
import com.mascara.oyo_booking_backend.exceptions.ResourceNotFoundException;
import com.mascara.oyo_booking_backend.repositories.UserRepository;
import com.mascara.oyo_booking_backend.repositories.WishItemRepository;
import com.mascara.oyo_booking_backend.securities.jwt.JwtUtils;
import com.mascara.oyo_booking_backend.utils.AppContants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 27/11/2023
 * Time      : 4:46 CH
 * Filename  : WishServiceImpl
 */
@Service
public class WishServiceImpl implements WishService {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private WishItemRepository wishItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public BaseMessageData<Boolean> checkAccomPlaceIsWishOfUser(Long accomId, String mailUser) {
        User user = userRepository.findByMail(mailUser)
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("User")));
        boolean isWIsh = wishItemRepository.checkAccomIsWishUser(accomId, user.getId());
        return new BaseMessageData<>(isWIsh);
    }
}
