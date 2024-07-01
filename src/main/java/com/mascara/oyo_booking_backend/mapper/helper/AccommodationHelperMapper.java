package com.mascara.oyo_booking_backend.mapper.helper;

import com.mascara.oyo_booking_backend.dtos.accom_place.response.PriceCustomForAccom;
import com.mascara.oyo_booking_backend.dtos.accom_place.response.RangeDateBooking;
import com.mascara.oyo_booking_backend.dtos.surcharge.surcharge_accom.response.GetSurchargeOfAccomResponse;
import com.mascara.oyo_booking_backend.entities.accommodation.AccomPlace;
import com.mascara.oyo_booking_backend.entities.accommodation.ImageAccom;
import com.mascara.oyo_booking_backend.entities.accommodation.PriceCustom;
import com.mascara.oyo_booking_backend.entities.accommodation.SurchargeOfAccom;
import com.mascara.oyo_booking_backend.entities.authentication.User;
import com.mascara.oyo_booking_backend.entities.booking.Booking;
import com.mascara.oyo_booking_backend.entities.surcharge.SurchargeCategory;
import com.mascara.oyo_booking_backend.exceptions.ResourceNotFoundException;
import com.mascara.oyo_booking_backend.repositories.*;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 17/06/2024
 * Time      : 9:41 CH
 * Filename  : AccommodationHelperMapper
 */
@Component
@RequiredArgsConstructor
public class AccommodationHelperMapper {
    private final AccomPlaceRepository accomPlaceRepository;
    private final ProvinceRepository provinceRepository;
    private final DistrictRepository districtRepository;
    private final SurchargeCategoryRepository surchargeCategoryRepository;
    private final SurchargeOfAccomRepository surchargeOfAccomRepository;
    private final BookingRepository bookingRepository;

    private final UserRepository userRepository;

    private final PriceCustomRepository priceCustomRepository;

    private final AccommodationCategoriesRepository accommodationCategoriesRepository;

    //    Covert list price custom
    @Named("idAccomPlaceToListPriceCustom")
    public List<PriceCustomForAccom> idAccomPlaceToListPriceCustom(Long idAccomPlace) {
        if (idAccomPlace != null) {
            AccomPlace accomPlace = accomPlaceRepository.findById(idAccomPlace).get();
            List<PriceCustomForAccom> priceCustomForAccoms = new LinkedList<>();
            List<PriceCustom> priceCustoms = priceCustomRepository.findByAccomId(accomPlace.getId());

            for (PriceCustom priceCustom : priceCustoms) {
                priceCustomForAccoms.add(PriceCustomForAccom.builder()
                        .priceApply(priceCustom.getPriceApply())
                        .dateApply(priceCustom.getDateApply())
                        .build());
            }
            return priceCustomForAccoms;
        }
        return null;
    }

    //    Covert list range date booking
    @Named("idAccomPlaceToListRangeDateBooking")
    public List<RangeDateBooking> idAccomPlaceToListRangeDateBooking(Long idAccomPlace) {
        if (idAccomPlace != null) {
            AccomPlace accomPlace = accomPlaceRepository.findById(idAccomPlace).get();
            List<RangeDateBooking> rangeDateBookings = new LinkedList<>();
            List<Booking> bookings = bookingRepository.findByAccomId(accomPlace.getId());

            for (Booking booking : bookings) {
                rangeDateBookings.add(RangeDateBooking.builder()
                        .nameCustomer(booking.getNameCustomer())
                        .dateStart(booking.getCheckIn())
                        .dateEnd(booking.getCheckOut())
                        .build());
            }
            return rangeDateBookings;
        }
        return null;
    }

    //    Covert Address General
    @Named("idAccomToGeneralAddress")
    public String idAccomToGeneralAddress(Long accomId) {
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
    }

    //    Covert Address General
    @Named("idAccomPlaceToListOfBookedDates")
    public List<LocalDate> idAccomPlaceToListOfBookedDates(Long idAccomPlace) {
        if (idAccomPlace != null) {
            LocalDate dateNow = LocalDate.now();
            List<Booking> bookingOfRangeDate = bookingRepository.findBookingByRangeDateStartFromCurrent(idAccomPlace, dateNow);
            List<LocalDate> datesBooked = new ArrayList<>();
            if (!bookingOfRangeDate.isEmpty() && bookingOfRangeDate != null) {
                for (Booking booking : bookingOfRangeDate) {
                    LocalDate currentDate = booking.getCheckIn();
                    while (!currentDate.isAfter(booking.getCheckOut()) && !datesBooked.contains(currentDate)) {
                        datesBooked.add(currentDate);
                        currentDate = currentDate.plusDays(1);
                    }
                }
            }
            return datesBooked;
        }
        return null;
    }

    @Named("idAccomPlaceToSurchargeList")
    public List<GetSurchargeOfAccomResponse> idAccomPlaceToSurchargeList(Long idAccomPlace) {
        if (idAccomPlace != null) {
            List<GetSurchargeOfAccomResponse> surchargeList = new ArrayList<>();
            List<SurchargeOfAccom> surchargeOfAccomList = surchargeOfAccomRepository.findByAccomPlaceId(idAccomPlace);
            if (surchargeOfAccomList != null && !surchargeOfAccomList.isEmpty()) {
                for (SurchargeOfAccom surcharge : surchargeOfAccomList) {
                    SurchargeCategory surchargeCate = surchargeCategoryRepository.findSurchargeCategoryById(surcharge.getSurchargeCateId()).get();
                    surchargeList.add(new GetSurchargeOfAccomResponse(surcharge.getCost(),
                            surchargeCate.getSurchargeCateName(),
                            surchargeCate.getSurchargeCode()));
                }
            }
            return surchargeList;
        }
        return null;
    }

    //    Covert Image Accom
    @Named("imageAccomToImageAccomUrl")
    public List<String> imageAccomToImageAccomUrl(Set<ImageAccom> imageAccomSet) {
        if (imageAccomSet != null) {
            List<String> imageAccomUrl = new ArrayList();
            for (ImageAccom imgAccom : imageAccomSet) {
                imageAccomUrl.add(imgAccom.getImgAccomLink());
            }
            return imageAccomUrl;
        }
        return null;
    }

//    //    Covert Image Accom
//    @Named("imageAccomToImageAccomUrl")
//    public List<String> idAccomToImageAccomUrl(Long accomId) {
//        if (accomId != null) {
//            List<String> imageAccomUrl = new ArrayList();
//            AccomPlace accomPlace = accomPlaceRepository.findById(accomId).get();
//            for (ImageAccom imgAccom : accomPlace.getImageAccoms()) {
//                imageAccomUrl.add(imgAccom.getImgAccomLink());
//            }
//            return imageAccomUrl;
//        }
//        return null;
//    }

    //    Covert id accom place to name accom place
    @Named("idAccomPlaceToNameAccom")
    public String idAccomPlaceToNameAccom(Long accomId) {
        if (accomId != null) {
            AccomPlace accomPlace = accomPlaceRepository.findById(accomId).get();
            return accomPlace.getAccomName();
        }
        return null;
    }

//    //    Covert id accom place to name accom place
//    @Named("idAccomPlaceToNameAccom")
//    public String idAccomPlaceToAccomCateName(Long accomId) {
//        if (accomId != null) {
//            AccomPlace accomPlace = accomPlaceRepository.findById(accomId).get();
//            return accomPlace.getAccommodationCategories().getAccomCateName();
//        }
//        return null;
//    }

    //        Covert accom place id to cancellation policy
    @Named("accomPlaceIdToCancellationPolicy")
    public String accomPlaceIdToCancellationPolicy(Long accomId) {
        if (accomId != null) {
            AccomPlace accomPlace = accomPlaceRepository.findById(accomId).get();
            return accomPlace.getCancellationPolicy().toString();
        }
        return null;
    }

    //        Covert accom place id to cancellation fee rate
    @Named("accomPlaceIdToCancellationFeeRate")
    public Integer accomPlaceIdToCancellationFeeRate(Long accomId) {
        if (accomId != null) {
            AccomPlace accomPlace = accomPlaceRepository.findById(accomId).get();
            return accomPlace.getCancellationFeeRate();
        }
        return null;
    }

    @Named("idAccomToFullNameHost")
    public String idAccomToFullNameHost(Long accomId) {
        if (accomId != null) {
            Long hostId = accomPlaceRepository.findById(accomId).get().getUserId();
            User host = userRepository.findByUserId(hostId).get();
            return host.getFirstName() + " " + host.getLastName();
        }
        return null;
    }

    @Named("idAccomToPricePerNight")
    public Double idAccomToPricePerNight(Long accomId) {
        if (accomId != null) {
            AccomPlace accomPlace = accomPlaceRepository.findById(accomId)
                    .orElseThrow(() -> new ResourceNotFoundException("Accom place"));
            return accomPlace.getPricePerNight();
        }
        return null;
    }

    @Named("idAccomToImageUrlDefaul")
    public String idAccomToImageUrlDefaul(Long accomId) {
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
    }
}


