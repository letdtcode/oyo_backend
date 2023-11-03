package com.mascara.oyo_booking_backend.dtos.response.review;

import com.mascara.oyo_booking_backend.entities.AccomPlace;
import com.mascara.oyo_booking_backend.entities.ImageReview;
import com.mascara.oyo_booking_backend.entities.ReviewList;
import com.mascara.oyo_booking_backend.enums.ReviewStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.Set;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 03/11/2023
 * Time      : 2:16 SA
 * Filename  : GetListReviewOfAccomPlace
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetReviewResponse {
    private Long Id;
    private String title;
    private String content;
    private Float rateStar;
    private Boolean haveImage;
    private Set<ImageReview> imageReviewSet;
    private Long accomPlaceId;
    private Long reviewListId;
}
