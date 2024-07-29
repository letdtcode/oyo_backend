package com.mascara.oyo_booking_backend.specifications.criteria;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 10/12/2023
 * Time      : 4:57 CH
 * Filename  : SearchCriteria
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchCriteria {
    private String key;
    private Object value;
    private SearchOperation operation;
}
