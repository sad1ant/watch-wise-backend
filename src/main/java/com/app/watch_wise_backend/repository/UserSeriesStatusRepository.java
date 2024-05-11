package com.app.watch_wise_backend.repository;

import com.app.watch_wise_backend.model.content.Series;
import com.app.watch_wise_backend.model.content.UserSeriesStatus;
import com.app.watch_wise_backend.model.content.WatchStatus;
import com.app.watch_wise_backend.model.user.User;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;

@Registered
public interface UserSeriesStatusRepository extends JpaRepository<UserSeriesStatus, Long> {
    UserSeriesStatus findByUserAndSeriesAndWatchStatus(User user, Series series, WatchStatus watchStatus);
    UserSeriesStatus findByUserAndSeries(User user, Series series);
}
