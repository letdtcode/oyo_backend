package com.mascara.oyo_booking_backend.common.mapper.helper;

import com.mascara.oyo_booking_backend.dtos.type_bed.response.GetTypeBedResponse;
import com.mascara.oyo_booking_backend.entities.accommodation.BedRoom;
import com.mascara.oyo_booking_backend.entities.type_bed.TypeBed;
import com.mascara.oyo_booking_backend.repositories.TypeBedRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 17/06/2024
 * Time      : 9:42 CH
 * Filename  : TypeBedHelperMapper
 */
@Component
@RequiredArgsConstructor
public class TypeBedHelperMapper {
    private final TypeBedRepository typeBedRepository;

    //    Covert bed rooms
    @Named("setBedRoomToNameTypeBed")
    public List<GetTypeBedResponse> setBedRoomToNameTypeBed(Set<BedRoom> bedRoomSet) {
        if (bedRoomSet != null) {
            List<GetTypeBedResponse> bedNameList = new ArrayList<>();
            for (BedRoom bedRoom : bedRoomSet) {
                String typeBedCode = bedRoom.getTypeBedCode();
                TypeBed typeBed = typeBedRepository.findByTypeBedCode(typeBedCode).get();
                GetTypeBedResponse bedName = typeBedToGetTypeBedResponse(typeBed);
                bedNameList.add(bedName);
            }
            return bedNameList;
        }
        return null;
    }

    private GetTypeBedResponse typeBedToGetTypeBedResponse(TypeBed typeBed) {
        return GetTypeBedResponse.builder()
                .id(typeBed.getId())
                .typeBedName(typeBed.getTypeBedName())
                .typeBedCode(typeBed.getTypeBedCode())
                .status(typeBed.getStatus().toString())
                .build();
    }
}
