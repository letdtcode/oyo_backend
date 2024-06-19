package com.mascara.oyo_booking_backend.mapper.booking;

import com.mascara.oyo_booking_backend.dtos.booking.request.BookingRequest;
import com.mascara.oyo_booking_backend.dtos.booking.response.GetBookingResponse;
import com.mascara.oyo_booking_backend.dtos.booking.response.GetHistoryBookingResponse;
import com.mascara.oyo_booking_backend.entities.booking.Booking;
import com.mascara.oyo_booking_backend.mapper.helper.AccommodationHelperMapper;
import com.mascara.oyo_booking_backend.mapper.helper.BookingHelperMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 17/06/2024
 * Time      : 6:03 CH
 * Filename  : BookingMapper
 */

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {BookingHelperMapper.class, AccommodationHelperMapper.class})
public interface BookingMapper {

    @Mapping(source = "accomId", target = "nameAccom", qualifiedByName = "idAccomPlaceToNameAccom")
    @Mapping(source = "id", target = "originPay", qualifiedByName = "bookingIdToOriginPay")
    @Mapping(source = "id", target = "surcharge", qualifiedByName = "bookingIdToSurchargePay")
    @Mapping(source = "id", target = "totalBill", qualifiedByName = "bookingIdToTotalBill")
    @Mapping(source = "id", target = "totalTransfer", qualifiedByName = "bookingIdToTotalTransfer")
    @Mapping(source = "id", target = "paymentPolicy", qualifiedByName = "bookingIdToPaymentPolicy")
    @Mapping(source = "id", target = "paymentMethod", qualifiedByName = "bookingIdToPaymentMethod")
    @Mapping(source = "id", target = "adminEarning", qualifiedByName = "bookingIdToAdminEarning")
    @Mapping(source = "id", target = "partnerEarning", qualifiedByName = "bookingIdToPartnerEarning")
    GetBookingResponse toGetBookingResponse(Booking booking);

    @Mapping(source = "accomId", target = "fullNameHost", qualifiedByName = "idAccomToFullNameHost")
    @Mapping(source = "accomId", target = "nameAccom", qualifiedByName = "idAccomPlaceToNameAccom")
    @Mapping(source = "accomId", target = "generalAddress", qualifiedByName = "idAccomToGeneralAddress")
    @Mapping(source = "accomId", target = "imageUrl", qualifiedByName = "idAccomToImageUrlDefaul")
    @Mapping(source = "id", target = "originPay", qualifiedByName = "bookingIdToOriginPay")
    @Mapping(source = "id", target = "surcharge", qualifiedByName = "bookingIdToSurchargePay")
    @Mapping(source = "id", target = "totalBill", qualifiedByName = "bookingIdToTotalBill")
    @Mapping(source = "id", target = "totalTransfer", qualifiedByName = "bookingIdToTotalTransfer")
    @Mapping(source = "accomId", target = "pricePerNight", qualifiedByName = "idAccomToPricePerNight")
    @Mapping(source = "id", target = "paymentPolicy", qualifiedByName = "bookingIdToPaymentPolicy")
    @Mapping(source = "id", target = "paymentMethod", qualifiedByName = "bookingIdToPaymentMethod")
    @Mapping(source = "accomId", target = "cancellationPolicy", qualifiedByName = "accomPlaceIdToCancellationPolicy")
    @Mapping(source = "accomId", target = "cancellationFeeRate", qualifiedByName = "accomPlaceIdToCancellationFeeRate")
    @Mapping(source = "id", target = "reviewed", qualifiedByName = "idBookingToIsReviewed")
    GetHistoryBookingResponse toGetHistoryBookingResponse(Booking booking);

    Booking toEntity(BookingRequest request);
}
