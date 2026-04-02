package com.MegaFlixTV.MegaFlix.repository;

import com.MegaFlixTV.MegaFlix.entity.Movie;
import com.MegaFlixTV.MegaFlix.entity.Streaming;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie,Long> {
    List<Movie> findMovieByGenre(String genre);
    List<Movie> findMovieByDurationGreaterThanEqual(double duration);
    List<Movie> findMovieByDurationLessThanEqual(double duration);
    List<Movie> findMovieByMovieContainingIgnoreCase (String title);
    boolean existsByIdAndStreamingId(Long movieId,Long streamingId);
}
