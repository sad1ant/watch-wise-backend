package com.app.watch_wise_backend.controller;

import com.app.watch_wise_backend.service.AuthService;
import com.app.watch_wise_backend.service.DiaryService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class DiaryController {
    @Autowired
    private DiaryService diaryService;
    @Autowired
    private AuthService authService;

    @PostMapping("/add-movie-diary")
    public ResponseEntity<?> addMovieToDiary(@RequestBody Map<String, Long> request, @RequestHeader("Authorization") String authHeader) {
        Long movieId = request.get("movieId");
        String token = authHeader.substring(7);
        Claims claims = authService.validateAccessToken(token);
        String username = (String) claims.get("username");

        Map<String, String> response = diaryService.addMovieToDiary(username, movieId);
        if (response != null) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Collections.singletonMap("message", "Error"), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete-movie-diary/{movieId}")
    public ResponseEntity<?> deleteMovieFromDiary(@PathVariable("movieId") Long movieId, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        Claims claims = authService.validateAccessToken(token);
        String username = (String) claims.get("username");

        Map<String, String> response = diaryService.deleteMovieFromDiary(movieId, username);
        if (response != null) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Collections.singletonMap("message", "Error"), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete-movie-diary")
    public ResponseEntity<?> deleteMovieDiary(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        Claims claims = authService.validateAccessToken(token);
        String username = (String) claims.get("username");

        Map<String, String> response = diaryService.deleteMovieDiary(username);
        if (response != null) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Collections.singletonMap("message", "Error"), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add-series-diary")
    public ResponseEntity<?> addSeriesToDiary(@RequestBody Map<String, Long> request, @RequestHeader("Authorization") String authHeader) {
        Long seriesId = request.get("seriesId");
        String token = authHeader.substring(7);
        Claims claims = authService.validateAccessToken(token);
        String username = (String) claims.get("username");

        Map<String, String> response = diaryService.addSeriesToDiary(username, seriesId);
        if (response != null) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Collections.singletonMap("message", "Error"), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete-series-diary/{seriesId}")
    public ResponseEntity<?> deleteSeriesFromDiary(@PathVariable("seriesId") Long seriesId, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        Claims claims = authService.validateAccessToken(token);
        String username = (String) claims.get("username");

        Map<String, String> response = diaryService.deleteSeriesFromDiary(seriesId, username);
        if (response != null) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Collections.singletonMap("message", "Error"), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete-series-diary")
    public ResponseEntity<?> deleteSeriesDiary(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        Claims claims = authService.validateAccessToken(token);
        String username = (String) claims.get("username");

        Map<String, String> response = diaryService.deleteSeriesDiary(username);
        if (response != null) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Collections.singletonMap("message", "Error"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/movie-diary")
    public ResponseEntity<?> getMovieDiary(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        Claims claims = authService.validateAccessToken(token);
        String username = (String) claims.get("username");

        Map<String, Object> response = diaryService.getMovieDiary(username);
        if (response != null) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Collections.singletonMap("message", "Error"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/series-diary")
    public ResponseEntity<?> getSeriesDiary(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        Claims claims = authService.validateAccessToken(token);
        String username = (String) claims.get("username");

        Map<String, Object> response = diaryService.getSeriesDiary(username);
        if (response != null) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Collections.singletonMap("message", "Error"), HttpStatus.BAD_REQUEST);
        }
    }
}
