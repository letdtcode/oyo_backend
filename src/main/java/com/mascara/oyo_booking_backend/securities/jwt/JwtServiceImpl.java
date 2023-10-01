package com.mascara.oyo_booking_backend.securities.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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
public class JwtServiceImpl implements Serializable, JwtService {
    private static final String SECRET_KEY = "thisissecret";
    private final long JWT_EXPIRATION = 10 * 60 * 1000;
    private final long REFRESH_JWT_EXPIRATION = 30 * 60 * 1000;

    @Autowired
    @Qualifier("CustomUserDetailsService")
    private final UserDetailsService userDetailsService;

    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    @Override
    public String extractRoles(String token) {
        return (String) extractAllClaims(token).get("roles");
    }

    @Override
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        try {
            final Claims claims = extractAllClaims(token);
            return claimsResolver.apply(claims);
        } catch (MalformedJwtException ex) {
            throw new JWTException(ex.getMessage());
        }

    }

    @Override
    public Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    @Override
    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    @Override
    public String generateToken(UserDetails userDetail) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", userDetail.getAuthorities().toString());
        return Jwts.builder().setClaims(claims)
                .setSubject(userDetail.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    @Override
    public String generateRefreshToken(UserDetails userDetail) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", userDetail.getAuthorities().toString());
        return Jwts.builder().setClaims(claims)
                .setSubject(userDetail.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_JWT_EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    @Override
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
