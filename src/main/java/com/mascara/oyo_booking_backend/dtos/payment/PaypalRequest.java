package com.mascara.oyo_booking_backend.dtos.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mascara.oyo_booking_backend.dtos.payment.enums.OrderIntent;
import com.mascara.oyo_booking_backend.dtos.payment.enums.PaymentLandingPage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 29/06/2024
 * Time      : 1:15 SA
 * Filename  : PaypalRequest
 */
@Data
public class PaypalRequest implements Serializable {
    @JsonProperty("intent")
    private OrderIntent intent;
    @JsonProperty("purchase_units")
    private List<PurchaseUnit> purchaseUnits;
    @JsonProperty("application_context")
    private PayPalAppContext applicationContext;

    @Data
    @AllArgsConstructor
    public static class PurchaseUnit {
        @JsonProperty("amount")
        private Money amount;

        @Data
        @AllArgsConstructor
        public static class Money {
            @JsonProperty("currency_code")
            private String currencyCode;
            @JsonProperty("value")
            private String value;
        }
    }

    @Data
    @Accessors(chain = true)
    public static class PayPalAppContext {
        @JsonProperty("brand_name")
        private String brandName;
        @JsonProperty("landing_page")
        private PaymentLandingPage landingPage;
        @JsonProperty("return_url")
        private String returnUrl;
        @JsonProperty("cancel_url")
        private String cancelUrl;
    }
}
