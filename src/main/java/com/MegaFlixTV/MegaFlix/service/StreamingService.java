package com.MegaFlixTV.MegaFlix.service;

import com.MegaFlixTV.MegaFlix.controller.request.StreamingRequest;
import com.MegaFlixTV.MegaFlix.controller.response.StreamingResponse;
import com.MegaFlixTV.MegaFlix.entity.Streaming;
import com.MegaFlixTV.MegaFlix.mapper.StreamingMapper;
import com.MegaFlixTV.MegaFlix.repository.StreamingRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class StreamingService {

    private final StreamingRepository streamingRepository;

    public StreamingService(StreamingRepository streamingRepository) {
        this.streamingRepository = streamingRepository;
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
}
