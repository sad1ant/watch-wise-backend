package com.app.watch_wise_backend.model.review;

import com.app.watch_wise_backend.model.content.*;
import com.app.watch_wise_backend.model.user.User;
import jakarta.persistence.*;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "series_id")
    private Series series;

    @Column(name = "description")
    private String description;

    @Column(name = "reason_deletion")
    private String reasonDeletion;

    @Enumerated(EnumType.STRING)
    private ReviewStatus status;
}
