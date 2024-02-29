package com.mascara.oyo_booking_backend.mapper;

import com.mascara.oyo_booking_backend.dtos.response.booking.GetBookingResponse;
import com.mascara.oyo_booking_backend.dtos.response.booking.GetHistoryBookingResponse;
import com.mascara.oyo_booking_backend.entities.*;
import com.mascara.oyo_booking_backend.exceptions.ResourceNotFoundException;
import com.mascara.oyo_booking_backend.repositories.*;
import jakarta.annotation.PostConstruct;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

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
    private UserRepository userRepository;

    @Autowired
    private RevenueRepository revenueRepository;

    @Autowired
    private DistrictRepository districtRepository;

    @Autowired
    private ProvinceRepository provinceRepository;

    @Autowired
    private BookingRepository bookingRepository;

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

    //    Covert booking code to total revenue
    private final Converter<Long, String> idAccomToFullNameHost = context -> {
        Long accomId = context.getSource();
        if (accomId != null) {
            Long hostId = accomPlaceRepository.findById(accomId).get().getUserId();
            User host = userRepository.findByUserId(hostId).get();
            return host.getFirstName() + " " + host.getLastName();
        }
        return null;
    };

    //    Covert booking code to total revenue
    private final Converter<Long, String> idAccomToGeneralAddress = context -> {
        Long accomId = context.getSource();
        if (accomId != null) {
            AccomPlace accomPlace = accomPlaceRepository.findById(accomId)
                    .orElseThrow(() -> new ResourceNotFoundException("Accom place"));
            String districtCode = accomPlace.getDistrictCode();
            String provinceCode = accomPlace.getProvinceCode();
            String districtName = districtRepository.findByDistrictCode(districtCode).get().getDistrictName();
            String provinceName = provinceRepository.findByProvinceCode(provinceCode).get().getProvinceName();
            return districtName + ", " + provinceName;
        }
        return null;
    };

    //    Covert booking code to total revenue
    private final Converter<Long, Double> idAccomToPricePerNight = context -> {
        Long accomId = context.getSource();
        if (accomId != null) {
            AccomPlace accomPlace = accomPlaceRepository.findById(accomId)
                    .orElseThrow(() -> new ResourceNotFoundException("Accom place"));
            return accomPlace.getPricePerNight();
        }
        return null;
    };

    //    Covert booking code to total revenue
    private final Converter<Long, String> idAccomToImageUrlDefaul = context -> {
        Long accomId = context.getSource();
        if (accomId != null) {
            AccomPlace accomPlace = accomPlaceRepository.findById(accomId)
                    .orElseThrow(() -> new ResourceNotFoundException("Accom place"));
            Set<ImageAccom> imageAccomSet = accomPlace.getImageAccoms();
            if (imageAccomSet.isEmpty() || imageAccomSet == null)
                return null;
            List<ImageAccom> imageAccomList = imageAccomSet.stream().toList();
            String imageUrlDefaul = imageAccomList.get(0).getImgAccomLink();
            return imageUrlDefaul;
        }
        return null;
    };

    //    Covert booking code to total revenue
//    private final Converter<Long, String> idAccomToRefundPolicy = context -> {
//        Long accomId = context.getSource();
//        if (accomId != null) {
//            AccomPlace accomPlace = accomPlaceRepository.findById(accomId).get();
//            return accomPlace.getRefundPolicy();
//        }
//        return null;
//    };

    //    Covert booking code to total revenue
    private final Converter<Long, Boolean> idBookingToIsReviewed = context -> {
        Long bookingId = context.getSource();
        Booking booking = bookingRepository.findById(bookingId).get();
        Review reviewOfBooking = booking.getReview();
        if (reviewOfBooking != null)
            return true;
        return false;
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

        mapper.createTypeMap(Booking.class, GetHistoryBookingResponse.class)
                .addMappings(mapper -> mapper.using(idAccomToFullNameHost)
                        .map(Booking::getAccomId, GetHistoryBookingResponse::setFullNameHost))
                .addMappings(mapper -> mapper.using(idAccomPlaceToNameAccom)
                        .map(Booking::getAccomId, GetHistoryBookingResponse::setNameAccom))
                .addMappings(mapper -> mapper.using(idAccomToGeneralAddress)
                        .map(Booking::getAccomId, GetHistoryBookingResponse::setGeneralAddress))
                .addMappings(mapper -> mapper.using(idAccomToPricePerNight)
                        .map(Booking::getAccomId, GetHistoryBookingResponse::setPricePerNight))
                .addMappings(mapper -> mapper.using(idAccomToImageUrlDefaul)
                        .map(Booking::getAccomId, GetHistoryBookingResponse::setImageUrl))
                .addMappings(mapper -> mapper.using(idBookingToIsReviewed)
                        .map(Booking::getId, GetHistoryBookingResponse::setReviewed));
    }

    public GetBookingResponse toGetBookingResponse(Booking booking) {
        GetBookingResponse getBookingResponse;
        getBookingResponse = mapper.map(booking, GetBookingResponse.class);
        return getBookingResponse;
    }

    public GetHistoryBookingResponse toGetHistoryBookingResponse(Booking booking) {
        GetHistoryBookingResponse getHistoryBookingResponset;
        getHistoryBookingResponset = mapper.map(booking, GetHistoryBookingResponse.class);
        return getHistoryBookingResponset;
    }
}
