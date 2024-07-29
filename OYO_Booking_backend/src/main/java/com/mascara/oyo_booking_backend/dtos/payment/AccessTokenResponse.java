package com.mascara.oyo_booking_backend.dtos.payment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.Instant;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 28/06/2024
 * Time      : 9:18 CH
 * Filename  : AccessTokenResponse
 */
@Data
public class AccessTokenResponse {
    @JsonProperty("scope")
    private String scope;
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("token_type")
    private String tokenType;
    @JsonProperty("app_id")
    private String applicationId;
    @JsonProperty("expires_in")
    private int expiresIn;
    @JsonProperty("nonce")
    private String nonce;
    @JsonIgnore
    private Instant expiration;
}
