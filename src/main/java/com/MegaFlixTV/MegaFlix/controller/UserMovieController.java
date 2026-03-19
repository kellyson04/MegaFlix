package com.MegaFlixTV.MegaFlix.controller;

import com.MegaFlixTV.MegaFlix.entity.Movie;
import com.MegaFlixTV.MegaFlix.entity.User;
import com.MegaFlixTV.MegaFlix.entity.UserMovie;
import com.MegaFlixTV.MegaFlix.service.UserMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/megaflix/playlist")
public class UserMovieController {
    private final UserMovieService userMovieService;

    public UserMovieController(UserMovieService userMovieService) {
        this.userMovieService = userMovieService;
    }

    @PostMapping("{userId}/{movieId}")
    public UserMovie vincularUsuarioEFilme (@PathVariable Long userId, @PathVariable Long movieId) {
        return userMovieService.adicionarFilmeAoUsuario(userId,movieId);
    }

    @GetMapping()
    public List<UserMovie> listarFilmesdeUsuarios () {
       return userMovieService.listarFilmesdeUsuarios();
    }

}
