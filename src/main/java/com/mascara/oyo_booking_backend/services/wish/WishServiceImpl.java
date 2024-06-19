package com.mascara.oyo_booking_backend.services.wish;

import com.mascara.oyo_booking_backend.dtos.base.BaseMessageData;
import com.mascara.oyo_booking_backend.dtos.accom_place.response.GetAccomPlaceResponse;
import com.mascara.oyo_booking_backend.dtos.base.BasePagingData;
import com.mascara.oyo_booking_backend.entities.accommodation.AccomPlace;
import com.mascara.oyo_booking_backend.entities.authentication.User;
import com.mascara.oyo_booking_backend.entities.wish.WishItem;
import com.mascara.oyo_booking_backend.entities.wish.WishList;
import com.mascara.oyo_booking_backend.exceptions.ResourceNotFoundException;
import com.mascara.oyo_booking_backend.mapper.accommodation.AccomPlaceMapper;
import com.mascara.oyo_booking_backend.repositories.AccomPlaceRepository;
import com.mascara.oyo_booking_backend.repositories.UserRepository;
import com.mascara.oyo_booking_backend.repositories.WishItemRepository;
import com.mascara.oyo_booking_backend.repositories.WishListRepository;
import com.mascara.oyo_booking_backend.utils.AppContants;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 27/11/2023
 * Time      : 4:46 CH
 * Filename  : WishServiceImpl
 */
@Service
@RequiredArgsConstructor
public class WishServiceImpl implements WishService {
    private final WishItemRepository wishItemRepository;
    private final UserRepository userRepository;
    private final AccomPlaceRepository accomPlaceRepository;
    private final WishListRepository wishListRepository;
    private final AccomPlaceMapper accomPlaceMapper;

    @Override
    @Transactional
    public BaseMessageData<Boolean> checkAccomPlaceIsWishOfUser(Long accomId, String mailUser) {
        User user = userRepository.findByMail(mailUser).orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("User")));
        boolean isWIsh = wishItemRepository.checkAccomIsWishUser(accomId, user.getId());
        return new BaseMessageData<>(isWIsh);
    }

    @Override
    @Transactional
    public BaseMessageData<String> handleWishItemOfUser(Long accomId, String mailUser) {
        User user = userRepository.findByMail(mailUser).orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("User")));
        WishList wishList = wishListRepository.findWishListByUserId(user.getId()).orElseThrow(() -> new ResourceNotFoundException("Wish list"));
        AccomPlace accomPlace = accomPlaceRepository.findById(accomId).orElseThrow(() -> new ResourceNotFoundException("Accom place"));
        Optional<WishItem> wishItemOptional = wishItemRepository.findWishItemByAccomIdIgnoreDeleted(accomId, user.getId());
        if (!wishItemOptional.isPresent()) {
            WishItem wishItem = WishItem.builder().accomPlace(accomPlace).accomId(accomPlace.getId()).wishList(wishList).wishId(wishList.getId()).build();
            wishItemRepository.save(wishItem);
            return new BaseMessageData<>(AppContants.ADD_WISH_ITEM_SUCCESS);
        }
        WishItem wishItem = wishItemOptional.get();
        if (wishItem.isDeleted()) {
            wishItemRepository.changeDeletedWishItem(false, wishList.getId(), accomPlace.getId());
            return new BaseMessageData<>(AppContants.ADD_WISH_ITEM_SUCCESS);
        }
        wishItemRepository.changeDeletedWishItem(true, wishList.getId(), accomPlace.getId());
        return new BaseMessageData<>(AppContants.REMOVE_WISH_ITEM_SUCCESS);
    }

    @Override
    @Transactional
    public BasePagingData<GetAccomPlaceResponse> getListWishItemOfUser(Integer pageNum,
                                                                       Integer pageSize,
                                                                       String sortType,
                                                                       String field,
                                                                       String userMail) {
        User user = userRepository.findByMail(userMail).orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("User")));
        Pageable paging = PageRequest.of(pageNum, pageSize,
                Sort.by(Sort.Direction.valueOf(sortType), field));
        Page<AccomPlace> accomPlacePage = accomPlaceRepository.getListAccomPlaceOfWishListUser(user.getId(), paging);
        List<AccomPlace> accomPlaceList = accomPlacePage.stream().toList();
        List<GetAccomPlaceResponse> responseList = accomPlaceList.stream()
                .map(accomPlace -> accomPlaceMapper.toGetAccomPlaceResponse(accomPlace)).collect(Collectors.toList());
        return new BasePagingData<>(responseList, accomPlacePage.getNumber(), accomPlacePage.getSize(), accomPlacePage.getTotalElements());
    }
}
