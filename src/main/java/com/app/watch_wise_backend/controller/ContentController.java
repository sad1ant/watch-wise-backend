package com.app.watch_wise_backend.controller;

import com.app.watch_wise_backend.dto.MovieDTO;
import com.app.watch_wise_backend.dto.SeriesDTO;
import com.app.watch_wise_backend.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
public class ContentController {
    @Autowired
    private ContentService contentService;

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
    public ResponseEntity<?> getMovie(@PathVariable("movieId") Long movieId) {
        Map<String, Object> response = contentService.getMovie(movieId);
        if (response != null) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Collections.singletonMap("message", "Movie not found"), HttpStatus.NOT_FOUND);
        }
    }
}
