package com.mascara.oyo_booking_backend.mapper.accommodation;

import com.mascara.oyo_booking_backend.dtos.accom_place.response.GetAccomPlaceDetailResponse;
import com.mascara.oyo_booking_backend.dtos.accom_place.response.GetAccomPlaceResponse;
import com.mascara.oyo_booking_backend.dtos.accom_place.response.GetPriceCustomAccomResponse;
import com.mascara.oyo_booking_backend.dtos.accom_place.response.GetRangeDateBookingAccomResponse;
import com.mascara.oyo_booking_backend.entities.accommodation.AccomPlace;
import com.mascara.oyo_booking_backend.mapper.helper.AccommodationHelperMapper;
import com.mascara.oyo_booking_backend.mapper.helper.FacilityHelperMapper;
import com.mascara.oyo_booking_backend.mapper.helper.TypeBedHelperMapper;
import com.mascara.oyo_booking_backend.mapper.helper.UserHelperMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 17/06/2024
 * Time      : 6:08 CH
 * Filename  : AccomPlaceMapperMapstruts
 */

@Mapper(componentModel = "spring", uses = {AccommodationHelperMapper.class,
        UserHelperMapper.class,
        FacilityHelperMapper.class, TypeBedHelperMapper.class})
public interface AccomPlaceMapper {

    @Mapping(source = "id", target = "addressGeneral", qualifiedByName = "idAccomToGeneralAddress")
    @Mapping(source = "imageAccoms", target = "imageAccomsUrls", qualifiedByName = "imageAccomToImageAccomUrl")
    @Mapping(source = "accommodationCategories.accomCateName", target = "accomCateName")
    @Mapping(source = "userId", target = "nameHost", qualifiedByName = "userIdToNameHost")
    GetAccomPlaceResponse toGetAccomPlaceResponse(AccomPlace accomPlace);


    @Mapping(source = "id", target = "priceCustomForAccomList", qualifiedByName = "idAccomPlaceToListPriceCustom")
    @Mapping(source = "id", target = "accomId")
    GetPriceCustomAccomResponse toGetPriceCustomAccom(AccomPlace accomPlace);


    @Mapping(source = "id", target = "rangeDateBookingList", qualifiedByName = "idAccomPlaceToListRangeDateBooking")
    @Mapping(source = "id", target = "accomId")
    GetRangeDateBookingAccomResponse toGetRangeDateBookingAccomResponse(AccomPlace accomPlace);


    @Mapping(source = "id", target = "addressGeneral", qualifiedByName = "idAccomToGeneralAddress")
    @Mapping(source = "imageAccoms", target = "imageAccomsUrls", qualifiedByName = "imageAccomToImageAccomUrl")
    @Mapping(source = "facilitySet", target = "facilityCategoryList", qualifiedByName = "setFacilityToFacilityCateDetails")
    @Mapping(source = "accommodationCategories.accomCateName", target = "accomCateName")
    @Mapping(source = "userId", target = "nameHost", qualifiedByName = "userIdToNameHost")
    @Mapping(source = "userId", target = "hostMail", qualifiedByName = "userIdToHostMail")
    @Mapping(source = "id", target = "surchargeList", qualifiedByName = "idAccomPlaceToSurchargeList")
    @Mapping(source = "bedRoomSet", target = "bedRooms", qualifiedByName = "setBedRoomToNameTypeBed")
    @Mapping(source = "id", target = "bookedDates", qualifiedByName = "idAccomPlaceToListOfBookedDates")
    GetAccomPlaceDetailResponse toGetAccomPlaceDetailResponse(AccomPlace accomPlace);
}
