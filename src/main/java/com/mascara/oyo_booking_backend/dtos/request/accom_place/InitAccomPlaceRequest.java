//package com.mascara.oyo_booking_backend.dtos.request.accom_place;
//
//import jakarta.validation.constraints.Min;
//import jakarta.validation.constraints.NotNull;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
///**
// * Created by: IntelliJ IDEA
// * User      : boyng
// * Date      : 03/03/2024
// * Time      : 7:46 CH
// * Filename  : InitAccomPlaceRequest
// */
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//public class InitAccomPlaceRequest extends AddAccomPlaceRequest {
//    @NotNull
//    private String cancellationPolicy;
//
//    @NotNull
//    @Min(value = 0, message = "Cancellation fee rate should not be less than 0")
//    private Integer cancellationFeeRate;
//}
