package com.app.watch_wise_backend.repository;

import com.app.watch_wise_backend.model.diary.UserMovieDiary;
import com.app.watch_wise_backend.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserMovieRepository extends JpaRepository<UserMovieDiary, Long> {
    Optional<UserMovieDiary> findByUserAndMovieId(User user, Long movieId);
    List<UserMovieDiary> findByUser(User user);
}
