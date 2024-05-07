package com.app.watch_wise_backend.model.diary;

import com.app.watch_wise_backend.model.content.Movie;
import com.app.watch_wise_backend.model.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user_movie_diary")
public class UserMovieDiary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @Column(name = "date_added", nullable = false)
    private LocalDateTime dateAdded;
}
