package com.app.watch_wise_backend.repository;

import com.app.watch_wise_backend.model.content.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    Page<Movie> findAll(Specification<Movie> spec, Pageable pageable);
    List<Movie> findAll(Specification<Movie> spec);
}
