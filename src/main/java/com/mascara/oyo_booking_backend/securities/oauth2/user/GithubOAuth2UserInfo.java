package com.mascara.oyo_booking_backend.securities.oauth2.user;

import java.util.Map;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 17/12/2023
 * Time      : 5:34 CH
 * Filename  : GithubOAuth2UserInfo
 */
public class GithubOAuth2UserInfo extends OAuth2UserInfo {

    public GithubOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return ((Integer) attributes.get("id")).toString();
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getImageUrl() {
        return (String) attributes.get("avatar_url");
    }
}
