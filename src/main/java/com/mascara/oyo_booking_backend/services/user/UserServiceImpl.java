package com.mascara.oyo_booking_backend.services.user;

import com.mascara.oyo_booking_backend.dtos.request.user.CreateUserRequest;
import com.mascara.oyo_booking_backend.dtos.response.user.CreateUserResponse;
import com.mascara.oyo_booking_backend.entities.*;
import com.mascara.oyo_booking_backend.enums.RoleEnum;
import com.mascara.oyo_booking_backend.enums.UserStatusEnum;
import com.mascara.oyo_booking_backend.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 09/10/2023
 * Time      : 6:01 CH
 * Filename  : UserService
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public CreateUserResponse createUser(CreateUserRequest request, String passwordEncode) {
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .mail(request.getMail())
                .password(passwordEncode)
                .gender(2)
                .status(UserStatusEnum.PEDING)
                .build();

        Set<Role> roles = new HashSet<>();
        for (RoleEnum roleName : request.getRoles()) {
            roles.add(Role.builder().roleName(roleName).build());
        }

        WishList wishList = WishList.builder().user(user).build();

        BookingList bookingList = BookingList.builder().user(user).build();

        ReviewList reviewList = ReviewList.builder().user(user).build();

        CommisionList commisionList = CommisionList.builder().user(user).build();

        Cart cart = Cart.builder().totalPrice(BigDecimal.valueOf(0)).user(user).build();

        user.setRoleSet(roles);
        user.setWishList(wishList);
        user.setBookingList(bookingList);
        user.setReviewList(reviewList);
        user.setCommisionList(commisionList);
        user.setCart(cart);
        user = userRepository.save(user);
        return mapper.map(user, CreateUserResponse.class);
    }

}