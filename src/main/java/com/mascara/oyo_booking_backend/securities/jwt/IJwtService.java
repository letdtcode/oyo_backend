package com.mascara.oyo_booking_backend.securities.jwt;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.Map;
import java.util.function.Function;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 01/10/2023
 * Time      : 3:53 CH
 * Filename  : JwtService
 */
public interface IJwtService {
    String extractUsername(String token);

    Date extractExpiration(String token);

    String extractRoles(String token);

    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

    Claims extractAllClaims(String token);

    Boolean isTokenExpired(String token);

    String generateToken(UserDetails userDetail);

    String generateRefreshToken(UserDetails userDetail);

    String doGenerateToken(Map<String, Object> claims, String subject, Boolean isAccessToken);

    Boolean validateToken(String token, UserDetails userDetails);
}
