package com.mascara.oyo_booking_backend.services.province;

import com.mascara.oyo_booking_backend.dtos.request.province.AddProvinceRequest;
import com.mascara.oyo_booking_backend.dtos.request.province.UpdateProvinceRequest;
import com.mascara.oyo_booking_backend.dtos.response.location.GetProvinceDetailResponse;
import com.mascara.oyo_booking_backend.dtos.response.location.UpdateProvinceResponse;
import com.mascara.oyo_booking_backend.entities.Province;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 22/10/2023
 * Time      : 7:02 CH
 * Filename  : ProvinceService
 */
public interface ProvinceService {
    List<Province> getAllProvinceDetails();

    @Transactional
    String addProvince(AddProvinceRequest request);

    @Transactional
    UpdateProvinceResponse updateProvince(UpdateProvinceRequest request, String provinceName);

    @Transactional
    String deleteProvince(String provinceName);
}
