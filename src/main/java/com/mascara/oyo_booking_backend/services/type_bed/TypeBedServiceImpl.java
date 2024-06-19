package com.mascara.oyo_booking_backend.services.type_bed;

import com.mascara.oyo_booking_backend.dtos.base.BaseMessageData;
import com.mascara.oyo_booking_backend.dtos.base.BasePagingData;
import com.mascara.oyo_booking_backend.dtos.type_bed.request.AddTypeBedRequest;
import com.mascara.oyo_booking_backend.dtos.type_bed.request.UpdateTypeBedRequest;
import com.mascara.oyo_booking_backend.dtos.type_bed.response.GetTypeBedResponse;
import com.mascara.oyo_booking_backend.entities.type_bed.TypeBed;
import com.mascara.oyo_booking_backend.enums.CommonStatusEnum;
import com.mascara.oyo_booking_backend.exceptions.ResourceNotFoundException;
import com.mascara.oyo_booking_backend.mapper.type_bed.TypeBedMapper;
import com.mascara.oyo_booking_backend.repositories.TypeBedRepository;
import com.mascara.oyo_booking_backend.utils.AliasUtils;
import com.mascara.oyo_booking_backend.utils.AppContants;
import com.mascara.oyo_booking_backend.utils.Utilities;
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
 * Date      : 25/11/2023
 * Time      : 5:25 CH
 * Filename  : TypeBedServiceImpl
 */
@Service
@RequiredArgsConstructor
public class TypeBedServiceImpl implements TypeBedService {
    private final TypeBedRepository typeBedRepository;
    private final TypeBedMapper typeBedMapper;

    @Override
    @Transactional
    public BasePagingData<GetTypeBedResponse> getAllTypeBedWithPaging(Integer pageNum, Integer pageSize, String sortType, String field) {
        Pageable paging = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.valueOf(sortType), field));
        Page<TypeBed> typeBedPage = typeBedRepository.getAllWithPaging(paging);
        List<TypeBed> typeBedList = typeBedPage.stream().toList();
        List<GetTypeBedResponse> responseList = typeBedList.stream()
                .map(typeBed -> typeBedMapper.toGetTypeBedResponse(typeBed))
                .collect(Collectors.toList());
        return new BasePagingData<>(responseList,
                typeBedPage.getNumber(),
                typeBedPage.getSize(),
                typeBedPage.getTotalElements());
    }

    @Override
    @Transactional
    public BasePagingData<GetTypeBedResponse> getAllTypeBedWithPagingByStatus(String status, Integer pageNum, Integer pageSize, String sortType, String field) {
        Pageable paging = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.valueOf(sortType), field));
        Page<TypeBed> typeBedPage = typeBedRepository.getAllWithPagingByStatus(status, paging);
        List<TypeBed> typeBedList = typeBedPage.stream().toList();
        List<GetTypeBedResponse> responseList = typeBedList.stream()
                .map(typeBed -> typeBedMapper.toGetTypeBedResponse(typeBed))
                .collect(Collectors.toList());
        return new BasePagingData<>(responseList,
                typeBedPage.getNumber(),
                typeBedPage.getSize(),
                typeBedPage.getTotalElements());
    }

    @Override
    @Transactional
    public GetTypeBedResponse addTypeBed(AddTypeBedRequest request) {
        int count = (int) typeBedRepository.count();
        TypeBed typeBed = TypeBed.builder()
                .typeBedName(request.getTypeBedName())
                .typeBedCode(Utilities.getInstance().generateCode(AliasUtils.TYPE_BED, count))
                .status(CommonStatusEnum.valueOf(request.getStatus()))
                .build();
        typeBed = typeBedRepository.save(typeBed);
        return typeBedMapper.toGetTypeBedResponse(typeBed);
    }

    @Override
    @Transactional
    public GetTypeBedResponse updateTypeBed(UpdateTypeBedRequest request, Long id) {
        TypeBed typeBed = typeBedRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("Type bed")));
        typeBed.setTypeBedName(request.getTypeBedName());
        typeBed.setStatus(CommonStatusEnum.valueOf(request.getStatus()));
        typeBed = typeBedRepository.save(typeBed);
        return typeBedMapper.toGetTypeBedResponse(typeBed);
    }

    @Override
    @Transactional
    public BaseMessageData changeStatusTypeBed(Long id, String status) {
        TypeBed typeBed = typeBedRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("Type bed")));
        typeBed.setStatus(CommonStatusEnum.valueOf(status));
        typeBedRepository.save(typeBed);
        return new BaseMessageData(AppContants.UPDATE_SUCCESS_MESSAGE("Type bed"));
    }

    @Override
    @Transactional
    public BaseMessageData deletedTypeBed(Long id) {
        TypeBed typeBed = typeBedRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("Type bed")));
        typeBed.setDeleted(true);
        typeBedRepository.save(typeBed);
        return new BaseMessageData(AppContants.DELETE_SUCCESS_MESSAGE("Type bed"));
    }
}
