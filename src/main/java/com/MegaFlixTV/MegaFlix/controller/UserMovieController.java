package com.MegaFlixTV.MegaFlix.controller;

import com.MegaFlixTV.MegaFlix.controller.response.UserMovieResponse;
import com.MegaFlixTV.MegaFlix.entity.Movie;
import com.MegaFlixTV.MegaFlix.entity.User;
import com.MegaFlixTV.MegaFlix.entity.UserMovie;
import com.MegaFlixTV.MegaFlix.service.UserMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<UserMovieResponse> vincularUsuarioEFilme (@PathVariable Long userId, @PathVariable Long movieId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userMovieService.adicionarFilmeAoUsuario(userId,movieId));
    }

    @GetMapping()
    public ResponseEntity<List<UserMovieResponse>> listarFilmesdeUsuarios () {
       return ResponseEntity.ok(userMovieService.listarFilmesdeUsuarios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserMovieResponse> listarRelacaoEspecifica (@PathVariable Long id) {

        return ResponseEntity.ok(userMovieService.listarRelacaoEspecifica(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarRelacao (@PathVariable Long id) {
        userMovieService.deletarRelacao(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @PostMapping("/watch/user/{userId}/movie/{movieId}/relation/{relationId}")
    public ResponseEntity<UserMovieResponse> assistirFilme (@PathVariable Long userId,@PathVariable Long movieId,@PathVariable Long relationId) {

        return ResponseEntity.ok(userMovieService.assistirFilme(userId,movieId,relationId));
    }

    @PostMapping("/favorite/{relacaoId}")
    public ResponseEntity<Void> adicionarFavorito (@PathVariable Long relacaoId) {
        userMovieService.adicionarFavorito(relacaoId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
