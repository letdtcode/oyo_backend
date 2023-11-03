package com.mascara.oyo_booking_backend.dtos.request.review;

import com.mascara.oyo_booking_backend.entities.ImageReview;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 04/11/2023
 * Time      : 2:36 SA
 * Filename  : ReviewAccomPlaceRequest
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewAccomPlaceRequest {
    private String title;
    private String content;
    private Float rateStar;
    private Boolean haveImage;
    private Long accomPlaceId;
    private Long userId;
}
