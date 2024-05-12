package com.app.watch_wise_backend.repository;

import com.app.watch_wise_backend.model.content.Movie;
import com.app.watch_wise_backend.model.content.UserMovieStatus;
import com.app.watch_wise_backend.model.content.WatchStatus;
import com.app.watch_wise_backend.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMovieStatusRepository extends JpaRepository<UserMovieStatus, Long> {
    UserMovieStatus findByUserAndMovieAndWatchStatus(User user, Movie movie, WatchStatus watchStatus);
    UserMovieStatus findByUserAndMovie(User user, Movie movie);
    List<UserMovieStatus> findByUserAndWatchStatus(User user, WatchStatus watchStatus);
}
