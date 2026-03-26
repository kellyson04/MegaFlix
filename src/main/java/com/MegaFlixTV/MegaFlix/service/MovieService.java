package com.MegaFlixTV.MegaFlix.service;

import com.MegaFlixTV.MegaFlix.controller.request.MovieRequest;
import com.MegaFlixTV.MegaFlix.controller.response.MovieResponse;
import com.MegaFlixTV.MegaFlix.controller.response.StreamingResponse;
import com.MegaFlixTV.MegaFlix.entity.Movie;
import com.MegaFlixTV.MegaFlix.entity.Streaming;
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

    public List<MovieResponse> listarFilmes () {
        List<Movie> moviesEntity = movieRepository.findAll();

        return moviesEntity.stream()
                .map(movie -> MovieMapper.toResponse(movie))
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

    public List<MovieResponse> listarFilmesPorGenero (String genre) {


        List<MovieResponse> listarFilmesPeloGenero = movieRepository.findMovieByGenre(genre).stream()
                .map(movie -> MovieMapper.toResponse(movie))
                .toList();

        return listarFilmesPeloGenero;
    }

    public List<MovieResponse> listarFilmesPelaDuracaoMaior (double duration) {

        List<MovieResponse> listarFilmesDuracaoMaior = movieRepository.findMovieByDurationGreaterThanEqual(duration)
                .stream()
                .map(movie -> MovieMapper.toResponse(movie))
                .toList();

        return listarFilmesDuracaoMaior;
    }

    public List<MovieResponse> listarFilmesPelaDuracaoMenor (double duration) {

        List<MovieResponse> listarFilmesDuracaoMenor = movieRepository.findMovieByDurationLessThanEqual(duration)
                .stream()
                .map(movie -> MovieMapper.toResponse(movie))
                .toList();

        return listarFilmesDuracaoMenor;
    }

    public List<MovieResponse> listarFilmesPeloTitulo (String title) {
        List<Movie> acharFilme = movieRepository.findMovieByMovieContainingIgnoreCase(title);

        if (acharFilme.isEmpty()) {
            throw new MovieNotFoundException("Não possuimos este filme no catalogo!");
        }else {
            List<MovieResponse> listarPeloTitulo = movieRepository.findMovieByMovieContainingIgnoreCase(title)
                    .stream()
                    .map(movie -> MovieMapper.toResponse(movie))
                    .toList();

            return listarPeloTitulo;
        }
    }

    public MovieResponse adicionarFilmeNoStreaming (Long movieId,Long streamingId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new MovieNotFoundException("Filme não encontrado!"));
        Streaming streaming = streamingRepository.findById(streamingId).orElseThrow(() -> new StreamingNotFoundException("Streaming não encontrado"));

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
