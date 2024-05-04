package com.app.watch_wise_backend.repository;

import com.app.watch_wise_backend.model.content.Episode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EpisodeRepository extends JpaRepository<Episode, Long> {}
