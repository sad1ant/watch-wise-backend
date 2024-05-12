package com.app.watch_wise_backend.repository;

import com.app.watch_wise_backend.model.content.Movie;
import com.app.watch_wise_backend.model.review.Review;
import com.app.watch_wise_backend.model.review.ReviewStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByMovieAndStatus(Movie movie, ReviewStatus status);
}
