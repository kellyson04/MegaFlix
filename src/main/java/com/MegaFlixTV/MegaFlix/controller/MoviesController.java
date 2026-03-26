package com.MegaFlixTV.MegaFlix.controller;

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
@RequestMapping("/megaflix/movie")
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
    public ResponseEntity<List<MovieResponse>> listarFilmes () {
        return ResponseEntity.ok(movieService.listarFilmes());
    }


    @GetMapping("{id}")
    public ResponseEntity<MovieResponse> listarFilmePorId (@PathVariable Long id) {
        return ResponseEntity.ok(movieService.listarFilmeEspecifico(id));
    }


    @PutMapping("{id}")
    public ResponseEntity<MovieResponse> AlterarFilmePorCompleto (@PathVariable Long id, @RequestBody @Valid MovieRequest movieRequest) {
         return ResponseEntity.ok(movieService.alterarFilmePorCompleto(id,movieRequest));
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletarFilme (@PathVariable Long id) {
        movieService.deletarFilme(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/filtrar/por-genero")
    public ResponseEntity <List<MovieResponse>> ListarPorGenero (@RequestParam String genero) {

        return ResponseEntity.ok(movieService.listarFilmesPorGenero(genero));
    }

    @GetMapping("/filtrar/por-duracao-maior")
    public ResponseEntity<List<MovieResponse>> listarPorDuracaoMaiorOuIgual (@RequestParam double duracaoMaior) {
        return ResponseEntity.ok(movieService.listarFilmesPelaDuracaoMaior(duracaoMaior));
    }

    @GetMapping("/filtrar/por-duracao-menor")
    public ResponseEntity<List<MovieResponse>> listarPorDuracaoMenorOuIgual (@RequestParam double duracaoMenor) {
        return ResponseEntity.ok(movieService.listarFilmesPelaDuracaoMenor(duracaoMenor));
    }

    @GetMapping("/filtrar/por-titulo")
    public ResponseEntity<List<MovieResponse>> listarPorTitulo (@RequestParam String titulo) {

        return ResponseEntity.ok(movieService.listarFilmesPeloTitulo(titulo));
    }

    @PostMapping("/streaming/{movieId}/{streamingId}")
    public ResponseEntity<MovieResponse> adicionarVinculoComStreaming (@PathVariable Long movieId,@PathVariable Long streamingId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(movieService.adicionarFilmeNoStreaming(movieId,streamingId));
    }

    @GetMapping("/{movieId}/streamings")
    public ResponseEntity<List<StreamingResponse>> StreamingsQuePossuemOFilme (@PathVariable Long movieId) {
        return ResponseEntity.status(HttpStatus.FOUND).body(movieService.StreamingsQueOFilmeSeEncontra(movieId));
    }

}
