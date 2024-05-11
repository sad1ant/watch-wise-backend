package com.app.watch_wise_backend.service;

import com.app.watch_wise_backend.model.content.*;
import com.app.watch_wise_backend.model.user.User;
import com.app.watch_wise_backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatusService {
    @Autowired
    private UserSeriesStatusRepository seriesStatusRepository;
    @Autowired
    private UserMovieStatusRepository movieStatusRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private SeriesRepository seriesRepository;

    // INTERESTED STATUS
    public void setUserMovieStatusInterested(String username, Long movieId) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return;
        }

        Movie movie = movieRepository.findById(movieId).orElse(null);
        if (movie == null) {
            return;
        }

        UserMovieStatus userMovieStatus = movieStatusRepository.findByUserAndMovieAndWatchStatus(user, movie, WatchStatus.INTERESTED);
        if (userMovieStatus == null) {
            userMovieStatus = new UserMovieStatus();
            userMovieStatus.setUser(user);
            userMovieStatus.setMovie(movie);
        }

        userMovieStatus.setWatchStatus(WatchStatus.INTERESTED);
        movieStatusRepository.save(userMovieStatus);
    }

    public void setUserSeriesStatusInterested(String username, Long seriesId) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return;
        }

        Series series = seriesRepository.findById(seriesId).orElse(null);
        if (series == null) {
            return;
        }

        UserSeriesStatus userSeriesStatus = seriesStatusRepository.findByUserAndSeriesAndWatchStatus(user, series, WatchStatus.INTERESTED);
        if (userSeriesStatus == null) {
            userSeriesStatus = new UserSeriesStatus();
            userSeriesStatus.setUser(user);
            userSeriesStatus.setSeries(series);
        }

        userSeriesStatus.setWatchStatus(WatchStatus.INTERESTED);
        seriesStatusRepository.save(userSeriesStatus);
    }

    public void removeUserMovieStatusInterested(String username, Long movieId) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return;
        }

        Movie movie = movieRepository.findById(movieId).orElse(null);
        if (movie == null) {
            return;
        }

        UserMovieStatus userMovieStatus = movieStatusRepository.findByUserAndMovieAndWatchStatus(user, movie, WatchStatus.INTERESTED);
        if (userMovieStatus != null) {
            movieStatusRepository.delete(userMovieStatus);
        }
    }

    public void removeUserSeriesStatusInterested(String username, Long seriesId) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return;
        }

        Series series = seriesRepository.findById(seriesId).orElse(null);
        if (series == null) {
            return;
        }

        UserSeriesStatus userSeriesStatus = seriesStatusRepository.findByUserAndSeriesAndWatchStatus(user, series, WatchStatus.INTERESTED);
        if (userSeriesStatus != null) {
            seriesStatusRepository.delete(userSeriesStatus);
        }
    }

    // WANT_WO_WATCH STATUS
    public void setUserMovieStatusWantToWatch(String username, Long movieId) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return;
        }

        Movie movie = movieRepository.findById(movieId).orElse(null);
        if (movie == null) {
            return;
        }

        UserMovieStatus existingStatus = movieStatusRepository.findByUserAndMovie(user, movie);
        if (existingStatus != null && (existingStatus.getWatchStatus() == WatchStatus.WATCHING || existingStatus.getWatchStatus() == WatchStatus.WATCHED)) {
            return;
        }

        UserMovieStatus userMovieStatus = movieStatusRepository.findByUserAndMovieAndWatchStatus(user, movie, WatchStatus.WANT_TO_WATCH);
        if (userMovieStatus == null) {
            userMovieStatus = new UserMovieStatus();
            userMovieStatus.setUser(user);
            userMovieStatus.setMovie(movie);
            userMovieStatus.setWatchStatus(WatchStatus.WANT_TO_WATCH);
            movieStatusRepository.save(userMovieStatus);
        }
    }

    public void setUserSeriesStatusWantToWatch(String username, Long seriesId) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return;
        }

        Series series = seriesRepository.findById(seriesId).orElse(null);
        if (series == null) {
            return;
        }

        UserSeriesStatus existingStatus = seriesStatusRepository.findByUserAndSeries(user, series);
        if (existingStatus != null && (existingStatus.getWatchStatus() == WatchStatus.WATCHED || existingStatus.getWatchStatus() == WatchStatus.WATCHING)) {
            return;
        }

        UserSeriesStatus userSeriesStatus = seriesStatusRepository.findByUserAndSeriesAndWatchStatus(user, series, WatchStatus.WANT_TO_WATCH);
        if (userSeriesStatus == null) {
            userSeriesStatus = new UserSeriesStatus();
            userSeriesStatus.setUser(user);
            userSeriesStatus.setSeries(series);
            userSeriesStatus.setWatchStatus(WatchStatus.WANT_TO_WATCH);
            seriesStatusRepository.save(userSeriesStatus);
        }
    }

    public void removeUserMovieStatusWantToWatch(String username, Long movieId) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return;
        }

        Movie movie = movieRepository.findById(movieId).orElse(null);
        if (movie == null) {
            return;
        }

        UserMovieStatus userMovieStatus = movieStatusRepository.findByUserAndMovieAndWatchStatus(user, movie, WatchStatus.WANT_TO_WATCH);
        if (userMovieStatus != null) {
            movieStatusRepository.delete(userMovieStatus);
        }
    }

    public void removeSeriesStatusWantToWatch(String username, Long seriesId) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return;
        }

        Series series = seriesRepository.findById(seriesId).orElse(null);
        if (series == null) {
            return;
        }

        UserSeriesStatus userSeriesStatus = seriesStatusRepository.findByUserAndSeriesAndWatchStatus(user, series, WatchStatus.WANT_TO_WATCH);
        if (userSeriesStatus != null) {
            seriesStatusRepository.delete(userSeriesStatus);
        }
    }

    // WATCHING STATUS
    public void setUserMovieStatusWatching(String username, Long movieId) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return;
        }

        Movie movie = movieRepository.findById(movieId).orElse(null);
        if (movie == null) {
            return;
        }

        UserMovieStatus userMovieStatus = movieStatusRepository.findByUserAndMovie(user, movie);
        if (userMovieStatus != null) {
            if (userMovieStatus.getWatchStatus() == WatchStatus.WATCHED) {
                return;
            } else if (userMovieStatus.getWatchStatus() == WatchStatus.WANT_TO_WATCH) {
                userMovieStatus.setWatchStatus(WatchStatus.WATCHING);
                movieStatusRepository.save(userMovieStatus);
            }
        } else {
            userMovieStatus = new UserMovieStatus();
            userMovieStatus.setUser(user);
            userMovieStatus.setMovie(movie);
            userMovieStatus.setWatchStatus(WatchStatus.WATCHING);
            movieStatusRepository.save(userMovieStatus);
        }
    }

    public void setUserSeriesStatusWatching(String username, Long seriesId) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return;
        }

        Series series = seriesRepository.findById(seriesId).orElse(null);
        if (series == null) {
            return;
        }

        UserSeriesStatus userSeriesStatus = seriesStatusRepository.findByUserAndSeries(user, series);
        if (userSeriesStatus != null) {
            if (userSeriesStatus.getWatchStatus() == WatchStatus.WATCHED) {
                return;
            } else if (userSeriesStatus.getWatchStatus() == WatchStatus.WANT_TO_WATCH) {
                userSeriesStatus.setWatchStatus(WatchStatus.WATCHING);
                seriesStatusRepository.save(userSeriesStatus);
            }
        } else {
            userSeriesStatus = new UserSeriesStatus();
            userSeriesStatus.setUser(user);
            userSeriesStatus.setSeries(series);
            userSeriesStatus.setWatchStatus(WatchStatus.WATCHING);
            seriesStatusRepository.save(userSeriesStatus);
        }
    }

    public void removeUserMovieStatusWatching(String username, Long movieId) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return;
        }

        Movie movie = movieRepository.findById(movieId).orElse(null);
        if (movie == null) {
            return;
        }

        UserMovieStatus userMovieStatus = movieStatusRepository.findByUserAndMovieAndWatchStatus(user, movie, WatchStatus.WATCHING);
        if (userMovieStatus != null) {
            movieStatusRepository.delete(userMovieStatus);
        }
    }

    public void removeUserSeriesStatusWatching(String username, Long seriesId) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return;
        }

        Series series = seriesRepository.findById(seriesId).orElse(null);
        if (series == null) {
            return;
        }

        UserSeriesStatus userSeriesStatus = seriesStatusRepository.findByUserAndSeriesAndWatchStatus(user, series, WatchStatus.WATCHING);
        if (userSeriesStatus != null) {
            seriesStatusRepository.delete(userSeriesStatus);
        }
    }
}
