package com.mascara.oyo_booking_backend.config.init_data.models;

import lombok.Data;

import java.util.List;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 25/10/2023
 * Time      : 2:21 CH
 * Filename  : InitDbModel
 */
@Data
public class InitDbModel<T> {
    private List<T> data;
}
