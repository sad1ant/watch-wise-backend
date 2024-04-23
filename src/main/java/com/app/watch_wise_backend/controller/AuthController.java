package com.app.watch_wise_backend.controller;

import com.app.watch_wise_backend.model.User;
import com.app.watch_wise_backend.repository.UserRepository;
import com.app.watch_wise_backend.service.AuthService;
import com.app.watch_wise_backend.service.UserService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User request) {
        User user = userService.registerUser(request.getUsername(), request.getEmail(), request.getPassword(), request.getFullName());
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User request, HttpServletResponse response) {
        Map<String, String> responseMap = userService.loginUser(request.getUsername(), request.getPassword(), response);
        return new ResponseEntity<>(responseMap, HttpStatus.OK);
    }

    @GetMapping("/refresh")
    public ResponseEntity<?> refresh(@CookieValue(name = "refresh_token", required = false) String refreshToken, HttpServletResponse response) {
        if (refreshToken == null) {
            return new ResponseEntity<>("Refresh token not found", HttpStatus.UNAUTHORIZED);
        }

        Claims claims = authService.validateRefreshToken(refreshToken);
        if (claims == null) {
            return new ResponseEntity<>("Invalid refresh token", HttpStatus.UNAUTHORIZED);
        }

        String username = (String) claims.get("username");
        User user = userRepository.findByUsername(username);

        Map<String, String> tokens = authService.generateTokensAndSetCookies(user, response);
        return new ResponseEntity<>(tokens, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        authService.removeAllTokens(response);
        return new ResponseEntity<>(Collections.singletonMap("message", "Logged out successfully"), HttpStatus.OK);
    }
}
