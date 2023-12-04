package com.mascara.oyo_booking_backend.dtos.response.review;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

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
    private List<String> imageReviewUrls;
    private Long accomPlaceId;
    private Long reviewListId;
    private String firstName;
    private String lastName;
    private String avatarUserUrl;
    private LocalDateTime createdDate;
}
