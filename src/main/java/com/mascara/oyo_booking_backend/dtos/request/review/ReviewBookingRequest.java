package com.mascara.oyo_booking_backend.dtos.request.review;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class ReviewBookingRequest {

    @NotNull
    @NotBlank
    private String title;
    @NotNull
    @NotBlank
    private String content;
    @NotNull
    private Float rateStar;
    private List<String> imagesUrls;
    @NotNull
    private String bookingCode;
}
