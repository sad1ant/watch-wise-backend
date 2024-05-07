package com.app.watch_wise_backend.model.content;

import com.app.watch_wise_backend.model.diary.UserMovieDiary;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "release_year", nullable = false)
    private Integer releaseYear;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "genre", nullable = false)
    private String genre;

    @Column(name = "slogan", columnDefinition = "TEXT")
    private String slogan;

    @Column(name = "directors", nullable = false)
    private String directors;

    @Column(name = "producers", nullable = false)
    private String producers;

    @Column(name = "writers", nullable = false)
    private String writers;

    @Column(name = "cinematographers", nullable = false)
    private String cinematographers;

    @Column(name = "composers", nullable = false)
    private String composers;

    @Column(name = "artists", nullable = false)
    private String artists;

    @Column(name = "editors", nullable = false)
    private String editors;

    @Column(name = "budget", nullable = false)
    private Double budget;

    @Column(name = "age_rating", nullable = false)
    private String ageRating;

    @Column(name = "duration", nullable = false)
    private Integer duration;

    @Column(name = "rating")
    private Double rating;

    @Column(name = "image", nullable = false)
    private String image;

    @OneToMany(mappedBy = "movie")
    private List<UserMovieStatus> movieStatuses;

    @OneToMany(mappedBy = "movie")
    private List<UserMovieDiary> movieDiary;
}
