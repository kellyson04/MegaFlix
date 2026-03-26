package com.MegaFlixTV.MegaFlix.service;

import com.MegaFlixTV.MegaFlix.controller.request.UserMovieRequest;
import com.MegaFlixTV.MegaFlix.controller.response.UserMovieResponse;
import com.MegaFlixTV.MegaFlix.entity.Movie;
import com.MegaFlixTV.MegaFlix.entity.User;
import com.MegaFlixTV.MegaFlix.entity.UserMovie;
import com.MegaFlixTV.MegaFlix.exception.MovieNotFoundException;
import com.MegaFlixTV.MegaFlix.exception.RelationNotFoundException;
import com.MegaFlixTV.MegaFlix.exception.UserNotFoundException;
import com.MegaFlixTV.MegaFlix.mapper.UserMovieMapper;
import com.MegaFlixTV.MegaFlix.repository.MovieRepository;
import com.MegaFlixTV.MegaFlix.repository.UserMovieRepository;
import com.MegaFlixTV.MegaFlix.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        User verificarUsuario = userRepository.findById(user).orElseThrow(() -> new UserNotFoundException("Este Usuario não existe."));
        Movie verificarFilme = movieRepository.findById(movie).orElseThrow(() -> new MovieNotFoundException("Este Filme não existe."));

        //REVISAR ISSO AQ SE PA TA RETORNANDO EMAIL E SENHA NA RELACAO POR CAUSA DO SET ALI EMBAIXO Q N TEM MATPTOESPONSE
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
        UserMovie userMovie = userMovieRepository.findById(id).orElseThrow(() -> new RelationNotFoundException("Esta relação não existe."));

        return UserMovieMapper.mapToResponse(userMovie);
    }

    public void deletarRelacao (Long id) {
        UserMovie userMovie = userMovieRepository.findById(id).orElseThrow(() -> new RelationNotFoundException("Esta relação não existe."));

        userMovieRepository.deleteById(id);
    }


    public UserMovieResponse assistirFilme (Long userId,Long movieId,Long relationId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RelationNotFoundException("Este usuario não existe"));

        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new MovieNotFoundException("Este filme não existe"));

        UserMovie userMovie = userMovieRepository.findById(relationId).orElseThrow(() -> new RelationNotFoundException("Usuario não possui o filme na playlist"));


            userMovie.setWatched(true);

            userMovieRepository.save(userMovie);
            return UserMovieMapper.mapToResponse(userMovie);

    }

    public void adicionarFavorito (Long relacaoId) {

        UserMovie relacao = userMovieRepository.findById(relacaoId).orElseThrow(() -> new RelationNotFoundException("Relação não existente."));

        relacao.setFavorite(true);

        if (!relacao.isFavorite()) {
            relacao.setFavorite(true);
            userMovieRepository.save(relacao);
        }else {
            throw new RuntimeException("Filme ja favoritado");
        }


    }
}

