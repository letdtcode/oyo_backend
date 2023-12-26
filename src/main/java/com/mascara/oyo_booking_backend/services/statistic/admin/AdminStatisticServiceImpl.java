package com.mascara.oyo_booking_backend.services.statistic.admin;

import com.mascara.oyo_booking_backend.dtos.request.statistic.admin.AdminStatisticForGuestRequest;
import com.mascara.oyo_booking_backend.dtos.request.statistic.admin.AdminStatisticRequest;
import com.mascara.oyo_booking_backend.dtos.response.statistic.models.AdminStatisticForGuestResponse;
import com.mascara.oyo_booking_backend.dtos.response.statistic.models.AdminStatisticResponse;
import com.mascara.oyo_booking_backend.entities.Revenue;
import com.mascara.oyo_booking_backend.entities.User;
import com.mascara.oyo_booking_backend.repositories.BookingRepository;
import com.mascara.oyo_booking_backend.repositories.RevenueRepository;
import com.mascara.oyo_booking_backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 26/12/2023
 * Time      : 2:45 SA
 * Filename  : AdminStatisticImpl
 */
@Service
public class AdminStatisticServiceImpl implements AdminStatisticService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RevenueRepository revenueRepository;

    @Override
    public AdminStatisticResponse getStatisticOfAdmin(AdminStatisticRequest request) {
        Long totalNumberOfGuests = Long.valueOf(userRepository.findAll().size());
        Long totalNumberOfBooking = Long.valueOf(bookingRepository.findAll().size());
        List<Revenue> revenueList = revenueRepository.findAll();
        Double totalRevenueAdmin = 0D;
        for (Revenue revenue : revenueList) {
            totalRevenueAdmin = totalRevenueAdmin + revenue.getCommPay();
        }
        return AdminStatisticResponse.builder()
                .totalNumberOfGuests(totalNumberOfGuests)
                .totalNumberOfOwner(totalNumberOfGuests)
                .totalNumberOfBooking(totalNumberOfBooking)
                .totalNumberOfRevenue(totalRevenueAdmin)
                .build();
    }

//    public AdminStatisticForGuestResponse getStatisticOfAdminForGuest(AdminStatisticForGuestRequest request, Integer pageNumber, Integer pageSize) {
//        List<User> userList = userRepository.findAll();
//        for (User user:userList) {
//
//        }
//    }
}
