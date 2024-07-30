package com.mascara.oyo_booking_backend.common.constant;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 30/07/2024
 * Time      : 5:40 CH
 * Filename  : SecurityConstants
 */
public interface SecurityConstants {
    String[] ADMIN_API_PATHS = {
            "/api/v1/cms/**"
    };

    String[] CLIENT_API_PATHS = {
            "/api/v1/client/accom-place/**",
            "/api/v1/client/booking/**",
            "/api/v1/client/notification/**",
            "/api/v1/client/reviews/**",
            "/api/v1/client/wish/**"
    };

    String[] PARTNER_API_PATHS = {
            "/api/v1/partner/accoms/**",
            "/api/v1/partner/booking/**",
            "/api/v1/partner/statistic/**"
    };

    String[] GENERAL_API_PATHS = {
            "/api/v1/general/**",
            "/api/v1/media/cloud/**"
    };
    String[] IGNORING_API_PATHS = {
            "/api/v1/auth/**",
            "/ws/**",
            "/api/v1/client/booking/success-payment",
            "/api/v1/client/booking/cancel-payment",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html"
    };

    interface Role {
        String ADMIN = "ADMIN";

        String PARTNER = "PARTNER";

        String CLIENT = "CLIENT";
    }

}
