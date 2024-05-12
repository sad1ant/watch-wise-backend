package com.app.watch_wise_backend.service;

import com.app.watch_wise_backend.model.content.*;
import com.app.watch_wise_backend.model.user.User;
import com.app.watch_wise_backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    @Autowired
    private UserEpisodeStatusRepository episodeStatusRepository;
    @Autowired
    private EpisodeRepository episodeRepository;

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
            userMovieStatus.setWatchStatus(WatchStatus.INTERESTED);
        }

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
            userSeriesStatus.setWatchStatus(WatchStatus.INTERESTED);
        }

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

        UserMovieStatus userMovieStatusWantToWatch = movieStatusRepository.findByUserAndMovieAndWatchStatus(user, movie, WatchStatus.WANT_TO_WATCH);
        if (userMovieStatusWantToWatch != null) {
            UserMovieStatus userMovieStatusWatched = movieStatusRepository.findByUserAndMovieAndWatchStatus(user, movie, WatchStatus.WATCHED);
            if (userMovieStatusWatched != null) {
                return;
            }
            userMovieStatusWantToWatch.setWatchStatus(WatchStatus.WATCHING);
            movieStatusRepository.save(userMovieStatusWantToWatch);
        } else {
            UserMovieStatus userMovieStatusWatched = movieStatusRepository.findByUserAndMovieAndWatchStatus(user, movie, WatchStatus.WATCHED);
            if (userMovieStatusWatched != null) {
                return;
            }
            userMovieStatusWantToWatch = new UserMovieStatus();
            userMovieStatusWantToWatch.setUser(user);
            userMovieStatusWantToWatch.setMovie(movie);
            userMovieStatusWantToWatch.setWatchStatus(WatchStatus.WATCHING);
            movieStatusRepository.save(userMovieStatusWantToWatch);
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

        UserSeriesStatus userSeriesStatusWantToWatch = seriesStatusRepository.findByUserAndSeriesAndWatchStatus(user, series, WatchStatus.WANT_TO_WATCH);
        if (userSeriesStatusWantToWatch != null) {
            UserSeriesStatus userSeriesStatusWatched = seriesStatusRepository.findByUserAndSeriesAndWatchStatus(user, series, WatchStatus.WATCHED);
            if (userSeriesStatusWatched != null) {
                return;
            }
            userSeriesStatusWantToWatch.setWatchStatus(WatchStatus.WATCHING);
            seriesStatusRepository.save(userSeriesStatusWantToWatch);
        } else {
            UserSeriesStatus userSeriesStatusWatched = seriesStatusRepository.findByUserAndSeriesAndWatchStatus(user, series, WatchStatus.WATCHED);
            if (userSeriesStatusWatched != null) {
                return;
            }
            userSeriesStatusWantToWatch = new UserSeriesStatus();
            userSeriesStatusWantToWatch.setUser(user);
            userSeriesStatusWantToWatch.setSeries(series);
            userSeriesStatusWantToWatch.setWatchStatus(WatchStatus.WATCHING);
            seriesStatusRepository.save(userSeriesStatusWantToWatch);
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

    // WATCHED STATUS
    public void setUserMovieStatusWatched(String username, Long movieId) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return;
        }

        Movie movie = movieRepository.findById(movieId).orElse(null);
        if (movie == null) {
            return;
        }

        UserMovieStatus userMovieStatusWatched = movieStatusRepository.findByUserAndMovieAndWatchStatus(user, movie, WatchStatus.WATCHED);
        if (userMovieStatusWatched == null) {
            UserMovieStatus userMovieStatusWantToWatch = movieStatusRepository.findByUserAndMovieAndWatchStatus(user, movie, WatchStatus.WANT_TO_WATCH);
            UserMovieStatus userMovieStatusWatching = movieStatusRepository.findByUserAndMovieAndWatchStatus(user, movie, WatchStatus.WATCHING);
            if (userMovieStatusWatching != null) {
                userMovieStatusWatching.setWatchStatus(WatchStatus.WATCHED);
                movieStatusRepository.save(userMovieStatusWatching);
            } else if (userMovieStatusWantToWatch != null) {
                userMovieStatusWantToWatch.setWatchStatus(WatchStatus.WANT_TO_WATCH);
                movieStatusRepository.save(userMovieStatusWantToWatch);
            } else {
                userMovieStatusWatched = new UserMovieStatus();
                userMovieStatusWatched.setUser(user);
                userMovieStatusWatched.setMovie(movie);
                userMovieStatusWatched.setWatchStatus(WatchStatus.WATCHED);
                movieStatusRepository.save(userMovieStatusWatched);
            }
        }


    }

    public void setUserSeriesStatusWatched(String username, Long seriesId) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return;
        }

        Series series = seriesRepository.findById(seriesId).orElse(null);
        if (series == null) {
            return;
        }

        UserSeriesStatus userSeriesStatusWatched = seriesStatusRepository.findByUserAndSeriesAndWatchStatus(user, series, WatchStatus.WATCHED);
        if (userSeriesStatusWatched == null) {
            UserSeriesStatus userSeriesStatusWatching = seriesStatusRepository.findByUserAndSeriesAndWatchStatus(user, series, WatchStatus.WATCHING);
            UserSeriesStatus userSeriesStatusWantToWatch = seriesStatusRepository.findByUserAndSeriesAndWatchStatus(user, series, WatchStatus.WANT_TO_WATCH);
            if (userSeriesStatusWatching != null) {
                userSeriesStatusWatching.setWatchStatus(WatchStatus.WATCHED);

                List<Episode> episodes = series.getEpisodes();
                for (Episode episode : episodes) {
                    setUserEpisodeStatusWatched(username, episode.getId(), seriesId);
                }

                seriesStatusRepository.save(userSeriesStatusWatching);
            } else if (userSeriesStatusWantToWatch != null) {
                userSeriesStatusWantToWatch.setWatchStatus(WatchStatus.WATCHED);

                List<Episode> episodes = series.getEpisodes();
                for (Episode episode : episodes) {
                    setUserEpisodeStatusWatched(username, episode.getId(), seriesId);
                }

                seriesStatusRepository.save(userSeriesStatusWantToWatch);
            } else {
                userSeriesStatusWatched = new UserSeriesStatus();
                userSeriesStatusWatched.setUser(user);
                userSeriesStatusWatched.setSeries(series);
                userSeriesStatusWatched.setWatchStatus(WatchStatus.WATCHED);
                seriesStatusRepository.save(userSeriesStatusWatched);

                List<Episode> episodes = series.getEpisodes();
                for (Episode episode : episodes) {
                    setUserEpisodeStatusWatched(username, episode.getId(), seriesId);
                }
            }
        }
    }

    public void setUserEpisodeStatusWatched(String username, Long episodeId, Long seriesId) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return;
        }

        Episode episode = episodeRepository.findById(episodeId).orElse(null);
        if (episode == null) {
            return;
        }

        UserEpisodeStatus userEpisodeStatus = episodeStatusRepository.findByUserAndEpisode(user, episode);
        if (userEpisodeStatus == null) {
            userEpisodeStatus = new UserEpisodeStatus();
            userEpisodeStatus.setUser(user);
            userEpisodeStatus.setEpisode(episode);
            userEpisodeStatus.setWatchStatus(WatchStatus.WATCHED);
            episodeStatusRepository.save(userEpisodeStatus);
        }

        Series series = seriesRepository.findById(seriesId).orElse(null);
        if (series == null) {
            return;
        }

        boolean allWatched = series.getEpisodes().stream().allMatch(ep -> {
            UserEpisodeStatus episodeStatus = episodeStatusRepository.findByUserAndEpisode(user, ep);
            return episodeStatus != null && episodeStatus.getWatchStatus() == WatchStatus.WATCHED;
        });

        if (allWatched) {
            UserSeriesStatus userSeriesStatus = seriesStatusRepository.findByUserAndSeriesAndWatchStatus(user, series, WatchStatus.WATCHED);
            if (userSeriesStatus == null) {
                UserSeriesStatus userSeriesStatusWatching = seriesStatusRepository.findByUserAndSeriesAndWatchStatus(user, series, WatchStatus.WATCHING);
                if (userSeriesStatusWatching != null) {
                    seriesStatusRepository.delete(userSeriesStatusWatching);
                    userSeriesStatusWatching.setWatchStatus(WatchStatus.WATCHED);
                    seriesStatusRepository.save(userSeriesStatusWatching);
                } else {
                    userSeriesStatus = new UserSeriesStatus();
                    userSeriesStatus.setUser(user);
                    userSeriesStatus.setSeries(series);
                    userSeriesStatus.setWatchStatus(WatchStatus.WATCHED);
                    seriesStatusRepository.save(userSeriesStatus);
                }
            }
        } else {
            UserSeriesStatus userSeriesStatus = seriesStatusRepository.findByUserAndSeriesAndWatchStatus(user, series, WatchStatus.WATCHING);
            if (userSeriesStatus == null) {
                UserSeriesStatus userSeriesStatusWatched = seriesStatusRepository.findByUserAndSeriesAndWatchStatus(user, series, WatchStatus.WATCHED);
                UserSeriesStatus userSeriesStatusWantToWatch = seriesStatusRepository.findByUserAndSeriesAndWatchStatus(user, series, WatchStatus.WANT_TO_WATCH);
                if (userSeriesStatusWatched != null) {
                    return;
                } else if (userSeriesStatusWantToWatch != null) {
                    seriesStatusRepository.delete(userSeriesStatusWantToWatch);
                    userSeriesStatusWantToWatch.setWatchStatus(WatchStatus.WATCHING);
                    seriesStatusRepository.save(userSeriesStatusWantToWatch);
                } else {
                    userSeriesStatus = new UserSeriesStatus();
                    userSeriesStatus.setUser(user);
                    userSeriesStatus.setSeries(series);
                    userSeriesStatus.setWatchStatus(WatchStatus.WATCHING);
                    seriesStatusRepository.save(userSeriesStatus);
                }
            }
        }
    }

    // RECOMMENDED STATUS
}
