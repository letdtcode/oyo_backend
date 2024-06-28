package com.mascara.oyo_booking_backend.dtos.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mascara.oyo_booking_backend.enums.BookingStatusEnum;
import lombok.Data;

import java.util.List;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 28/06/2024
 * Time      : 9:19 CH
 * Filename  : PaypalResponse
 */
@Data
public class PaypalResponse {
    @JsonProperty("id")
    private String id;
    @JsonProperty("status")
    private BookingStatusEnum status;
    @JsonProperty("links")
    private List<Link> links;

    @Data
    public static class Link {
        @JsonProperty("href")
        private String href;
        @JsonProperty("rel")
        private String rel;
        @JsonProperty("method")
        private String method;
    }
}
