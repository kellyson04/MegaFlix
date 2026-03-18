package com.MegaFlixTV.MegaFlix.service;

import com.MegaFlixTV.MegaFlix.controller.request.MovieRequest;
import com.MegaFlixTV.MegaFlix.controller.response.MovieResponse;
import com.MegaFlixTV.MegaFlix.entity.Movie;
import com.MegaFlixTV.MegaFlix.mapper.MovieMapper;
import com.MegaFlixTV.MegaFlix.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService {
    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<MovieResponse> listarFilmes () {
        List<Movie> moviesEntity = movieRepository.findAll();

        return moviesEntity.stream()
                .map(movie -> MovieMapper.toResponse(movie))
                .toList();
    }

    public MovieRequest adicionarFilme (MovieRequest movieRequest) {
         movieRepository.save(MovieMapper.toEntity(movieRequest));

        return movieRequest;
    }

    public MovieResponse listarFilmeEspecifico (Long id) {
        Movie procurarFilme = movieRepository.findById(id).orElseThrow(() -> new RuntimeException("ID não encontrado"));

        return MovieMapper.toResponse(procurarFilme);
    }

    public MovieResponse AlterarFilmePorCompleto (Long id, MovieRequest movieRequest) {
        Movie procurarFilme = movieRepository.findById(id).orElseThrow(() -> new RuntimeException("ID não encontrado"));


        procurarFilme.setMovie(movieRequest.movie());
        procurarFilme.setGenre(movieRequest.genre());
        procurarFilme.setDuration(movieRequest.duration());

        movieRepository.save(procurarFilme);

        return MovieMapper.toResponse(procurarFilme);
    }

    public void deletarFilme (Long id) {
        movieRepository.deleteById(id);
    }
}
