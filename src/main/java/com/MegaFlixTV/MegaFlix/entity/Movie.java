package com.MegaFlixTV.MegaFlix.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "movie")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "movie", length = 100, nullable = false)
    private String movie;

    @Column(name = "genre", length = 25, nullable = false)
    private String genre;

    @Column(name = "duration", length = 5, nullable = false)
    private double duration;

}

