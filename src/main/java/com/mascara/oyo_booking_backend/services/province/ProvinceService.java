package com.mascara.oyo_booking_backend.services.province;

import com.mascara.oyo_booking_backend.dtos.BaseMessageData;
import com.mascara.oyo_booking_backend.dtos.request.province.AddProvinceRequest;
import com.mascara.oyo_booking_backend.dtos.request.province.UpdateProvinceRequest;
import com.mascara.oyo_booking_backend.dtos.response.location.GetProvinceResponse;
import com.mascara.oyo_booking_backend.dtos.response.location.UpdateProvinceResponse;
import com.mascara.oyo_booking_backend.dtos.response.paging.BasePagingData;
import com.mascara.oyo_booking_backend.entities.address.Province;
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
    BaseMessageData addProvince(AddProvinceRequest request);

    @Transactional
    UpdateProvinceResponse updateProvince(UpdateProvinceRequest request, Long id);

    @Transactional
    BaseMessageData deleteProvince(Long id);

    @Transactional
    BasePagingData<GetProvinceResponse> getTopProvinceByField(Integer pageNum,
                                                              Integer pageSize,
                                                              String sortType,
                                                              String field);
}
