package com.app.watch_wise_backend.model.diary;

import com.app.watch_wise_backend.model.content.Series;
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
@Table(name = "user_series_diary")
public class UserSeriesDiary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "series_id", nullable = false)
    private Series series;

    @Column(name = "date_added", nullable = false)
    private LocalDateTime dateAdded;
}
