package com.mascara.oyo_booking_backend.dtos.response.paging;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 25/11/2023
 * Time      : 1:03 CH
 * Filename  : BasePagingData
 */
@Data
public class BasePagingData<T> implements Serializable {
    private final List<T> content;
    private final int pageNumber;
    private final int pageSize;
    private final long totalElements;

    public BasePagingData(List<T> content, int pageNumber, int pageSize, long totalElements) {
        this.content = content;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
    }
}
