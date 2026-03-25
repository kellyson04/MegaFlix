package com.MegaFlixTV.MegaFlix.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "streaming")
public class Streaming {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name",length = 100, nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(
            name = "streaming_movie",
            joinColumns = @JoinColumn(name = "streaming_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id")

    )
    private List<Movie> movie;

}
