package com.app.watch_wise_backend.repository;

import com.app.watch_wise_backend.model.content.Episode;
import com.app.watch_wise_backend.model.content.UserEpisodeStatus;
import com.app.watch_wise_backend.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserEpisodeStatusRepository extends JpaRepository<UserEpisodeStatus, Long> {
    UserEpisodeStatus findByUserAndEpisode(User user, Episode episode);
}
