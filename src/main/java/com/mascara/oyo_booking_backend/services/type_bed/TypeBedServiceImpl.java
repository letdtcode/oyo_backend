package com.mascara.oyo_booking_backend.services.type_bed;

import com.mascara.oyo_booking_backend.dtos.BaseMessageData;
import com.mascara.oyo_booking_backend.dtos.request.type_bed.AddTypeBedRequest;
import com.mascara.oyo_booking_backend.dtos.request.type_bed.UpdateTypeBedRequest;
import com.mascara.oyo_booking_backend.dtos.response.paging.BasePagingData;
import com.mascara.oyo_booking_backend.dtos.response.type_bed.GetTypeBedResponse;
import com.mascara.oyo_booking_backend.entities.type_bed.TypeBed;
import com.mascara.oyo_booking_backend.enums.CommonStatusEnum;
import com.mascara.oyo_booking_backend.exceptions.ResourceNotFoundException;
import com.mascara.oyo_booking_backend.repositories.TypeBedRepository;
import com.mascara.oyo_booking_backend.utils.AliasUtils;
import com.mascara.oyo_booking_backend.utils.AppContants;
import com.mascara.oyo_booking_backend.utils.GenerateCodeUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
public class TypeBedServiceImpl implements TypeBedService {

    @Autowired
    private TypeBedRepository typeBedRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    @Transactional
    public BasePagingData<GetTypeBedResponse> getAllTypeBedWithPaging(Integer pageNum, Integer pageSize, String sortType, String field) {
        Pageable paging = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.valueOf(sortType), field));
        Page<TypeBed> typeBedPage = typeBedRepository.getAllWithPaging(paging);
        List<TypeBed> typeBedList = typeBedPage.stream().toList();
        List<GetTypeBedResponse> responseList = typeBedList.stream().map(typeBed -> mapper.map(typeBed,
                GetTypeBedResponse.class)).collect(Collectors.toList());
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
        List<GetTypeBedResponse> responseList = typeBedList.stream().map(typeBed -> mapper.map(typeBed,
                GetTypeBedResponse.class)).collect(Collectors.toList());
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
                .typeBedCode(GenerateCodeUtils.generateCode(AliasUtils.TYPE_BED, count))
                .status(CommonStatusEnum.valueOf(request.getStatus()))
                .build();
        typeBed = typeBedRepository.save(typeBed);
        return mapper.map(typeBed, GetTypeBedResponse.class);
    }

    @Override
    @Transactional
    public GetTypeBedResponse updateTypeBed(UpdateTypeBedRequest request, Long id) {
        TypeBed typeBed = typeBedRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(AppContants.NOT_FOUND_MESSAGE("Type bed")));
        typeBed.setTypeBedName(request.getTypeBedName());
        typeBed.setStatus(CommonStatusEnum.valueOf(request.getStatus()));
        typeBed = typeBedRepository.save(typeBed);
        return mapper.map(typeBed, GetTypeBedResponse.class);
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
