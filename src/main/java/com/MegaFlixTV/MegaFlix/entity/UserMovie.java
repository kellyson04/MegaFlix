package com.MegaFlixTV.MegaFlix.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users_movies")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserMovie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User userId;

    @ManyToOne
    @JoinColumn(name = "movie_id",nullable = false)
    private Movie movieId;

    @Column(name = "favorite",nullable = false)
    private boolean favorite;

    @Column(name = "watched", nullable = false)
    private boolean watched;

}
