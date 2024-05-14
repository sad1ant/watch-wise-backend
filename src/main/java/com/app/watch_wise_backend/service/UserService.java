package com.app.watch_wise_backend.service;

import com.app.watch_wise_backend.model.content.*;
import com.app.watch_wise_backend.model.diary.UserMovieDiary;
import com.app.watch_wise_backend.model.diary.UserSeriesDiary;
import com.app.watch_wise_backend.model.review.Review;
import com.app.watch_wise_backend.model.user.Role;
import com.app.watch_wise_backend.model.user.User;
import com.app.watch_wise_backend.repository.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthService authService;
    @Autowired
    private UserMovieStatusRepository movieStatusRepository;
    @Autowired
    private UserSeriesStatusRepository seriesStatusRepository;
    @Autowired
    private UserEpisodeStatusRepository episodeStatusRepository;
    @Autowired
    private UserMovieRepository movieDiaryRepository;
    @Autowired
    private UserSeriesRepository seriesDiaryRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private SeriesRepository seriesRepository;

    public User registerUser(String username, String email, String password, String fullName) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setFullName(fullName);
        user.setRole(Role.USER);
        return userRepository.save(user);
    }

    public Map<String, String> loginUser(String username, String password, HttpServletResponse response) {
        User user = userRepository.findByUsername(username);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return authService.generateTokensAndSetCookies(user, response);
        } else {
            return Collections.singletonMap("error", "Invalid credentials");
        }
    }

    public Map<String, String> getUserData(String username) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            Map<String, String> userData = new HashMap<>();
            userData.put("username", user.getUsername());
            userData.put("email", user.getEmail());
            userData.put("fullName", user.getFullName());
            return userData;
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("error", "User not found");
            return response;
        }
    }

    public Map<String, Object> getViewingStatistics(String username) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            Map<String, Object> statistics = new HashMap<>();
            List<UserMovieStatus> watchedMovies = movieStatusRepository.findByUserAndWatchStatus(user, WatchStatus.WATCHED);
            List<UserSeriesStatus> watchedSeries = seriesStatusRepository.findByUserAndWatchStatus(user, WatchStatus.WATCHED);
            List<UserSeriesStatus> watchingSeries = seriesStatusRepository.findByUserAndWatchStatus(user, WatchStatus.WATCHING);
            List<UserMovieDiary> movieDiary = movieDiaryRepository.findByUser(user);
            List<UserSeriesDiary> seriesDiary = seriesDiaryRepository.findByUser(user);

            int watchedMoviesCount = watchedMovies.size();
            int watchedSeriesCount = 0;
            int totalMovieDuration = 0;
            int totalSeriesDuration = 0;

            Map<String, Integer> movieGenresCount = new HashMap<>();
            Map<String, Integer> seriesGenresCount = new HashMap<>();

            int totalMoviesInDiary = movieDiary.size();
            int totalSeriesInDiary = seriesDiary.size();

            int completedSeriesCount = 0;
            int completedMoviesCount = 0;

            if (!watchedSeries.isEmpty() || !watchingSeries.isEmpty()) {
                for (UserSeriesStatus seriesStatus : watchedSeries) {
                    Series series = seriesStatus.getSeries();

                    seriesGenresCount.put(series.getGenre(), seriesGenresCount.getOrDefault(series.getGenre(), 0) + 1);

                    List<Episode> episodes = series.getEpisodes();
                    for (Episode episode : episodes) {
                        UserEpisodeStatus episodeStatus = episodeStatusRepository.findByUserAndEpisode(user, episode);
                        if (episodeStatus != null && episodeStatus.getWatchStatus() == WatchStatus.WATCHED) {
                            totalSeriesDuration += episode.getDuration();
                        }
                    }

                    watchedSeriesCount++;
                    completedSeriesCount++;
                }

                for (UserSeriesStatus seriesStatus : watchingSeries) {
                    Series series = seriesStatus.getSeries();
                    List<Episode> episodes = series.getEpisodes();
                    for (Episode episode : episodes) {
                        UserEpisodeStatus episodeStatus = episodeStatusRepository.findByUserAndEpisode(user, episode);
                        if (episodeStatus != null && episodeStatus.getWatchStatus() == WatchStatus.WATCHED) {
                            totalSeriesDuration += episode.getDuration();
                        }
                    }

                    if (seriesStatus.getWatchStatus() == WatchStatus.WATCHED) {
                        watchedSeriesCount++;
                        completedSeriesCount++;
                    }
                }
            }

            if (!watchedMovies.isEmpty()) {
                for (UserMovieStatus movieStatus : watchedMovies) {
                    Movie movie = movieStatus.getMovie();
                    totalMovieDuration += movie.getDuration();

                    movieGenresCount.put(movie.getGenre(), movieGenresCount.getOrDefault(movie.getGenre(), 0) + 1);

                    completedMoviesCount++;
                }
            }

            String mostWatchedMovieGenre = !movieGenresCount.isEmpty() ? Collections.max(movieGenresCount.entrySet(), Map.Entry.comparingByValue()).getKey() : "";
            String mostWatchedSeriesGenre = !seriesGenresCount.isEmpty() ? Collections.max(seriesGenresCount.entrySet(), Map.Entry.comparingByValue()).getKey() : "";

            long movieHours = TimeUnit.MINUTES.toHours(totalMovieDuration);
            long movieMinutes = totalMovieDuration - TimeUnit.HOURS.toMinutes(movieHours);
            String totalMovieDurationFormatted = String.format("%02d:%02d", movieHours, movieMinutes);

            long seriesHours = TimeUnit.MINUTES.toHours(totalSeriesDuration);
            long seriesMinutes = totalSeriesDuration - TimeUnit.HOURS.toMinutes(seriesHours);
            String totalSeriesDurationFormatted = String.format("%02d:%02d", seriesHours, seriesMinutes);

            statistics.put("watchedMoviesCount", watchedMoviesCount);
            statistics.put("watchedSeriesCount", watchedSeriesCount);
            statistics.put("totalMovieDuration", totalMovieDurationFormatted);
            statistics.put("totalSeriesDuration", totalSeriesDurationFormatted);
            statistics.put("mostWatchedMovieGenre", mostWatchedMovieGenre);
            statistics.put("mostWatchedSeriesGenre", mostWatchedSeriesGenre);
            statistics.put("completedSeriesPercent", totalSeriesInDiary != 0 ? (double) completedSeriesCount / totalSeriesInDiary * 100 : 0);
            statistics.put("completedMoviesPercent", totalMoviesInDiary != 0 ? (double) completedMoviesCount / totalMoviesInDiary * 100 : 0);

            return statistics;
        }

        Map<String, Object> statistics = new HashMap<>();
        statistics.put("watchedMoviesCount", 0);
        statistics.put("watchedSeriesCount", 0);
        statistics.put("totalMovieDuration", "00:00");
        statistics.put("totalSeriesDuration", "00:00");
        statistics.put("mostWatchedMovieGenre", "");
        statistics.put("mostWatchedSeriesGenre", "");
        statistics.put("completedSeriesPercent", 0);
        statistics.put("completedMoviesPercent", 0);
        return statistics;
    }

    public Map<String, Object> getUserReviews(String username) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            List<Review> reviews = reviewRepository.findByUser(user);
            if (reviews != null && !reviews.isEmpty()) {
                List<Map<String, Object>> reviewList = new ArrayList<>();
                for (Review review : reviews) {
                    Map<String, Object> reviewData = new HashMap<>();
                    reviewData.put("id", review.getId());
                    reviewData.put("description", review.getDescription());
                    reviewData.put("status", review.getStatus());
                    reviewData.put("reasonDeletion", review.getReasonDeletion());

                    String contentTitle = "";
                    if (review.getMovie() != null) {
                        Movie movie = movieRepository.findById(review.getMovie().getId()).orElse(null);
                        if (movie != null) {
                            contentTitle = movie.getTitle();
                        }
                    } else if (review.getSeries() != null) {
                        Series series = seriesRepository.findById(review.getSeries().getId()).orElse(null);
                        if (series != null) {
                            contentTitle = series.getTitle();
                        }
                    }
                    reviewData.put("contentTitle", contentTitle);

                    reviewList.add(reviewData);
                }
                Map<String, Object> response = new HashMap<>();
                response.put("reviews", reviewList);
                return response;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}
