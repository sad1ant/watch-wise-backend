package com.app.watch_wise_backend.service;

import com.app.watch_wise_backend.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {
    @Value("${jwt.access.secret}")
    private String ACCESS_SECRET_KEY;
    @Value("${jwt.refresh.secret}")
    private String REFRESH_SECRET_KEY;
    @Value("${jwt.access.token.expiration}")
    private long ACCESS_TOKEN_EXPIRATION;
    @Value("${jwt.refresh.token.expiration}")
    private long REFRESH_TOKEN_EXPIRATION;

    public Map<String, String> generateTokensAndSetCookies(User payload, HttpServletResponse response) {
        Map<String, String> tokens = new HashMap<>();
        String accessToken = generateToken(payload, ACCESS_SECRET_KEY, ACCESS_TOKEN_EXPIRATION);
        String refreshToken = generateToken(payload, REFRESH_SECRET_KEY, REFRESH_TOKEN_EXPIRATION);

        tokens.put("access_token", accessToken);
        tokens.put("refresh_token", refreshToken);

        setCookie(refreshToken, REFRESH_TOKEN_EXPIRATION, response);
        return tokens;
    }

    private String generateToken(User payload, String secret, long expiration) {
        return Jwts.builder()
                .setSubject(String.valueOf(payload.getId()))
                .claim("username", payload.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public Claims validateAccessToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(ACCESS_SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            return null;
        }
    }

    public Claims validateRefreshToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(REFRESH_SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            return null;
        }
    }

    private void setCookie(String value, long expiration, HttpServletResponse response) {
        Cookie cookie = new Cookie("refresh_token", value);
        cookie.setMaxAge((int) expiration);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public void removeAllTokens(HttpServletResponse response) {
        Cookie refreshTokenCookie = new Cookie("refresh_token", null);
        refreshTokenCookie.setMaxAge(0);
        refreshTokenCookie.setPath("/");
        response.addCookie(refreshTokenCookie);
    }
}