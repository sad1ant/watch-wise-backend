package com.app.watch_wise_backend.controller;

import com.app.watch_wise_backend.dto.FilterParams;
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
    public ResponseEntity<Page<MovieDTO>> getAllMovies(
            @RequestParam(value = "genre", required = false) String genre,
            @RequestParam(value = "start-year", required = false) String startYear,
            @RequestParam(value = "end-year", required = false) String endYear,
            @RequestParam(value = "artist", required = false) String artist,
            @RequestParam(value = "age-rating", required = false) String ageRating,
            @RequestParam(value = "rating", required = false) String rating,
            @RequestParam(value = "country", required = false) String country,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        if (genre != null || startYear != null || endYear != null || artist != null || ageRating != null || rating != null || country != null) {
            FilterParams params = new FilterParams(genre, startYear, endYear, artist, ageRating, rating, country);
            Page<MovieDTO> filteredMovies = contentService.getFilteredMovies(params, page, size);
            return new ResponseEntity<>(filteredMovies, HttpStatus.OK);
        } else {
            Page<MovieDTO> allMovies = contentService.getAllMovies(page, size);
            return new ResponseEntity<>(allMovies, HttpStatus.OK);
        }
    }

    @GetMapping("/series")
    public ResponseEntity<Page<SeriesDTO>> getAllSeries(
            @RequestParam(value = "genre", required = false) String genre,
            @RequestParam(value = "start-year", required = false) String startYear,
            @RequestParam(value = "end-year", required = false) String endYear,
            @RequestParam(value = "artist", required = false) String artist,
            @RequestParam(value = "age-rating", required = false) String ageRating,
            @RequestParam(value = "rating", required = false) String rating,
            @RequestParam(value = "country", required = false) String country,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        if (genre != null || startYear != null || endYear != null || artist != null || ageRating != null || rating != null || country != null) {
            FilterParams params = new FilterParams(genre, startYear, endYear, artist, ageRating, rating, country);
            Page<SeriesDTO> filteredSeries = contentService.getFilteredSeries(params, page, size);
            return new ResponseEntity<>(filteredSeries, HttpStatus.OK);
        } else {
            Page<SeriesDTO> allSeries = contentService.getAllSeries(page, size);
            return new ResponseEntity<>(allSeries, HttpStatus.OK);
        }
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

    @PostMapping("/movies/search")
    public ResponseEntity<Page<MovieDTO>> searchMovieByTitle(@RequestBody Map<String, String> request, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        String title = request.get("title");
        Page<MovieDTO> result = contentService.searchMoviesByTitle(title, page, size);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/series/search")
    public ResponseEntity<Page<SeriesDTO>> searchSeriesByTitle(@RequestBody Map<String, String> request, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        String title = request.get("title");
        Page<SeriesDTO> result = contentService.searchSeriesByTitle(title, page, size);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
