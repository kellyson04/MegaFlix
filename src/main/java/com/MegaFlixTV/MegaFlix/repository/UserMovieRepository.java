package com.MegaFlixTV.MegaFlix.repository;

import com.MegaFlixTV.MegaFlix.entity.UserMovie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMovieRepository extends JpaRepository<UserMovie,Long> {
}
