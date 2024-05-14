package com.app.watch_wise_backend.repository;

import com.app.watch_wise_backend.model.content.Movie;
import com.app.watch_wise_backend.model.content.Series;
import com.app.watch_wise_backend.model.content.UserRating;
import com.app.watch_wise_backend.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRatingRepository extends JpaRepository<UserRating, Long> {
    UserRating findByUserAndMovie(User user, Movie movie);
    UserRating findByUserAndSeries(User user, Series series);
}
