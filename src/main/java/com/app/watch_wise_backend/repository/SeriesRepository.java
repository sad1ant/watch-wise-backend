package com.app.watch_wise_backend.repository;

import com.app.watch_wise_backend.model.content.Series;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeriesRepository extends JpaRepository<Series, Long> {
    Page<Series> findAll(Specification<Series> spec, Pageable pageable);

}
