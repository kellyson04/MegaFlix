package com.MegaFlixTV.MegaFlix.service;

import com.MegaFlixTV.MegaFlix.controller.request.UserLoginRequest;
import com.MegaFlixTV.MegaFlix.controller.request.UserMovieRequest;
import com.MegaFlixTV.MegaFlix.controller.response.UserLoginResponse;
import com.MegaFlixTV.MegaFlix.controller.response.UserMovieResponse;
import com.MegaFlixTV.MegaFlix.controller.response.UserResponse;
import com.MegaFlixTV.MegaFlix.entity.Movie;
import com.MegaFlixTV.MegaFlix.entity.User;
import com.MegaFlixTV.MegaFlix.entity.UserMovie;
import com.MegaFlixTV.MegaFlix.exception.*;
import com.MegaFlixTV.MegaFlix.mapper.UserMapper;
import com.MegaFlixTV.MegaFlix.mapper.UserMovieMapper;
import com.MegaFlixTV.MegaFlix.repository.MovieRepository;
import com.MegaFlixTV.MegaFlix.repository.UserMovieRepository;
import com.MegaFlixTV.MegaFlix.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserMovieService {

    private final UserMovieRepository userMovieRepository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;
    private final PasswordEncoder passwordEncoder;

    public UserMovieService(UserMovieRepository userMovieRepository, UserRepository userRepository, MovieRepository movieRepository, PasswordEncoder passwordEncoder) {
        this.userMovieRepository = userMovieRepository;
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserMovieResponse adicionarFilmeAoUsuario (Long user,Long movie) {
        User verificarUsuario = userRepository.findById(user).orElseThrow(() -> new UserNotFoundException("Este Usuario não existe."));
        Movie verificarFilme = movieRepository.findById(movie).orElseThrow(() -> new MovieNotFoundException("Este Filme não existe."));

        if (userMovieRepository.existsByUserIdAndMovieId(user,movie)) {
            throw new BusinessRuleException("Usuario ja possui este filme na playlist.");
        }


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


    public UserMovieResponse assistirFilme (Long userId,Long movieId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("Este usuario não existe"));

        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new MovieNotFoundException("Este filme não existe"));

        UserMovie userMovie = userMovieRepository.findByUserAndMovie(user,movie).orElseThrow(() -> new RelationNotFoundException("Usuario não possui o filme na playlist"));

        if (!userMovie.isWatched()) {
            userMovie.setWatched(true);
            userMovieRepository.save(userMovie);
            return UserMovieMapper.mapToResponse(userMovie);
        }else {
            throw new BusinessRuleException("O filme ja foi assistido!");
        }


    }

    public void adicionarFavorito (UserLoginRequest userLoginRequest,Long relacaoId) {

        autenticarUsuario(userLoginRequest);

        UserMovie relacao = userMovieRepository.findById(relacaoId).orElseThrow(() -> new RelationNotFoundException("Relação não existente."));

        if (!userLoginRequest.user().equals(relacao.getUser().getUser())) {
            throw new RelationNotFoundException("Voce está tentando favoritar um filme que não é da sua Playlist.");
        }

        if (!relacao.isFavorite()) {
            relacao.setFavorite(true);
            userMovieRepository.save(relacao);
        }else {
            throw new BusinessRuleException("Filme ja esta favoritado");
        }
    }

    public void removerFavorito (UserLoginRequest userLoginRequest,Long relacaoId) {
        autenticarUsuario(userLoginRequest);

        UserMovie userMovie = userMovieRepository.findById(relacaoId).orElseThrow(() -> new RelationNotFoundException("O usuario não possui esse Filme na Playlist!"));

        if (!userLoginRequest.user().equals(userMovie.getUser().getUser())) {
            throw new RelationNotFoundException("Voce está tentando desfavoritar um filme que não é da sua Playlist.");
        }

        if (!userMovie.isFavorite()) {
            throw new BusinessRuleException("O filme não esta favoritado!");
        }

        userMovie.setFavorite(false);
        userMovieRepository.save(userMovie);
    }

    public List<UserMovieResponse> listarFilmesFavoritados () {
       List<UserMovie> userMovies = userMovieRepository.findByFavoriteIsTrue();

        return userMovies
                .stream()
                .map(userMovie -> UserMovieMapper.mapToResponse(userMovie))
                .toList();
    }

    public List<UserMovieResponse> filmesFavoritadosDoUsuario (Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("Usuario não encontrado!"));

        List<UserMovie> userMovie = userMovieRepository.findByUserAndFavoriteTrue(user);

        return userMovie.stream()
                .map(filme -> UserMovieMapper.mapToResponse(filme))
                .toList();
    }

    private void autenticarUsuario (UserLoginRequest userLoginRequest) {
        User user = userRepository.findUserByUser(userLoginRequest.user()).orElseThrow(() -> new InvalidCredentialsException("Dados de login invalidos"));

        if (!passwordEncoder.matches(userLoginRequest.password(),user.getPassword())) {
            throw new InvalidCredentialsException("Dados de Login invalidos");
        }

    }

}

