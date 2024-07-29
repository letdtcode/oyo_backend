package com.mascara.oyo_booking_backend.config.init_data.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 16/07/2024
 * Time      : 11:37 CH
 * Filename  : InitImageAccomModel
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InitImageAccomModel {
    private Long accomPlaceId;
    private List<String> imgAccomLink;
}
