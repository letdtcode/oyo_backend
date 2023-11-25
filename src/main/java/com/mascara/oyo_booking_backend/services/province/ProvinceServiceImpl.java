package com.mascara.oyo_booking_backend.services.province;

import com.mascara.oyo_booking_backend.dtos.request.province.AddProvinceRequest;
import com.mascara.oyo_booking_backend.dtos.request.province.UpdateProvinceRequest;
import com.mascara.oyo_booking_backend.dtos.response.location.UpdateProvinceResponse;
import com.mascara.oyo_booking_backend.entities.Province;
import com.mascara.oyo_booking_backend.entities.Ward;
import com.mascara.oyo_booking_backend.exceptions.ResourceNotFoundException;
import com.mascara.oyo_booking_backend.repositories.DistrictRepository;
import com.mascara.oyo_booking_backend.repositories.ProvinceRepository;
import com.mascara.oyo_booking_backend.repositories.WardRepository;
import com.mascara.oyo_booking_backend.utils.AppContants;
import com.mascara.oyo_booking_backend.utils.SlugsUtils;
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
    private DistrictRepository districtRepository;

    @Autowired
    private WardRepository wardRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public List<Province> getAllProvinceDetails() {
        List<Ward> wardList = wardRepository.findAll();
        return provinceRepository.findAll();
    }

    @Override
    @Transactional
    public String addProvince(AddProvinceRequest request) {
        Province province = Province.builder()
                .provinceName(request.getProvinceName())
                .thumbnail(request.getThumbnailLink())
                .provinceCode(request.getProvinceCode())
                .divisionType(request.getDivisionType())
                .slugs(SlugsUtils.toSlug(request.getProvinceName()))
                .build();
        provinceRepository.save(province);
        return "Add Province Success !";
    }

    @Override
    @Transactional
    public UpdateProvinceResponse updateProvince(UpdateProvinceRequest request, String provinceSlug) {
        Province province = provinceRepository.findByProvinceSlugs(provinceSlug)
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("province")));
        province.setProvinceName(request.getProvinceName());
        province.setThumbnail(request.getThumbnailLink());
        province = provinceRepository.save(province);
        return mapper.map(province, UpdateProvinceResponse.class);
    }

    @Override
    @Transactional
    public String deleteProvince(String provinceName) {
        Province province = provinceRepository.findByProvinceName(provinceName)
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("province")));
        provinceRepository.delete(province);
        return "Province Success !";
    }
}
