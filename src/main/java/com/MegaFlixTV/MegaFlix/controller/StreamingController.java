package com.MegaFlixTV.MegaFlix.controller;

import com.MegaFlixTV.MegaFlix.controller.request.StreamingRequest;
import com.MegaFlixTV.MegaFlix.controller.response.MovieResponse;
import com.MegaFlixTV.MegaFlix.controller.response.StreamingResponse;
import com.MegaFlixTV.MegaFlix.service.StreamingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/megaflix/streaming")
public class StreamingController {
    private final StreamingService streamingService;

    public StreamingController(StreamingService streamingService) {
        this.streamingService = streamingService;
    }

    @PostMapping()
    public ResponseEntity<StreamingResponse> adicionarStreaming (@RequestBody @Valid StreamingRequest streamingRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(streamingService.salvarStreaming(streamingRequest));
    }

    @GetMapping()
    public ResponseEntity<List<StreamingResponse>> listarStreamings () {
        return ResponseEntity.ok(streamingService.listarStreamings());
    }


    @GetMapping("/{id}")
    public ResponseEntity<StreamingResponse> listarStreamingPorId (@PathVariable Long id) {
        return ResponseEntity.ok(streamingService.listaStreamingPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StreamingResponse> alterarStreamingPorCompleto (@PathVariable Long id, @RequestBody @Valid StreamingRequest streamingRequest) {
        return ResponseEntity.ok(streamingService.alterarStreamingPorCompleto(id,streamingRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStreaming (@PathVariable Long id) {
        streamingService.deletarStreaming(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/filmes/{id}")
    public ResponseEntity<List<MovieResponse>> listarFilmesDisponiveisNoStreaming (@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.FOUND).body(streamingService.filmesNoStreaming(id));
    }

    @DeleteMapping("/{streamingId}/movie/{movieId}")
    public ResponseEntity<Void> removerFilmeDoStreaming (@PathVariable Long streamingId,@PathVariable Long movieId) {
        streamingService.removerFilmeDoStreaming(streamingId,movieId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
