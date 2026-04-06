package com.MegaFlixTV.MegaFlix.service;

import com.MegaFlixTV.MegaFlix.controller.request.MovieRequest;
import com.MegaFlixTV.MegaFlix.controller.response.MovieResponse;
import com.MegaFlixTV.MegaFlix.controller.response.StreamingResponse;
import com.MegaFlixTV.MegaFlix.entity.Movie;
import com.MegaFlixTV.MegaFlix.entity.Streaming;
import com.MegaFlixTV.MegaFlix.exception.BusinessRuleException;
import com.MegaFlixTV.MegaFlix.exception.MovieNotFoundException;
import com.MegaFlixTV.MegaFlix.exception.StreamingNotFoundException;
import com.MegaFlixTV.MegaFlix.mapper.MovieMapper;
import com.MegaFlixTV.MegaFlix.mapper.StreamingMapper;
import com.MegaFlixTV.MegaFlix.repository.MovieRepository;
import com.MegaFlixTV.MegaFlix.repository.StreamingRepository;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieService {
    private final MovieRepository movieRepository;
    private final StreamingRepository streamingRepository;

    public MovieService(MovieRepository movieRepository, StreamingRepository streamingRepository) {
        this.movieRepository = movieRepository;
        this.streamingRepository = streamingRepository;
    }

    public List<MovieResponse> listarFilmes (String genero,String titulo,Double duracaoMaior,Double duracaoMenor) {
        List<Movie> moviesFiltro = movieRepository.findAll();

        if (genero != null && !genero.isBlank()) {
            moviesFiltro = moviesFiltro.stream()
                    .filter(filme -> filme.getGenre().toUpperCase().contains(genero.toUpperCase()))
                    .toList();
        }
        if (titulo != null && !titulo.isBlank() ) {
                moviesFiltro = moviesFiltro.stream()
                        .filter(filme -> filme.getMovie().toUpperCase().contains(titulo.toUpperCase()))
                        .toList();
        }
        if(duracaoMaior != null) {
            moviesFiltro = moviesFiltro.stream()
                    .filter(filme -> filme.getDuration() > duracaoMaior)
                    .toList();

        }
        if (duracaoMenor != null) {
            moviesFiltro = moviesFiltro.stream()
                    .filter(filme -> filme.getDuration() < duracaoMenor)
                    .toList();
        }
        return moviesFiltro.stream()
                .map(filme -> MovieMapper.toResponse(filme))
                .toList();
    }

    public MovieResponse adicionarFilme (MovieRequest movieRequest) {
        Movie movie = movieRepository.save(MovieMapper.toEntity(movieRequest));

        return MovieMapper.toResponse(movie);
    }

    public MovieResponse listarFilmeEspecifico (Long id) {
        Movie procurarFilme = movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundException("Filme não encontrado"));

        return MovieMapper.toResponse(procurarFilme);
    }


    public MovieResponse alterarFilmePorCompleto (Long id, MovieRequest movieRequest) {
        Movie procurarFilme = movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundException("Filme não encontrado"));


        procurarFilme.setMovie(movieRequest.movie());
        procurarFilme.setGenre(movieRequest.genre());
        procurarFilme.setDuration(movieRequest.duration());

        movieRepository.save(procurarFilme);

        return MovieMapper.toResponse(procurarFilme);
    }

    public void deletarFilme (Long id) {
        Movie filmePraDeletar = movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundException("Impossivel Deletar um filme inexistente"));

        movieRepository.deleteById(id);
    }

    public MovieResponse adicionarFilmeNoStreaming (Long movieId,Long streamingId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new MovieNotFoundException("Filme não encontrado!"));
        Streaming streaming = streamingRepository.findById(streamingId).orElseThrow(() -> new StreamingNotFoundException("Streaming não encontrado"));

        if (movieRepository.existsByIdAndStreamingId(movieId,streamingId)) {
            throw new BusinessRuleException("Esse filme ja existe no Streaming");
        }

        streaming.getMovie().add(movie);

        streamingRepository.save(streaming);


        return MovieMapper.toResponse(movie);
    }

    public List<StreamingResponse> StreamingsQueOFilmeSeEncontra(Long movieId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new MovieNotFoundException("Filme não existe"));


        return movie.getStreaming()
                .stream()
                .map(cadaStreaming -> StreamingMapper.toResponse(cadaStreaming))
                .toList();
    }
}
