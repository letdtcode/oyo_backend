package com.mascara.oyo_booking_backend.mapper;

import com.mascara.oyo_booking_backend.dtos.response.booking.GetBookingResponse;
import com.mascara.oyo_booking_backend.entities.AccomPlace;
import com.mascara.oyo_booking_backend.entities.Booking;
import com.mascara.oyo_booking_backend.entities.Revenue;
import com.mascara.oyo_booking_backend.repositories.AccomPlaceRepository;
import com.mascara.oyo_booking_backend.repositories.BookingRepository;
import com.mascara.oyo_booking_backend.repositories.RevenueRepository;
import jakarta.annotation.PostConstruct;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 01/12/2023
 * Time      : 3:07 SA
 * Filename  : BookingMapper
 */
@Component
public class BookingMapper {
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private AccomPlaceRepository accomPlaceRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RevenueRepository revenueRepository;

    //    Covert id accom place to name accom place
    private final Converter<Long, String> idAccomPlaceToNameAccom = context -> {
        Long accomId = context.getSource();
        if (accomId != null) {
            AccomPlace accomPlace = accomPlaceRepository.findById(accomId).get();
            return accomPlace.getAccomName();
        }
        return null;
    };

    //    Covert booking code to commision money
    private final Converter<String, Double> bookingCodeToCommisionMoney = context -> {
        String bookingCode = context.getSource();
        if (bookingCode != null) {
            Revenue revenue = revenueRepository.findByBookingCode(bookingCode).get();
            return revenue.getCommPay();
        }
        return null;
    };

    //    Covert booking code to total revenue
    private final Converter<String, Double> bookingCodeToTotalRevenue = context -> {
        String bookingCode = context.getSource();
        if (bookingCode != null) {
            Revenue revenue = revenueRepository.findByBookingCode(bookingCode).get();
            return revenue.getTotalRevenue();
        }
        return null;
    };

    @PostConstruct
    public void init() {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        mapper.createTypeMap(Booking.class, GetBookingResponse.class)
                .addMappings(mapper -> mapper.using(idAccomPlaceToNameAccom)
                        .map(Booking::getAccomId, GetBookingResponse::setNameAccom))
                .addMappings(mapper -> mapper.using(bookingCodeToCommisionMoney)
                        .map(Booking::getBookingCode, GetBookingResponse::setCommisionMoney))
                .addMappings(mapper -> mapper.using(bookingCodeToTotalRevenue)
                        .map(Booking::getBookingCode, GetBookingResponse::setTotalRevenue));
    }

    public GetBookingResponse toGetBookingResponse(Booking booking) {
        GetBookingResponse getBookingResponse;
        getBookingResponse = mapper.map(booking, GetBookingResponse.class);
        return getBookingResponse;
    }

}
