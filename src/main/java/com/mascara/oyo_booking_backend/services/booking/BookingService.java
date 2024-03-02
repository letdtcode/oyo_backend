package com.mascara.oyo_booking_backend.services.booking;

import com.mascara.oyo_booking_backend.dtos.BaseMessageData;
import com.mascara.oyo_booking_backend.dtos.request.booking.BookingRequest;
import com.mascara.oyo_booking_backend.dtos.request.booking.CancelBookingRequest;
import com.mascara.oyo_booking_backend.dtos.request.booking.CheckBookingRequest;
import com.mascara.oyo_booking_backend.dtos.response.booking.CheckBookingResponse;
import com.mascara.oyo_booking_backend.dtos.response.booking.GetBookingResponse;
import com.mascara.oyo_booking_backend.dtos.response.booking.GetHistoryBookingResponse;
import com.mascara.oyo_booking_backend.dtos.response.paging.BasePagingData;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 22/10/2023
 * Time      : 7:44 CH
 * Filename  : BookingService
 */
public interface BookingService {
    @Transactional
    BaseMessageData createOrderBookingAccom(BookingRequest request, String userMail);

    @Transactional
    CheckBookingResponse checkBookingToGetPrice(CheckBookingRequest request);

    @Transactional
    BasePagingData<GetBookingResponse> getListBookingOfPartner(String hostMail,
                                                              String status,
                                                              Integer pageNum,
                                                              Integer pageSize,
                                                              String sortType,
                                                              String field);

    @Transactional
    BasePagingData<GetHistoryBookingResponse> getHistoryBookingUser(String userMail,
                                                                    Integer pageNum,
                                                                    Integer pageSize,
                                                                    String sortType,
                                                                    String field);

    @Transactional
    BaseMessageData changeStatusBookingByHost(String hostMail, String bookingCode, String status);

    @Transactional
    BaseMessageData cancelBooking(String userMail, CancelBookingRequest request);

//    @Transactional
//    BaseMessageData changeStatusBookingByUser(String userMail, String bookingCode, String status);
}
