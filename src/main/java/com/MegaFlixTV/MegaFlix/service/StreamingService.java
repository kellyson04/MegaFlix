package com.MegaFlixTV.MegaFlix.service;

import com.MegaFlixTV.MegaFlix.controller.request.StreamingRequest;
import com.MegaFlixTV.MegaFlix.controller.response.MovieResponse;
import com.MegaFlixTV.MegaFlix.controller.response.StreamingResponse;
import com.MegaFlixTV.MegaFlix.entity.Movie;
import com.MegaFlixTV.MegaFlix.entity.Streaming;
import com.MegaFlixTV.MegaFlix.exception.MovieNotFoundException;
import com.MegaFlixTV.MegaFlix.exception.RelationNotFoundException;
import com.MegaFlixTV.MegaFlix.exception.StreamingNotFoundException;
import com.MegaFlixTV.MegaFlix.mapper.MovieMapper;
import com.MegaFlixTV.MegaFlix.mapper.StreamingMapper;
import com.MegaFlixTV.MegaFlix.repository.MovieRepository;
import com.MegaFlixTV.MegaFlix.repository.StreamingRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class StreamingService {

    private final StreamingRepository streamingRepository;
    private final MovieRepository movieRepository;

    public StreamingService(StreamingRepository streamingRepository, MovieRepository movieRepository) {
        this.streamingRepository = streamingRepository;
        this.movieRepository = movieRepository;
    }

    public List<StreamingResponse> listarStreamings () {
        List<Streaming> entityList = streamingRepository.findAll();

      return entityList.stream()
                .map(streaming -> StreamingMapper.toResponse(streaming))
                .toList();
    }


    public StreamingResponse salvarStreaming (StreamingRequest streamingRequest) {
        Streaming streaming = streamingRepository.save(StreamingMapper.toEntity(streamingRequest));

        return StreamingMapper.toResponse(streaming);
    }


    public StreamingResponse listaStreamingPorId (Long id) {
       Streaming streaming = streamingRepository.findById(id).orElseThrow(() -> new RuntimeException("Não existe streaming com este ID."));

       return StreamingMapper.toResponse(streaming);
    }

    public StreamingResponse alterarStreamingPorCompleto (Long id, StreamingRequest streamingRequest) {
        Streaming acharStreaming = streamingRepository.findById(id).orElseThrow(() -> new RuntimeException("Esse Streaming não existe."));

        acharStreaming.setName(streamingRequest.name());

        streamingRepository.save(acharStreaming);

        return StreamingMapper.toResponse(acharStreaming);
    }

    public void deletarStreaming (Long id) {
        streamingRepository.findById(id).orElseThrow(() -> new RuntimeException("Este streaming não existe."));

        streamingRepository.deleteById(id);
    }

    public List<MovieResponse> filmesNoStreaming (Long streamingid) {
        Streaming streamings = streamingRepository.findStreamingById(streamingid);

        List<MovieResponse> filmes = streamings.getMovie()
                .stream()
                .map(filme -> MovieMapper.toResponse(filme))
                .toList();

        return filmes;
    }

    public void removerFilmeDoStreaming (Long streamingId,Long movieId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new MovieNotFoundException("Filme não encontrado"));
        Streaming streaming = streamingRepository.findById(streamingId).orElseThrow(() -> new StreamingNotFoundException("Streaming não encontrado"));

        if (streaming.getMovie().contains(movie)) {
            streaming.getMovie().remove(movie);
            streamingRepository.save(streaming);
        }else {
            throw new RelationNotFoundException("Esse streaming não possui este filme");
        }

    }
}
