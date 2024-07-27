package com.mascara.oyo_booking_backend.services.province;

import com.mascara.oyo_booking_backend.constant.MessageConstant;
import com.mascara.oyo_booking_backend.dtos.base.BaseMessageData;
import com.mascara.oyo_booking_backend.dtos.base.BasePagingData;
import com.mascara.oyo_booking_backend.dtos.location.response.locationDTO.GetProvinceResponse;
import com.mascara.oyo_booking_backend.dtos.location.response.locationDTO.UpdateProvinceResponse;
import com.mascara.oyo_booking_backend.dtos.province.request.AddProvinceRequest;
import com.mascara.oyo_booking_backend.dtos.province.request.UpdateProvinceRequest;
import com.mascara.oyo_booking_backend.entities.address.Province;
import com.mascara.oyo_booking_backend.exceptions.ResourceNotFoundException;
import com.mascara.oyo_booking_backend.mapper.address.ProvinceMapper;
import com.mascara.oyo_booking_backend.repositories.ProvinceRepository;
import com.mascara.oyo_booking_backend.utils.SlugsUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 22/10/2023
 * Time      : 7:02 CH
 * Filename  : ProvinceServiceImpl
 */
@Service
@RequiredArgsConstructor
public class ProvinceServiceImpl implements ProvinceService {
    private final ProvinceRepository provinceRepository;
    private final ProvinceMapper provinceMapper;

    @Override
    public List<Province> getAllProvinceDetails() {
        return provinceRepository.findAll();
    }

    @Override
    @Transactional
    public BaseMessageData addProvince(AddProvinceRequest request) {
        Province province = Province.builder()
                .provinceName(request.getProvinceName())
                .thumbnail(request.getThumbnailLink())
                .provinceCode(request.getProvinceCode())
                .divisionType(request.getDivisionType())
                .slugs(SlugsUtils.toSlug(request.getProvinceName()))
                .build();
        provinceRepository.save(province);
        return new BaseMessageData(MessageConstant.ADD_SUCCESS_MESSAGE("province"));
    }

    @Override
    @Transactional
    public UpdateProvinceResponse updateProvince(UpdateProvinceRequest request, Long id) {
        Province province = provinceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstant.NOT_FOUND_MESSAGE("province")));
        province.setProvinceName(request.getProvinceName());
        province.setThumbnail(request.getThumbnailLink());
        province.setSlugs(SlugsUtils.toSlug(request.getProvinceName()));
        province = provinceRepository.save(province);
        return provinceMapper.toUpdateProvinceResponse(province);
    }

    @Override
    @Transactional
    public BaseMessageData deleteProvince(Long id) {
        Province province = provinceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstant.NOT_FOUND_MESSAGE("province")));
        provinceRepository.delete(province);
        return new BaseMessageData(MessageConstant.UPDATE_SUCCESS_MESSAGE("Province"));
    }

    @Override
    @Transactional
    public BasePagingData<GetProvinceResponse> getTopProvinceByField(Integer pageNum,
                                                                     Integer pageSize,
                                                                     String sortType,
                                                                     String field) {
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.valueOf(sortType), field));
        Page<Province> provincePage = provinceRepository.getAllWithPaging(pageable);
        List<Province> provinceList = provincePage.stream().toList();
        List<GetProvinceResponse> responseList = provinceList.stream().map(province -> provinceMapper.toGetProvinceResponse(province))
                .collect(Collectors.toList());
        return new BasePagingData<>(responseList,
                provincePage.getNumber(),
                provincePage.getSize(),
                provincePage.getTotalElements());
    }
}
