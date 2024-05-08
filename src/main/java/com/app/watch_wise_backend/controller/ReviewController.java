package com.app.watch_wise_backend.controller;

import com.app.watch_wise_backend.dto.ReviewRequest;
import com.app.watch_wise_backend.service.AuthService;
import com.app.watch_wise_backend.service.ReviewService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private AuthService authService;

    @PostMapping("/add-movie-review")
    public ResponseEntity<?> addMovieReview(@RequestBody ReviewRequest request, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        Claims claims = authService.validateAccessToken(token);
        String username = (String) claims.get("username");
        Map<String, String> review = reviewService.addMovieReview(username, request.getMovieId(), request.getDescription());
        if (review != null) {
            return new ResponseEntity<>(review, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Collections.singletonMap("message", "Content not found"), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add-series-review")
    public ResponseEntity<?> addSeriesReview(@RequestBody ReviewRequest request, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        Claims claims = authService.validateAccessToken(token);
        String username = (String) claims.get("username");
        Map<String, String> review = reviewService.addSeriesReview(username, request.getSeriesId(), request.getDescription());
        if (review != null) {
            return new ResponseEntity<>(review, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Collections.singletonMap("message", "Content not found"), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete-review/{reviewId}")
    public ResponseEntity<?> deleteReview(@PathVariable("reviewId") Long reviewId, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        Claims claims = authService.validateAccessToken(token);
        String username = (String) claims.get("username");
        Map<String, String> response = reviewService.deleteReview(username, reviewId);
        if (response != null) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Collections.singletonMap("message", "Review not found"), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/edit-review/{reviewId}")
    public ResponseEntity<?> editReview(@PathVariable("reviewId") Long reviewId, @RequestHeader("Authorization") String authHeader, @RequestBody String request) {
        String token = authHeader.substring(7);
        Claims claims = authService.validateAccessToken(token);
        String username = (String) claims.get("username");
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode;
        try {
            jsonNode = mapper.readTree(request);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(Collections.singletonMap("message", "Failed to parse request body"), HttpStatus.BAD_REQUEST);
        }

        String description = jsonNode.get("description").asText();
        Map<String, String> response = reviewService.editReview(username, reviewId, description);
        if (response != null) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Collections.singletonMap("message", "Review not found"), HttpStatus.NOT_FOUND);
        }
    }
}
