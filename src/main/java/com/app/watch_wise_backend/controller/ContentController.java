package com.app.watch_wise_backend.controller;

import com.app.watch_wise_backend.dto.MovieDTO;
import com.app.watch_wise_backend.dto.SeriesDTO;
import com.app.watch_wise_backend.service.AuthService;
import com.app.watch_wise_backend.service.ContentService;
import com.app.watch_wise_backend.service.UserService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
public class ContentController {
    @Autowired
    private ContentService contentService;
    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;

    @GetMapping("/movies")
    public ResponseEntity<Page<MovieDTO>> getAllMovies(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Page<MovieDTO> movies = contentService.getAllMovies(page, size);
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    @GetMapping("/series")
    public ResponseEntity<Page<SeriesDTO>> getAllSeries(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Page<SeriesDTO> series = contentService.getAllSeries(page, size);
        return new ResponseEntity<>(series, HttpStatus.OK);
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<?> getMovie(@PathVariable("movieId") Long movieId, @RequestHeader(value = "Authorization", required = false) String authHeader) {
        String username = null;
        if (authHeader != null && !authHeader.isEmpty()) {
            String token = authHeader.substring(7);
            Claims claims = authService.validateAccessToken(token);
            if (claims != null) {
                username = (String) claims.get("username");
            }
        }

        Map<String, Object> response = contentService.getMovie(movieId, username);
        if (response != null) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Collections.singletonMap("message", "Movie not found"), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/series/{seriesId}")
    public ResponseEntity<?> getSeries(@PathVariable("seriesId") Long seriesId, @RequestHeader(value = "Authorization", required = false) String authHeader) {
        String username = null;
        if (authHeader != null && !authHeader.isEmpty()) {
            String token = authHeader.substring(7);
            Claims claims = authService.validateAccessToken(token);
            if (claims != null) {
                username = (String) claims.get("username");
            }
        }

        Map<String, Object> response = contentService.getSeries(seriesId, username);
        if (response != null) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Collections.singletonMap("message", "Series not found"), HttpStatus.NOT_FOUND);
        }
    }
}
