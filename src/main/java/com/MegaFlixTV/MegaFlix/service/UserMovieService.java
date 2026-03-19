package com.MegaFlixTV.MegaFlix.service;

import com.MegaFlixTV.MegaFlix.entity.Movie;
import com.MegaFlixTV.MegaFlix.entity.User;
import com.MegaFlixTV.MegaFlix.entity.UserMovie;
import com.MegaFlixTV.MegaFlix.repository.MovieRepository;
import com.MegaFlixTV.MegaFlix.repository.UserMovieRepository;
import com.MegaFlixTV.MegaFlix.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserMovieService {

    private final UserMovieRepository userMovieRepository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;

    public UserMovieService(UserMovieRepository userMovieRepository, UserRepository userRepository, MovieRepository movieRepository) {
        this.userMovieRepository = userMovieRepository;
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
    }

    public UserMovie adicionarFilmeAoUsuario (Long userId,Long movieId) {
        User verificarUsuario = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Este Usuario não existe."));
        Movie verificarFilme = movieRepository.findById(movieId).orElseThrow(() -> new RuntimeException("Este Filme não existe."));

        UserMovie vincularUsuarioFilme = new UserMovie();

        vincularUsuarioFilme.setUserId(verificarUsuario);
        vincularUsuarioFilme.setMovieId(verificarFilme);
        vincularUsuarioFilme.setFavorite(false);
        vincularUsuarioFilme.setWatched(false);

        return userMovieRepository.save(vincularUsuarioFilme);
    }

    public List<UserMovie> listarFilmesdeUsuarios () {
        return userMovieRepository.findAll();
    }
}

