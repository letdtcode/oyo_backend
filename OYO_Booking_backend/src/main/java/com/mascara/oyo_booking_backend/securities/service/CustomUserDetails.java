package com.mascara.oyo_booking_backend.securities.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mascara.oyo_booking_backend.entities.authentication.User;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 01/10/2023
 * Time      : 4:23 CH
 * Filename  : CustomUserDetails
 */
@Service
@NoArgsConstructor
@Slf4j
public class CustomUserDetails implements OAuth2User, UserDetails {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String username;

    private String email;

    @JsonIgnore
    private String password;

    private String firstName;

    private Map<String, Object> attributes;

    private Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(Long id, String username, String email, String password,
                             Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static CustomUserDetails build(User user) {
        List<GrantedAuthority> authorities = user.getRoleSet().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName().name()))
                .collect(Collectors.toList());
        return new CustomUserDetails(
                user.getId(),
                user.getUserName(),
                user.getMail(),
                user.getPassword(),
                authorities);
    }

    public static CustomUserDetails build(User user, Map<String, Object> attributes) {
        CustomUserDetails customUserDetails = CustomUserDetails.build(user);
        customUserDetails.setAttributes(attributes);
        return customUserDetails;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        CustomUserDetails user = (CustomUserDetails) o;
        log.error(id.toString());
        log.error(user.id.toString());
        return Objects.equals(id, user.id);
    }

    @Override
    public String getName() {
        return firstName;
    }
}
