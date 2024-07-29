package com.mascara.oyo_booking_backend.securities.oauth2.user;

import java.util.Map;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 17/12/2023
 * Time      : 5:33 CH
 * Filename  : OAuth2UserInfo
 */
public abstract class OAuth2UserInfo {
    protected Map<String, Object> attributes;

    public OAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public abstract String getId();

    public abstract String getName();

    public abstract String getEmail();

    public abstract String getImageUrl();
}
