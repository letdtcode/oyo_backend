package com.mascara.oyo_booking_backend.services.province;

import com.mascara.oyo_booking_backend.dtos.request.province.AddProvinceRequest;
import com.mascara.oyo_booking_backend.dtos.request.province.UpdateProvinceRequest;
import com.mascara.oyo_booking_backend.dtos.response.general.MessageResponse;
import com.mascara.oyo_booking_backend.dtos.response.province.UpdateProvinceResponse;
import com.mascara.oyo_booking_backend.entities.Province;
import com.mascara.oyo_booking_backend.exceptions.ResourceNotFoundException;
import com.mascara.oyo_booking_backend.repositories.ProvinceRepository;
import com.mascara.oyo_booking_backend.utils.AppContants;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 22/10/2023
 * Time      : 7:02 CH
 * Filename  : ProvinceServiceImpl
 */
@Service
public class ProvinceServiceImpl implements ProvinceService {

    @Autowired
    private ProvinceRepository provinceRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public List<Province> getAllProvinceDetails() {
        return provinceRepository.findAll();
    }

    @Override
    @Transactional
    public MessageResponse addProvince(AddProvinceRequest request) {
        Province province = Province.builder()
                .provinceName(request.getProvinceName())
                .thumbnail(request.getThumbnailLink()).build();
        provinceRepository.save(province);
        return new MessageResponse("Add Province Success !");
    }

    @Override
    @Transactional
    public UpdateProvinceResponse updateProvince(UpdateProvinceRequest request, String provinceName) {
        Province province = provinceRepository.findByProvinceName(provinceName)
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.PROVINCE_NOT_FOUND));
        province.setProvinceName(request.getProvinceName());
        province.setThumbnail(request.getThumbnailLink());
        province = provinceRepository.save(province);
        return mapper.map(province, UpdateProvinceResponse.class);
    }

    @Override
    @Transactional
    public MessageResponse deleteProvince(String provinceName) {
        Province province = provinceRepository.findByProvinceName(provinceName)
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.PROVINCE_NOT_FOUND));
        provinceRepository.delete(province);
        return new MessageResponse("Province Success !");
    }
}
