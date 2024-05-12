package com.app.watch_wise_backend.service;

import com.app.watch_wise_backend.model.content.*;
import com.app.watch_wise_backend.model.review.Review;
import com.app.watch_wise_backend.model.review.ReviewStatus;
import com.app.watch_wise_backend.model.user.User;
import com.app.watch_wise_backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private SeriesRepository seriesRepository;
    @Autowired
    private UserSeriesStatusRepository seriesStatusRepository;
    @Autowired
    private UserMovieStatusRepository movieStatusRepository;

    public Map<String, String> addMovieReview(String username, Long movieId, String description) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return null;
        }

        Review review = new Review();
        review.setUser(user);
        review.setDescription(description);
        review.setStatus(ReviewStatus.ACTIVE);

        if (movieId != null) {
            Optional<Movie> optionalMovie = movieRepository.findById(movieId);
            if (optionalMovie.isPresent()) {
                Movie movie = optionalMovie.get();
                review.setMovie(movie);

                UserMovieStatus newUserMovieStatus = new UserMovieStatus();
                newUserMovieStatus.setUser(user);
                newUserMovieStatus.setMovie(movie);
                newUserMovieStatus.setWatchStatus(WatchStatus.REVIEWED);
                movieStatusRepository.save(newUserMovieStatus);
            } else {
                return null;
            }
        }

        reviewRepository.save(review);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Review added successfully");
        return response;
    }

    public Map<String, String> addSeriesReview(String username, Long seriesId, String description) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return null;
        }

        Review review = new Review();
        review.setUser(user);
        review.setDescription(description);
        review.setStatus(ReviewStatus.ACTIVE);

        if (seriesId != null) {
            Optional<Series> optionalSeries = seriesRepository.findById(seriesId);
            if (optionalSeries.isPresent()) {
                Series series = optionalSeries.get();
                review.setSeries(series);

                UserSeriesStatus newUserSeriesStatus = new UserSeriesStatus();
                newUserSeriesStatus.setUser(user);
                newUserSeriesStatus.setSeries(series);
                newUserSeriesStatus.setWatchStatus(WatchStatus.REVIEWED);
                seriesStatusRepository.save(newUserSeriesStatus);
            } else {
                return null;
            }
        }
        reviewRepository.save(review);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Review added successfully");
        return response;
    }

    public Map<String, String> deleteReview(String username, Long reviewId) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return null;
        }

        Review review = reviewRepository.findById(reviewId).orElse(null);
        if (review == null) {
            return null;
        }

        if (!review.getUser().equals(user)) {
            return null;
        }
        reviewRepository.delete(review);

        Movie movie = review.getMovie();
        if (movie != null) {
            UserMovieStatus userMovieStatus = movieStatusRepository.findByUserAndMovieAndWatchStatus(user, movie, WatchStatus.REVIEWED);
            if (userMovieStatus != null) {
                movieStatusRepository.delete(userMovieStatus);
            }
        }

        Series series = review.getSeries();
        if (series != null) {
            UserSeriesStatus userSeriesStatus = seriesStatusRepository.findByUserAndSeriesAndWatchStatus(user, series, WatchStatus.REVIEWED);
            if (userSeriesStatus != null) {
                seriesStatusRepository.delete(userSeriesStatus);
            }
        }

        Map<String, String> response = new HashMap<>();
        response.put("message", "Review deleted successfully");
        return response;
    }

    public Map<String, String> editReview(String username, Long reviewId, String newDescription) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return null;
        }

        Review review = reviewRepository.findById(reviewId).orElse(null);
        if (review == null) {
            return null;
        }

        if (!review.getUser().equals(user)) {
            return null;
        }

        review.setDescription(newDescription);
        reviewRepository.save(review);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Review edited successfully");
        return response;
    }
}
