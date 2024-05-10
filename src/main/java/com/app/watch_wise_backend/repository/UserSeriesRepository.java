package com.app.watch_wise_backend.repository;

import com.app.watch_wise_backend.model.diary.UserSeriesDiary;
import com.app.watch_wise_backend.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserSeriesRepository extends JpaRepository<UserSeriesDiary, Long> {
    Optional<UserSeriesDiary> findByUserAndSeriesId(User user, Long seriesId);
    List<UserSeriesDiary> findByUser(User user);
}
