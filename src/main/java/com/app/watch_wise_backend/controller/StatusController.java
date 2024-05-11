package com.app.watch_wise_backend.controller;

import com.app.watch_wise_backend.service.AuthService;
import com.app.watch_wise_backend.service.StatusService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class StatusController {
    @Autowired
    private StatusService statusService;
    @Autowired
    private AuthService authService;

    // INTERESTED STATUS
    @PostMapping("/movie/{movieId}/interested")
    @ResponseStatus(HttpStatus.OK)
    public void setUserMovieStatusInterested(@PathVariable("movieId") Long movieId, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        Claims claims = authService.validateAccessToken(token);
        String username = (String) claims.get("username");
        statusService.setUserMovieStatusInterested(username, movieId);
    }

    @PostMapping("/series/{seriesId}/interested")
    @ResponseStatus(HttpStatus.OK)
    public void setUserSeriesStatusInterested(@PathVariable("seriesId") Long seriesId, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        Claims claims = authService.validateAccessToken(token);
        String username = (String) claims.get("username");
        statusService.setUserSeriesStatusInterested(username, seriesId);
    }

    @DeleteMapping("/movie/{movieId}/interested")
    @ResponseStatus(HttpStatus.OK)
    public void removeUserMovieStatusInterested(@PathVariable("movieId") Long movieId, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        Claims claims = authService.validateAccessToken(token);
        String username = (String) claims.get("username");
        statusService.removeUserMovieStatusInterested(username, movieId);
    }

    @DeleteMapping("/series/{seriesId}/interested")
    @ResponseStatus(HttpStatus.OK)
    public void removeUserSeriesStatusInterested(@PathVariable("seriesId") Long seriesId, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        Claims claims = authService.validateAccessToken(token);
        String username = (String) claims.get("username");
        statusService.removeUserSeriesStatusInterested(username, seriesId);
    }

    // WANT_TO_WATCH STATUS
    @PostMapping("/movie/{movieId}/want-to-watch")
    @ResponseStatus(HttpStatus.OK)
    public void setUserMovieStatusWantToWatch(@PathVariable("movieId") Long movieId, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        Claims claims = authService.validateAccessToken(token);
        String username = (String) claims.get("username");
        statusService.setUserMovieStatusWantToWatch(username, movieId);
    }

    @PostMapping("/series/{seriesId}/want-to-watch")
    @ResponseStatus(HttpStatus.OK)
    public void setUserSeriesStatusWantToWatch(@PathVariable("seriesId") Long seriesId, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        Claims claims = authService.validateAccessToken(token);
        String username = (String) claims.get("username");
        statusService.setUserSeriesStatusWantToWatch(username, seriesId);
    }

    @DeleteMapping("/movie/{movieId}/want-to-watch")
    @ResponseStatus(HttpStatus.OK)
    public void removeUserMovieStatusWantToWatch(@PathVariable("movieId") Long movieId, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        Claims claims = authService.validateAccessToken(token);
        String username = (String) claims.get("username");
        statusService.removeUserMovieStatusWantToWatch(username, movieId);
    }

    @DeleteMapping("/series/{seriesId}/want-to-watch")
    @ResponseStatus(HttpStatus.OK)
    public void removeUserSeriesStatusWantToWatch(@PathVariable("seriesId") Long seriesId, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        Claims claims = authService.validateAccessToken(token);
        String username = (String) claims.get("username");
        statusService.removeSeriesStatusWantToWatch(username, seriesId);
    }

    // WATCHING STATUS
    @PostMapping("/movie/{movieId}/watching")
    @ResponseStatus(HttpStatus.OK)
    public void setUserMovieStatusWatching(@PathVariable("movieId") Long movieId, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        Claims claims = authService.validateAccessToken(token);
        String username = (String) claims.get("username");
        statusService.setUserMovieStatusWatching(username, movieId);
    }

    @PostMapping("/series/{seriesId}/watching")
    @ResponseStatus(HttpStatus.OK)
    public void setUserSeriesStatusWatching(@PathVariable("seriesId") Long seriesId, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        Claims claims = authService.validateAccessToken(token);
        String username = (String) claims.get("username");
        statusService.setUserSeriesStatusWatching(username, seriesId);
    }

    @DeleteMapping("/movie/{movieId}/watching")
    @ResponseStatus(HttpStatus.OK)
    public void removeUserMovieStatusWatching(@PathVariable("movieId") Long movieId, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        Claims claims = authService.validateAccessToken(token);
        String username = (String) claims.get("username");
        statusService.removeUserMovieStatusWatching(username, movieId);
    }

    @DeleteMapping("/series/{seriesId}/watching")
    @ResponseStatus(HttpStatus.OK)
    public void removeUserSeriesStatusWatching(@PathVariable("seriesId") Long seriesId, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        Claims claims = authService.validateAccessToken(token);
        String username = (String) claims.get("username");
        statusService.removeUserSeriesStatusWatching(username, seriesId);
    }

    // WATCHED STATUS
    @PostMapping("/movie/{movieId}/watched")
    @ResponseStatus(HttpStatus.OK)
    public void setUserMovieStatusWatched(@PathVariable("movieId") Long movieId, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        Claims claims = authService.validateAccessToken(token);
        String username = (String) claims.get("username");
        statusService.setUserMovieStatusWatched(username, movieId);
    }

    @PostMapping("/series/{seriesId}/watched")
    @ResponseStatus(HttpStatus.OK)
    public void setUserSeriesStatusWatched(@PathVariable("seriesId") Long seriesId, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        Claims claims = authService.validateAccessToken(token);
        String username = (String) claims.get("username");
        statusService.setUserSeriesStatusWatched(username, seriesId);
    }

    @PostMapping("/episode/{episodeId}/series/{seriesId}/watched")
    @ResponseStatus(HttpStatus.OK)
    public void setUserEpisodeStatusWatched(@PathVariable("episodeId") Long episodeId, @PathVariable("seriesId") Long seriesId, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        Claims claims = authService.validateAccessToken(token);
        String username = (String) claims.get("username");
        statusService.setUserEpisodeStatusWatched(username, episodeId, seriesId);
    }
}
