package com.app.watch_wise_backend.service;

import com.app.watch_wise_backend.model.content.Episode;
import com.app.watch_wise_backend.model.content.Movie;
import com.app.watch_wise_backend.model.content.Series;
import com.app.watch_wise_backend.model.user.User;
import com.app.watch_wise_backend.repository.EpisodeRepository;
import com.app.watch_wise_backend.repository.MovieRepository;
import com.app.watch_wise_backend.repository.SeriesRepository;
import com.app.watch_wise_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private SeriesRepository seriesRepository;
    @Autowired
    private EpisodeRepository episodeRepository;

    public List<Map<String, String>> getUserList() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(u -> {
                    Map<String, String> userInfo = new HashMap<>();
                    userInfo.put("id", u.getId().toString());
                    userInfo.put("username", u.getUsername());
                    userInfo.put("email", u.getEmail());
                    userInfo.put("fullName", u.getFullName());
                    userInfo.put("role", u.getRole().toString());
                    return userInfo;
                })
                .collect(Collectors.toList());
    }

    public Map<String, String> addContent(Object content) {
        Map<String, String> response = new HashMap<>();
        if (content instanceof Movie) {
            Movie savedMovie = movieRepository.save((Movie) content);
            response.put("id", String.valueOf(savedMovie.getId()));
            response.put("type", "Movie");
            response.put("title", savedMovie.getTitle());
        } else if (content instanceof Series) {
            Series series = (Series) content;
            Series savedSeries = seriesRepository.save((Series) content);
            response.put("id", String.valueOf(savedSeries.getId()));
            response.put("type", "Series");
            response.put("title", savedSeries.getTitle());

            List<Episode> episodes = series.getEpisodes();
            if (episodes != null && !episodes.isEmpty()) {
                for (Episode episode : episodes) {
                    episode.setSeries(savedSeries);
                    episodeRepository.save(episode);
                }
            }
        }
        return response;
    }

    public Movie updateMovie(Long movieId, Movie updatedMovie) {
        Optional<Movie> optionalMovie = movieRepository.findById(movieId);
        if (optionalMovie.isPresent()) {
            Movie existingMovie = optionalMovie.get();
            existingMovie.setTitle(updatedMovie.getTitle());
            existingMovie.setReleaseYear(updatedMovie.getReleaseYear());
            existingMovie.setCountry(updatedMovie.getCountry());
            existingMovie.setGenre(updatedMovie.getGenre());
            existingMovie.setSlogan(updatedMovie.getSlogan());
            existingMovie.setDirectors(updatedMovie.getDirectors());
            existingMovie.setProducers(updatedMovie.getProducers());
            existingMovie.setWriters(updatedMovie.getWriters());
            existingMovie.setCinematographers(updatedMovie.getCinematographers());
            existingMovie.setComposers(updatedMovie.getComposers());
            existingMovie.setArtists(updatedMovie.getArtists());
            existingMovie.setEditors(updatedMovie.getEditors());
            existingMovie.setBudget(updatedMovie.getBudget());
            existingMovie.setAgeRating(updatedMovie.getAgeRating());
            existingMovie.setDuration(updatedMovie.getDuration());
            existingMovie.setImage(updatedMovie.getImage());
            return movieRepository.save(existingMovie);
        }
        return null;
    }
}
