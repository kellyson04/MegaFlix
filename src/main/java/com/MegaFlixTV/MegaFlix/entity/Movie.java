package com.MegaFlixTV.MegaFlix.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

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

    @ManyToMany(mappedBy = "movie")
    private List<Streaming> streaming;

    @Column(name = "movie", length = 100, nullable = false)
    private String movie;

    @Column(name = "genre", length = 25, nullable = false)
    private String genre;

    @Column(name = "duration", length = 5, nullable = false)
    private Double duration;

    @Column(name = "release_year")
    private Integer releaseYear;

    @Column(name = "created_at",updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "rating")
    private Double rating;
}

