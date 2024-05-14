package com.app.watch_wise_backend.controller;

import com.app.watch_wise_backend.model.review.Review;
import com.app.watch_wise_backend.service.AuthService;
import com.app.watch_wise_backend.service.UserService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(@RequestParam(value = "username", defaultValue = "") String username, @RequestHeader("Authorization") String authHeader) {
        if (username.isEmpty()) {
            String token = authHeader.substring(7);
            Claims claims = authService.validateAccessToken(token);
            username = (String) claims.get("username");
        }

        Map<String, String> response = userService.getUserData(username);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/statistics")
    public ResponseEntity<?> getViewingStatistics(@RequestParam(value = "username", defaultValue = "") String username, @RequestHeader("Authorization") String authHeader) {
        if (username.isEmpty()) {
            String token = authHeader.substring(7);
            Claims claims = authService.validateAccessToken(token);
            username = (String) claims.get("username");
        }

        Map<String, Object> response = userService.getViewingStatistics(username);
        if (response != null) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Collections.singletonMap("message", "error"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/profile/reviews")
    public ResponseEntity<?> getUserReviews(@RequestParam(value = "username", defaultValue = "") String username, @RequestHeader("Authorization") String authHeader) {
        if (username.isEmpty()) {
            String token = authHeader.substring(7);
            Claims claims = authService.validateAccessToken(token);
            username = (String) claims.get("username");
        }

        Map<String, Object> reviews = userService.getUserReviews(username);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }
}
