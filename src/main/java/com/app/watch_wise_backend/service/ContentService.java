package com.app.watch_wise_backend.service;

import com.app.watch_wise_backend.dto.MovieDTO;
import com.app.watch_wise_backend.dto.SeriesDTO;
import com.app.watch_wise_backend.model.content.Movie;
import com.app.watch_wise_backend.model.content.Series;
import com.app.watch_wise_backend.model.review.Review;
import com.app.watch_wise_backend.model.review.ReviewStatus;
import com.app.watch_wise_backend.repository.MovieRepository;
import com.app.watch_wise_backend.repository.ReviewRepository;
import com.app.watch_wise_backend.repository.SeriesRepository;
import com.app.watch_wise_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ContentService {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private SeriesRepository seriesRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    public Page<MovieDTO> getAllMovies(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Movie> movies = movieRepository.findAll(pageable);
        return movies.map(movie -> new MovieDTO(
                movie.getId(),
                movie.getTitle(),
                movie.getGenre(),
                movie.getReleaseYear(),
                movie.getRating(),
                movie.getImage()
        ));
    }

    public Page<SeriesDTO> getAllSeries(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Series> series = seriesRepository.findAll(pageable);
        return series.map(s -> new SeriesDTO(
                s.getId(),
                s.getTitle(),
                s.getGenre(),
                s.getReleaseYear(),
                s.getRating(),
                s.getImage()
        ));
    }

    public Map<String, Object> getMovie(Long movieId) {
        Map<String, Object> map = new HashMap<>();
        Movie movie = movieRepository.findById(movieId).orElse(null);
        if (movie != null) {
            map.put("id", movieId);
            map.put("title", movie.getTitle());
            map.put("genre", movie.getGenre());
            map.put("releaseYear", movie.getReleaseYear());
            map.put("rating", movie.getRating());
            map.put("image", movie.getImage());
            map.put("country", movie.getCountry());
            map.put("slogan", movie.getSlogan());
            map.put("directors", movie.getDirectors());
            map.put("writers", movie.getWriters());
            map.put("cinematographers", movie.getCinematographers());
            map.put("producers", movie.getProducers());
            map.put("composers", movie.getComposers());
            map.put("editors", movie.getEditors());
            map.put("artists", movie.getArtists());
            map.put("budget", movie.getBudget());
            map.put("age_rating", movie.getAgeRating());
            map.put("duration", movie.getDuration());

            List<Review> activeReviews = reviewRepository.findByMovieAndStatus(movie, ReviewStatus.ACTIVE);
            List<Map<String, Object>> reviewData = activeReviews.stream()
                    .map(review -> {
                        Map<String, Object> reviewMap = new HashMap<>();
                        reviewMap.put("description", review.getDescription());
                        reviewMap.put("user", review.getUser().getUsername());
                        return reviewMap;
                    })
                    .collect(Collectors.toList());

            map.put("reviews", reviewData);

            return map;
        }
        return null;
    }
}
