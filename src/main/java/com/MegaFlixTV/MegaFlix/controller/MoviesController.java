package com.MegaFlixTV.MegaFlix.controller;

import com.MegaFlixTV.MegaFlix.controller.request.MovieRequest;
import com.MegaFlixTV.MegaFlix.controller.response.MovieResponse;
import com.MegaFlixTV.MegaFlix.service.MovieService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/megaflix/movie")
public class MoviesController {
    private final MovieService movieService;

    public MoviesController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping()
    public List<MovieResponse> listarFilmes () {
        return movieService.listarFilmes();
    }

    @GetMapping("{id}")
    public MovieResponse movieResponse (@PathVariable Long id) {
        return movieService.listarFilmeEspecifico(id);
    }

    @PostMapping()
    public MovieRequest movieRequest (@RequestBody MovieRequest movieRequest) {
        return movieService.adicionarFilme(movieRequest);
    }

    @PutMapping("{id}")
    public MovieRequest movieRequest (@PathVariable Long id, @RequestBody MovieRequest movieRequest) {
         movieService.AlterarFilmePorCompleto(id,movieRequest);

         return movieRequest;
    }

    @DeleteMapping("{id}")
    public void deletarFilme (@PathVariable Long id) {
        movieService.deletarFilme(id);
    }
}