package com.mascara.oyo_booking_backend.dtos.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 28/06/2024
 * Time      : 9:18 CH
 * Filename  : ClientTokenResponse
 */
@Data
public class ClientTokenResponse {
    @JsonProperty("client_token")
    private String clientToken;
    @JsonProperty("expires_in")
    private Long expiresIn;
}
