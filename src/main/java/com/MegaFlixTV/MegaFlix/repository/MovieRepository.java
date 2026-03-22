package com.MegaFlixTV.MegaFlix.repository;

import com.MegaFlixTV.MegaFlix.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie,Long> {
    List<Movie> findMovieByGenre(String genre);
    List<Movie> findMovieByDuration(double duration);
}
