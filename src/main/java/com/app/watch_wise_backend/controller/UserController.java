package com.app.watch_wise_backend.controller;

import com.app.watch_wise_backend.service.AuthService;
import com.app.watch_wise_backend.service.UserService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        Claims claims = authService.validateAccessToken(token);
        String username = (String) claims.get("username");
        Map<String, String> response = userService.getUserData(username);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping("/statistics")
    public ResponseEntity<?> getViewingStatistics(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        Claims claims = authService.validateAccessToken(token);
        String username = (String) claims.get("username");
        Map<String, Object> response = userService.getViewingStatistics(username);
        if (response != null) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Collections.singletonMap("message", "error"), HttpStatus.BAD_REQUEST);
        }
    }
}
