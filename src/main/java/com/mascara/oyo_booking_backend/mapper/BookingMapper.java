package com.mascara.oyo_booking_backend.mapper;

import com.mascara.oyo_booking_backend.dtos.booking.response.GetBookingResponse;
import com.mascara.oyo_booking_backend.dtos.booking.response.GetHistoryBookingResponse;
import com.mascara.oyo_booking_backend.entities.accommodation.AccomPlace;
import com.mascara.oyo_booking_backend.entities.accommodation.ImageAccom;
import com.mascara.oyo_booking_backend.entities.authentication.User;
import com.mascara.oyo_booking_backend.entities.booking.Booking;
import com.mascara.oyo_booking_backend.entities.order.Payment;
import com.mascara.oyo_booking_backend.entities.review.Review;
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
    private PaymentRepository paymentRepository;

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

    //        Covert booking id to origin pay
    private final Converter<Long, Double> bookingIdToOriginPay = context -> {
        Long id = context.getSource();
        if (id != null) {
            Payment payment = paymentRepository.findById(id).get();
            return payment.getOriginPay();
        }
        return null;
    };

    //        Covert booking id to surcharge pay
    private final Converter<Long, Double> bookingIdToSurchargePay = context -> {
        Long id = context.getSource();
        if (id != null) {
            Payment payment = paymentRepository.findById(id).get();
            return payment.getSurchargePay();
        }
        return null;
    };

    //        Covert booking id to total bill
    private final Converter<Long, Double> bookingIdToTotalBill = context -> {
        Long id = context.getSource();
        if (id != null) {
            Payment payment = paymentRepository.findById(id).get();
            return payment.getTotalBill();
        }
        return null;
    };

    //        Covert booking id to total transfer
    private final Converter<Long, Double> bookingIdToTotalTransfer = context -> {
        Long id = context.getSource();
        if (id != null) {
            Payment payment = paymentRepository.findById(id).get();
            return payment.getTotalTransfer();
        }
        return null;
    };

    //        Covert booking id to payment policy
    private final Converter<Long, String> bookingIdToPaymentPolicy = context -> {
        Long id = context.getSource();
        if (id != null) {
            Payment payment = paymentRepository.findById(id).get();
            return payment.getPaymentPolicy().toString();
        }
        return null;
    };

    //        Covert booking id to origin pay
    private final Converter<Long, String> bookingIdToPaymentMethod = context -> {
        Long id = context.getSource();
        if (id != null) {
            Payment payment = paymentRepository.findById(id).get();
            return payment.getPaymentMethod().toString();
        }
        return null;
    };

    //        Covert accom place id to cancellation policy
    private final Converter<Long, String> accomPlaceIdToCancellationPolicy = context -> {
        Long id = context.getSource();
        if (id != null) {
            AccomPlace accomPlace = accomPlaceRepository.findById(id).get();
            return accomPlace.getCancellationPolicy().toString();
        }
        return null;
    };

    //        Covert accom place id to cancellation fee rate
    private final Converter<Long, Integer> accomPlaceIdToCancellationFeeRate = context -> {
        Long id = context.getSource();
        if (id != null) {
            AccomPlace accomPlace = accomPlaceRepository.findById(id).get();
            return accomPlace.getCancellationFeeRate();
        }
        return null;
    };

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

    private final Converter<Long, Double> idAccomToPricePerNight = context -> {
        Long accomId = context.getSource();
        if (accomId != null) {
            AccomPlace accomPlace = accomPlaceRepository.findById(accomId)
                    .orElseThrow(() -> new ResourceNotFoundException("Accom place"));
            return accomPlace.getPricePerNight();
        }
        return null;
    };


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
                        .map(Booking::getAccomId, GetBookingResponse::setNameAccom));

        mapper.createTypeMap(Booking.class, GetHistoryBookingResponse.class)
                .addMappings(mapper -> mapper.using(idAccomToFullNameHost)
                        .map(Booking::getAccomId, GetHistoryBookingResponse::setFullNameHost))
                .addMappings(mapper -> mapper.using(idAccomPlaceToNameAccom)
                        .map(Booking::getAccomId, GetHistoryBookingResponse::setNameAccom))
                .addMappings(mapper -> mapper.using(idAccomToGeneralAddress)
                        .map(Booking::getAccomId, GetHistoryBookingResponse::setGeneralAddress))
                .addMappings(mapper -> mapper.using(idAccomToImageUrlDefaul)
                        .map(Booking::getAccomId, GetHistoryBookingResponse::setImageUrl))

                .addMappings(mapper -> mapper.using(bookingIdToOriginPay)
                        .map(Booking::getId, GetHistoryBookingResponse::setOriginPay))
                .addMappings(mapper -> mapper.using(bookingIdToSurchargePay)
                        .map(Booking::getId, GetHistoryBookingResponse::setSurcharge))
                .addMappings(mapper -> mapper.using(bookingIdToTotalBill)
                        .map(Booking::getId, GetHistoryBookingResponse::setTotalBill))
                .addMappings(mapper -> mapper.using(bookingIdToTotalTransfer)
                        .map(Booking::getId, GetHistoryBookingResponse::setTotalTransfer))

                .addMappings(mapper -> mapper.using(idAccomToPricePerNight)
                        .map(Booking::getAccomId, GetHistoryBookingResponse::setPricePerNight))

                .addMappings(mapper -> mapper.using(bookingIdToPaymentPolicy)
                        .map(Booking::getId, GetHistoryBookingResponse::setPaymentPolicy))
                .addMappings(mapper -> mapper.using(bookingIdToPaymentMethod)
                        .map(Booking::getId, GetHistoryBookingResponse::setPaymentMethod))

                .addMappings(mapper -> mapper.using(accomPlaceIdToCancellationPolicy)
                        .map(Booking::getAccomId, GetHistoryBookingResponse::setCancellationPolicy))
                .addMappings(mapper -> mapper.using(accomPlaceIdToCancellationFeeRate)
                        .map(Booking::getAccomId, GetHistoryBookingResponse::setCancellationFeeRate))

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
