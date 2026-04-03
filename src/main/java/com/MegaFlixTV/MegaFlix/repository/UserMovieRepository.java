package com.MegaFlixTV.MegaFlix.repository;

import com.MegaFlixTV.MegaFlix.entity.Movie;
import com.MegaFlixTV.MegaFlix.entity.User;
import com.MegaFlixTV.MegaFlix.entity.UserMovie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserMovieRepository extends JpaRepository<UserMovie,Long> {

    Optional<UserMovie> findByUserAndMovie (User user, Movie movie);
    List<UserMovie> findByFavoriteIsTrue();
    List<UserMovie> findByUserAndFavoriteTrue(User user);
    boolean existsByUserIdAndMovieId(Long userId,Long movieId);
}
