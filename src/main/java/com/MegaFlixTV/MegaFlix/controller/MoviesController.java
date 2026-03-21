package com.MegaFlixTV.MegaFlix.controller;

import com.MegaFlixTV.MegaFlix.controller.request.MovieRequest;
import com.MegaFlixTV.MegaFlix.controller.response.MovieResponse;
import com.MegaFlixTV.MegaFlix.service.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/megaflix/movie")
public class MoviesController {
    private final MovieService movieService;

    public MoviesController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping()
    public ResponseEntity<MovieResponse> adicionarFilme (@RequestBody MovieRequest movieRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(movieService.adicionarFilme(movieRequest));
    }


    @GetMapping()
    public ResponseEntity<List<MovieResponse>> listarFilmes () {
        return ResponseEntity.ok(movieService.listarFilmes());
    }


    @GetMapping("{id}")
    public ResponseEntity<MovieResponse> listarFilmePorId (@PathVariable Long id) {
        return ResponseEntity.ok(movieService.listarFilmeEspecifico(id));
    }


    @PutMapping("{id}")
    public ResponseEntity<MovieResponse> AlterarFilmePorCompleto (@PathVariable Long id, @RequestBody MovieRequest movieRequest) {
         return ResponseEntity.ok(movieService.alterarFilmePorCompleto(id,movieRequest));
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletarFilme (@PathVariable Long id) {
        movieService.deletarFilme(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}