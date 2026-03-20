package com.MegaFlixTV.MegaFlix.service;

import com.MegaFlixTV.MegaFlix.controller.response.UserMovieResponse;
import com.MegaFlixTV.MegaFlix.entity.Movie;
import com.MegaFlixTV.MegaFlix.entity.User;
import com.MegaFlixTV.MegaFlix.entity.UserMovie;
import com.MegaFlixTV.MegaFlix.mapper.UserMovieMapper;
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

    public UserMovieResponse adicionarFilmeAoUsuario (Long user,Long movie) {
        User verificarUsuario = userRepository.findById(user).orElseThrow(() -> new RuntimeException("Este Usuario não existe."));
        Movie verificarFilme = movieRepository.findById(movie).orElseThrow(() -> new RuntimeException("Este Filme não existe."));

        UserMovie vincularUsuarioFilme = new UserMovie();

        vincularUsuarioFilme.setUser(verificarUsuario);
        vincularUsuarioFilme.setMovie(verificarFilme);
        vincularUsuarioFilme.setFavorite(false);
        vincularUsuarioFilme.setWatched(false);

        userMovieRepository.save(vincularUsuarioFilme);

        return UserMovieMapper.mapToResponse(vincularUsuarioFilme);
    }

    public List<UserMovieResponse> listarFilmesdeUsuarios () {
       List<UserMovie> listaEntity = userMovieRepository.findAll();

       return listaEntity.stream()
               .map(userMovie -> UserMovieMapper.mapToResponse(userMovie))
               .toList();
    }

    public UserMovieResponse listarRelacaoEspecifica (Long id) {
        UserMovie userMovie = userMovieRepository.findById(id).orElseThrow(() -> new RuntimeException("Esta relação não existe."));

        return UserMovieMapper.mapToResponse(userMovie);
    }

    public void deletarRelacao (Long id) {
        UserMovie userMovie = userMovieRepository.findById(id).orElseThrow(() -> new RuntimeException("Esta relação não existe."));

        userMovieRepository.deleteById(id);
    }

    public void adicionarFavorito (Long userId,Long movieId,Long relationId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Este usuario não existe"));

        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new RuntimeException("Este filme não existe"));

        UserMovie userMovie = userMovieRepository.findById(relationId).orElseThrow(() -> new RuntimeException("Filme ou usuario não possuem relação para favoritar."));

        userMovie.setFavorite(true);

        userMovieRepository.save(userMovie);
    }

    public UserMovieResponse assistirFilme (Long userId,Long movieId,Long relationId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Este usuario não existe"));

        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new RuntimeException("Este filme não existe"));

        UserMovie userMovie = userMovieRepository.findById(relationId).orElseThrow(() -> new RuntimeException("Usuario não possui o filme na playlist"));

        userMovie.setWatched(true);

        userMovieRepository.save(userMovie);

        return UserMovieMapper.mapToResponse(userMovie);
    }
}

