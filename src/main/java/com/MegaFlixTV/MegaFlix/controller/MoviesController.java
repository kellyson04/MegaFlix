package com.MegaFlixTV.MegaFlix.controller;

import com.MegaFlixTV.MegaFlix.controller.request.MoviePatchRequest;
import com.MegaFlixTV.MegaFlix.controller.request.MovieRequest;
import com.MegaFlixTV.MegaFlix.controller.response.MovieResponse;
import com.MegaFlixTV.MegaFlix.controller.response.StreamingResponse;
import com.MegaFlixTV.MegaFlix.service.MovieService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/megaflix/movies")
public class MoviesController {
    private final MovieService movieService;

    public MoviesController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping()
    public ResponseEntity<MovieResponse> adicionarFilme (@RequestBody @Valid MovieRequest movieRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(movieService.adicionarFilme(movieRequest));
    }


    @GetMapping()
    public ResponseEntity<List<MovieResponse>> listarFilmes (@RequestParam(required = false) String title,
                                                             @RequestParam(required = false) String genre,
                                                             @RequestParam(required = false) Double maxDuration,
                                                             @RequestParam(required = false) Double minDuration,
                                                             @RequestParam(required = false) Integer releaseYear,
                                                             @RequestParam(required = false) Double rating) {
        return ResponseEntity.ok(movieService.listarFilmes(title,genre,maxDuration,minDuration,releaseYear,rating));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieResponse> listarFilmePorId (@PathVariable Long id) {
        return ResponseEntity.ok(movieService.listarFilmeEspecifico(id));
    }


    @PutMapping("/{id}")
    public ResponseEntity<MovieResponse> alterarFilmePorCompleto (@PathVariable Long id, @RequestBody @Valid MovieRequest movieRequest) {
         return ResponseEntity.ok(movieService.alterarFilmePorCompleto(id,movieRequest));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MovieResponse> alterarFilmeParcialmente (@PathVariable Long id,@RequestBody @Valid MoviePatchRequest moviePatchRequest) {
        return ResponseEntity.ok(movieService.alterarFilmeParcialmente(id,moviePatchRequest));
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletarFilme (@PathVariable Long id) {
        movieService.deletarFilme(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/{movieId}/streaming/{streamingId}")
    public ResponseEntity<MovieResponse> adicionarVinculoComStreaming (@PathVariable Long movieId,@PathVariable Long streamingId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(movieService.adicionarFilmeNoStreaming(movieId,streamingId));
    }

    @GetMapping("/{movieId}/streamings")
    public ResponseEntity<List<StreamingResponse>> streamingsQuePossuemOFilme (@PathVariable Long movieId) {
        return ResponseEntity.ok(movieService.streamingsQueOFilmeSeEncontra(movieId));
    }

}
