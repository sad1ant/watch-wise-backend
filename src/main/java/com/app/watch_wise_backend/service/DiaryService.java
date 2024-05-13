package com.app.watch_wise_backend.service;

import com.app.watch_wise_backend.model.content.Movie;
import com.app.watch_wise_backend.model.content.Series;
import com.app.watch_wise_backend.model.content.UserMovieStatus;
import com.app.watch_wise_backend.model.diary.UserMovieDiary;
import com.app.watch_wise_backend.model.diary.UserSeriesDiary;
import com.app.watch_wise_backend.model.review.Review;
import com.app.watch_wise_backend.model.review.ReviewStatus;
import com.app.watch_wise_backend.model.user.User;
import com.app.watch_wise_backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DiaryService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMovieRepository userMovieRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private UserSeriesRepository userSeriesRepository;
    @Autowired
    private SeriesRepository seriesRepository;

    public Map<String, String> addMovieToDiary(String username, Long movieId) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return null;
        }

        Optional<Movie> optionalMovie = movieRepository.findById(movieId);
        if (optionalMovie.isPresent()) {
            Movie movie = optionalMovie.get();
            Optional<UserMovieDiary> existingEntry = userMovieRepository.findByUserAndMovieId(user, movieId);
            if (existingEntry.isPresent()) {
                Map<String, String> response = new HashMap<>();
                response.put("message", "Movie already exists in the diary");
                return response;
            }
            UserMovieDiary userMovieDiary = new UserMovieDiary();
            userMovieDiary.setUser(user);
            userMovieDiary.setMovie(movie);
            userMovieDiary.setDateAdded(LocalDateTime.now());

            userMovieRepository.save(userMovieDiary);

            Map<String, String> response = new HashMap<>();
            response.put("message", "Movie added to diary successfully");
            return response;
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Movie not found");
            return response;
        }
    }

    public Map<String, String> deleteMovieFromDiary(Long movieId, String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return null;
        }
        Optional<UserMovieDiary> optionalUserMovieDiary = userMovieRepository.findByUserAndMovieId(user, movieId);

        if (optionalUserMovieDiary.isPresent()) {
            Map<String, String> response = new HashMap<>();
            userMovieRepository.delete(optionalUserMovieDiary.get());
            response.put("message", "Movie deleted from diary successfully");
            return response;
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Movie not found");
            return response;
        }
    }

    public Map<String, String> deleteMovieDiary(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return null;
        }

        List<UserMovieDiary> userMovieDiaryList = userMovieRepository.findByUser(user);
        if (!userMovieDiaryList.isEmpty()) {
            Map<String, String> response = new HashMap<>();
            userMovieRepository.deleteAll(userMovieDiaryList);
            response.put("message", "Diary deleted successfully");
            return response;
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Diary not found");
            return response;
        }
    }

    public Map<String, String> addSeriesToDiary(String username, Long seriesId) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return null;
        }

        Optional<Series> optionalSeries = seriesRepository.findById(seriesId);
        if (optionalSeries.isPresent()) {
            Series series = optionalSeries.get();
            Optional<UserSeriesDiary> existingEntry = userSeriesRepository.findByUserAndSeriesId(user, seriesId);
            if (existingEntry.isPresent()) {
                Map<String, String> response = new HashMap<>();
                response.put("message", "Series already exists in the diary");
                return response;
            }
            UserSeriesDiary userSeriesDiary = new UserSeriesDiary();
            userSeriesDiary.setUser(user);
            userSeriesDiary.setSeries(series);
            userSeriesDiary.setDateAdded(LocalDateTime.now());

            userSeriesRepository.save(userSeriesDiary);

            Map<String, String> response = new HashMap<>();
            response.put("message", "Series added to diary successfully");
            return response;
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Series not found");
            return response;
        }
    }

    public Map<String, String> deleteSeriesFromDiary(Long seriesId, String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return null;
        }
        Optional<UserSeriesDiary> optionalUserSeriesDiary = userSeriesRepository.findByUserAndSeriesId(user, seriesId);

        if (optionalUserSeriesDiary.isPresent()) {
            Map<String, String> response = new HashMap<>();
            userSeriesRepository.delete(optionalUserSeriesDiary.get());
            response.put("message", "Series deleted from diary successfully");
            return response;
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Series not found");
            return response;
        }
    }

    public Map<String, String> deleteSeriesDiary(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return null;
        }

        List<UserSeriesDiary> userSeriesDiaryList = userSeriesRepository.findByUser(user);
        if (!userSeriesDiaryList.isEmpty()) {
            Map<String, String> response = new HashMap<>();
            userSeriesRepository.deleteAll(userSeriesDiaryList);
            response.put("message", "Diary deleted successfully");
            return response;
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Diary not found");
            return response;
        }
    }

    public Map<String, Object> getMovieDiary(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return null;
        }

        List<UserMovieDiary> movieDiary = userMovieRepository.findByUser(user);
        List<Map<String, Object>> movieDiaryEntries = movieDiary.stream()
                .map(entry -> {
                    Map<String, Object> diaryEntry = new HashMap<>();
                    diaryEntry.put("id", entry.getId());
                    diaryEntry.put("movieId", entry.getMovie().getId());
                    diaryEntry.put("title", entry.getMovie().getTitle());
                    diaryEntry.put("genre", entry.getMovie().getGenre());
                    diaryEntry.put("releaseYear", entry.getMovie().getReleaseYear());
                    diaryEntry.put("image", entry.getMovie().getImage());
                    diaryEntry.put("dateAdded", entry.getDateAdded());
                    return diaryEntry;
                })
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("movieDiary", movieDiaryEntries);
        return response;
    }

    public Map<String, Object> getSeriesDiary(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return null;
        }

        List<UserSeriesDiary> seriesDiary = userSeriesRepository.findByUser(user);
        List<Map<String, Object>> seriesDiaryEntries = seriesDiary.stream()
                .map(entry -> {
                    Map<String, Object> diaryEntry = new HashMap<>();
                    diaryEntry.put("id", entry.getId());
                    diaryEntry.put("seriesId", entry.getSeries().getId());
                    diaryEntry.put("title", entry.getSeries().getTitle());
                    diaryEntry.put("dateAdded", entry.getDateAdded());
                    diaryEntry.put("releaseYear", entry.getSeries().getReleaseYear());
                    diaryEntry.put("genre", entry.getSeries().getGenre());
                    diaryEntry.put("image", entry.getSeries().getImage());
                    return diaryEntry;
                })
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("seriesDiary", seriesDiaryEntries);
        return response;
    }
}
