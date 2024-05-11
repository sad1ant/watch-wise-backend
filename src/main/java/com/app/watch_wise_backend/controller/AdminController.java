package com.app.watch_wise_backend.controller;

import com.app.watch_wise_backend.model.content.Episode;
import com.app.watch_wise_backend.model.content.Movie;
import com.app.watch_wise_backend.model.content.Series;
import com.app.watch_wise_backend.service.AdminService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @GetMapping("/users")
    public ResponseEntity<?> getUserList() {
        List<Map<String, String>> userList = adminService.getUserList();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @PostMapping("/add-content")
    public ResponseEntity<?> addContent(@RequestBody Map<String, Object> request) {
        String contentType = (String) request.get("contentType");
        Object content = request.get("content");

        if (contentType != null && content != null) {
            if ("movie".equals(contentType)) {
                Movie movie = new ObjectMapper().convertValue(content, Movie.class);
                Map<String, String> response = adminService.addContent(movie);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else if ("series".equals(contentType)) {
                Series series = new ObjectMapper().convertValue(content, Series.class);
                Map<String, String> response = adminService.addContent(series);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(Collections.singletonMap("message", "Invalid content type"), HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(Collections.singletonMap("message", "Content type and content must be provided"), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add-episode/{seriesId}")
    public ResponseEntity<?> addEpisode(@PathVariable Long seriesId, @RequestBody Episode episode) {
        Map<String, String> response = adminService.addEpisode(seriesId, episode);
        if (response != null) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Collections.singletonMap("message", "Failed to add episode to series with id: " + seriesId), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update-movie/{movieId}")
    public ResponseEntity<?> updateMovie(@PathVariable Long movieId, @RequestBody Movie updatedMovie) {
        Movie updated = adminService.updateMovie(movieId, updatedMovie);
        if (updated != null) {
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Collections.singletonMap("message", "Movie not found with id: " + movieId), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update-series/{seriesId}")
    public ResponseEntity<?> updateSeries(@PathVariable Long seriesId, @RequestBody Series updatedSeries) {
        Map<String, String> response = adminService.updateSeries(seriesId, updatedSeries);
        if (response != null) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Collections.singletonMap("message", "Series not found with id: " + seriesId), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update-episode/{seriesId}/{episodeId}")
    public ResponseEntity<?> updateEpisode(@PathVariable Long seriesId, @PathVariable Long episodeId, @RequestBody Episode updatedEpisode) {
        Map<String, String> response = adminService.updateEpisode(seriesId, episodeId, updatedEpisode);
        if (response != null) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Collections.singletonMap("message", "Episode not found with id: " + episodeId + " in series with id: " + seriesId), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/delete-review/{reviewId}")
    public ResponseEntity<?> deleteReview(@PathVariable("reviewId") Long reviewId, @RequestBody String request) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode;
        try {
            jsonNode = mapper.readTree(request);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(Collections.singletonMap("message", "Failed to parse request body"), HttpStatus.BAD_REQUEST);
        }

        String reason = jsonNode.get("reason").asText();
        Map<String, String> response = adminService.deleteReview(reviewId, reason);

        if (response != null) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Collections.singletonMap("message", "Review not found"), HttpStatus.NOT_FOUND);
        }
    }
}
