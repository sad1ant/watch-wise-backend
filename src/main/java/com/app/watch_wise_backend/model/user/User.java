package com.app.watch_wise_backend.model.user;

import com.app.watch_wise_backend.model.content.*;
import com.app.watch_wise_backend.model.diary.*;
import com.app.watch_wise_backend.model.review.Review;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<UserMovieStatus> movieStatuses;

    @OneToMany(mappedBy = "user")
    private List<UserSeriesStatus> seriesStatuses;

    @OneToMany(mappedBy = "user")
    private List<UserEpisodeStatus> episodeStatuses;

    @OneToMany(mappedBy = "user")
    private List<UserMovieDiary> movieDiary;

    @OneToMany(mappedBy = "user")
    private List<UserSeriesDiary> seriesDiary;

    @OneToMany(mappedBy = "user")
    private List<Review> review;

    @OneToMany(mappedBy = "user")
    private List<UserRating> userRatings;
}
