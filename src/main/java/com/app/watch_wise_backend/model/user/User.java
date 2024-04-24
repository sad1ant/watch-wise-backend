package com.app.watch_wise_backend.model.user;

import com.app.watch_wise_backend.model.content.UserContentRecommendation;
import com.app.watch_wise_backend.model.content.UserContentStatus;
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
    private List<UserContentRecommendation> contentRecommendations;

    @OneToMany(mappedBy = "user")
    private List<UserContentStatus> contentStatus;
}
