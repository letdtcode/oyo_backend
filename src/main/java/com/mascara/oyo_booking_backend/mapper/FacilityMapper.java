package com.mascara.oyo_booking_backend.mapper;

import com.mascara.oyo_booking_backend.dtos.response.booking.GetBookingResponse;
import com.mascara.oyo_booking_backend.dtos.response.booking.GetHistoryBookingResponse;
import com.mascara.oyo_booking_backend.dtos.response.facility.GetFacilityResponse;
import com.mascara.oyo_booking_backend.entities.Booking;
import com.mascara.oyo_booking_backend.entities.Facility;
import com.mascara.oyo_booking_backend.entities.FacilityCategories;
import com.mascara.oyo_booking_backend.repositories.FacilityCategoriesRepository;
import jakarta.annotation.PostConstruct;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 08/12/2023
 * Time      : 3:55 CH
 * Filename  : FacilityMapper
 */
@Component
public class FacilityMapper {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private FacilityCategoriesRepository facilityCategoriesRepository;

    //    Covert facility cate code to facility cate name
    private final Converter<String, String> facilityCateCodeToFacilityCateName = context -> {
        String facilityCateCode = context.getSource();
        if (facilityCateCode != null) {
            FacilityCategories facilityCategories = facilityCategoriesRepository.findByFaciCateCode(facilityCateCode).get();
            return facilityCategories.getFaciCateName();
        }
        return null;
    };

    @PostConstruct
    public void init() {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        mapper.createTypeMap(Facility.class, GetFacilityResponse.class)
                .addMappings(mapper -> mapper.using(facilityCateCodeToFacilityCateName)
                        .map(Facility::getFacilityCateCode, GetFacilityResponse::setFacilityCateName));
    }

    public GetFacilityResponse toGetFacilityResponse(Facility facility) {
        GetFacilityResponse getFacilityResponse;
        getFacilityResponse = mapper.map(facility, GetFacilityResponse.class);
        return getFacilityResponse;
    }
}
