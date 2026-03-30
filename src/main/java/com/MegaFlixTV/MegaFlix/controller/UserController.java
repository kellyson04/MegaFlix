package com.MegaFlixTV.MegaFlix.controller;


import com.MegaFlixTV.MegaFlix.controller.request.UserLoginRequest;
import com.MegaFlixTV.MegaFlix.controller.request.UserRequest;
import com.MegaFlixTV.MegaFlix.controller.response.UserLoginResponse;
import com.MegaFlixTV.MegaFlix.controller.response.UserResponse;
import com.MegaFlixTV.MegaFlix.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/megaflix/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    public ResponseEntity<UserResponse> criarUsuario (@RequestBody @Valid UserRequest userRequest) {
         return ResponseEntity.status(HttpStatus.CREATED).body(userService.criarUsuario(userRequest));
    }


    @GetMapping()
    public ResponseEntity<List<UserResponse>> listarUsuarios () {
        return ResponseEntity.ok(userService.listarUsuarios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> listarUsuarioPorId (@PathVariable Long id) {
        return ResponseEntity.ok(userService.listarUsuarioPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> alterarUsuarioPorCompleto (@PathVariable Long id, @RequestBody @Valid UserRequest userRequest) {
        return ResponseEntity.ok(userService.alterarUsuarioCompleto(id, userRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario (@PathVariable Long id) {
        userService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> login (@RequestBody @Valid UserLoginRequest userLoginRequest) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.logarUsuario(userLoginRequest));
    }
}