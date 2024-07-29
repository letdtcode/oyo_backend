package com.mascara.oyo_booking_backend.securities.oauth2.user;

import com.mascara.oyo_booking_backend.common.enums.user.AuthProviderEnum;
import com.mascara.oyo_booking_backend.common.exceptions.OAuth2AuthenticationProcessingException;

import java.util.Map;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 17/12/2023
 * Time      : 5:34 CH
 * Filename  : OAuth2UserInfoFactory
 */
public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if(registrationId.equalsIgnoreCase(AuthProviderEnum.google.toString())) {
            return new GoogleOAuth2UserInfo(attributes);
        } else if (registrationId.equalsIgnoreCase(AuthProviderEnum.facebook.toString())) {
            return new FacebookOAuth2UserInfo(attributes);
        } else if (registrationId.equalsIgnoreCase(AuthProviderEnum.github.toString())) {
            return new GithubOAuth2UserInfo(attributes);
        } else {
            throw new OAuth2AuthenticationProcessingException("Sorry! Login with " + registrationId + " is not supported yet.");
        }
    }
}
