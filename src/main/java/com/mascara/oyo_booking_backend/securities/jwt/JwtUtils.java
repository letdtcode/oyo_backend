package com.mascara.oyo_booking_backend.securities.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 01/10/2023
 * Time      : 3:54 CH
 * Filename  : JwtServiceImpl
 */

@Component
@Slf4j
public class JwtUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${jwt.secret}")
    private String secret;
    private final long JWT_EXPIRATION = 1 * 60 * 1000;
    private final long REFRESH_JWT_EXPIRATION = 3 * 60 * 1000;

    public String generateAccessJwtToken(String email, Set<String> roles) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", roles);
        return doGenerateToken(claims, email, JWT_EXPIRATION);
    }

    public String generateRefreshJwtToken(String email) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, email, REFRESH_JWT_EXPIRATION);
    }

    private String doGenerateToken(Map<String, Object> claims, String subject, long expiredTime) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        Key key = Keys.hmacShaKeyFor(keyBytes);
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredTime))
                .signWith(key, SignatureAlgorithm.HS256).compact();
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Set<String> getRolesFromToken(String token) {
        List<String> roleNames = getClaimFromToken(token, claims -> (List<String>) claims.get("role"));
        return roleNames != null ? roleNames.stream().collect(Collectors.toSet()) : null;
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        Key key = Keys.hmacShaKeyFor(keyBytes);
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        Set<String> rolesNameFromToken = getRolesFromToken(token);
        Set<String> rolesNameFromUserDetails = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority()).collect(Collectors.toSet());
        boolean isRoleValid = rolesNameFromToken != null ? rolesNameFromToken.equals(rolesNameFromUserDetails) : false;
        boolean isUserNameValid = username.equals(userDetails.getUsername());
        return (isUserNameValid && !isTokenExpired(token) && isRoleValid);
    }
}
