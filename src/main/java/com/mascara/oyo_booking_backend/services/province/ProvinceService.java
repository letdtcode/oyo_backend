package com.mascara.oyo_booking_backend.services.province;

import com.mascara.oyo_booking_backend.dtos.request.province.AddProvinceRequest;
import com.mascara.oyo_booking_backend.dtos.request.province.UpdateProvinceRequest;
import com.mascara.oyo_booking_backend.dtos.response.MessageResponse;
import com.mascara.oyo_booking_backend.dtos.response.province.UpdateProvinceResponse;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 22/10/2023
 * Time      : 7:02 CH
 * Filename  : ProvinceService
 */
public interface ProvinceService {
    @Transactional
    MessageResponse addProvince(AddProvinceRequest request);

    @Transactional
    UpdateProvinceResponse updateProvince(UpdateProvinceRequest request, String provinceName);

    @Transactional
    MessageResponse deleteProvince(String provinceName);
}
