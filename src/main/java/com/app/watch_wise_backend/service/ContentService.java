package com.app.watch_wise_backend.service;

import com.app.watch_wise_backend.dto.MovieDTO;
import com.app.watch_wise_backend.dto.SeriesDTO;
import com.app.watch_wise_backend.model.content.*;
import com.app.watch_wise_backend.model.review.Review;
import com.app.watch_wise_backend.model.review.ReviewStatus;
import com.app.watch_wise_backend.model.user.User;
import com.app.watch_wise_backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    @Autowired
    private UserMovieStatusRepository movieStatusRepository;
    @Autowired
    private EpisodeRepository episodeRepository;
    @Autowired
    private UserSeriesStatusRepository seriesStatusRepository;
    @Autowired
    private UserEpisodeStatusRepository episodeStatusRepository;

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

    public Map<String, Object> getMovie(Long movieId, String username) {
        Map<String, Object> map = new HashMap<>();
        User user = userRepository.findByUsername(username);
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

            if (user != null) {
                List<UserMovieStatus> userMovieStatus = movieStatusRepository.findAllByUserAndMovie(user, movie);
                List<Map<String, Object>> statusData = userMovieStatus.stream()
                        .map(status -> {
                            Map<String, Object> statusMap = new HashMap<>();
                            statusMap.put("status", status.getWatchStatus());
                            return statusMap;
                        })
                        .collect(Collectors.toList());

                map.put("statuses", statusData);
            }

            return map;
        }
        return null;
    }

    public Map<String, Object> getSeries(Long seriesId, String username) {
        Map<String, Object> map = new HashMap<>();
        User user = userRepository.findByUsername(username);
        Series series = seriesRepository.findById(seriesId).orElse(null);
        if (series != null) {
            map.put("id", seriesId);
            map.put("title", series.getTitle());
            map.put("genre", series.getGenre());
            map.put("releaseYear", series.getReleaseYear());
            map.put("rating", series.getRating());
            map.put("image", series.getImage());
            map.put("country", series.getCountry());
            map.put("slogan", series.getSlogan());
            map.put("directors", series.getDirectors());
            map.put("writers", series.getWriters());
            map.put("cinematographers", series.getCinematographers());
            map.put("producers", series.getProducers());
            map.put("composers", series.getComposers());
            map.put("editors", series.getEditors());
            map.put("artists", series.getArtists());
            map.put("budget", series.getBudget());
            map.put("age_rating", series.getAgeRating());

            List<Episode> episodes = episodeRepository.findBySeriesId(seriesId);

            int numberOfSeasons = episodes.stream()
                    .mapToInt(Episode::getSeasonNumber)
                    .max()
                    .orElse(0);
            map.put("numberOfSeasons", numberOfSeasons);

            int numberOfEpisodes = episodes.size();
            map.put("numberOfEpisodes", numberOfEpisodes);

            List<Map<String, Object>> episodeList = new ArrayList<>();
            for (Episode episode : episodes) {
                Map<String, Object> episodeData = new HashMap<>();
                episodeData.put("id", episode.getId());
                episodeData.put("title", episode.getTitle());
                episodeData.put("seasonNumber", episode.getSeasonNumber());
                episodeData.put("episodeNumber", episode.getEpisodeNumber());
                episodeData.put("duration", episode.getDuration());
                episodeData.put("image", episode.getImage());

                if (user != null) {
                    UserEpisodeStatus userEpisodeStatus = episodeStatusRepository.findByUserAndEpisode(user, episode);
                    if (userEpisodeStatus != null) {
                        episodeData.put("status", userEpisodeStatus.getWatchStatus());
                    }
                }

                episodeList.add(episodeData);
            }

            map.put("episodes", episodeList);

            List<Review> activeReviews = reviewRepository.findBySeriesAndStatus(series, ReviewStatus.ACTIVE);
            List<Map<String, Object>> reviewData = activeReviews.stream()
                    .map(review -> {
                        Map<String, Object> reviewMap = new HashMap<>();
                        reviewMap.put("description", review.getDescription());
                        reviewMap.put("user", review.getUser().getUsername());
                        return reviewMap;
                    })
                    .collect(Collectors.toList());
            map.put("reviews", reviewData);

            if (user != null) {
                List<UserSeriesStatus> userSeriesStatus = seriesStatusRepository.findAllByUserAndSeries(user, series);
                List<Map<String, Object>> statusData = userSeriesStatus.stream()
                        .map(status -> {
                            Map<String, Object> statusMap = new HashMap<>();
                            statusMap.put("status", status.getWatchStatus());
                            return statusMap;
                        })
                        .collect(Collectors.toList());
                map.put("statuses", statusData);
            }

            return map;
        }
        return null;
    }
}
