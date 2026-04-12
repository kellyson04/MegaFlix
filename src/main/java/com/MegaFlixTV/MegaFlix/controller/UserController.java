package com.MegaFlixTV.MegaFlix.controller;


import com.MegaFlixTV.MegaFlix.controller.request.userRequests.ChangePasswordRequest;
import com.MegaFlixTV.MegaFlix.controller.request.userRequests.ChangeUserDataRequest;
import com.MegaFlixTV.MegaFlix.controller.request.userRequests.UserLoginRequest;
import com.MegaFlixTV.MegaFlix.controller.request.userRequests.CreateUserRequest;
import com.MegaFlixTV.MegaFlix.controller.response.UserLoginResponse;
import com.MegaFlixTV.MegaFlix.controller.response.UserResponse;
import com.MegaFlixTV.MegaFlix.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
    public ResponseEntity<UserResponse> criarUsuario (@RequestBody @Valid CreateUserRequest createUserRequest) {
         return ResponseEntity.status(HttpStatus.CREATED).body(userService.criarUsuario(createUserRequest));
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
    public ResponseEntity<UserResponse> alterarUsuarioPorCompleto (@PathVariable Long id, @RequestBody @Valid CreateUserRequest createUserRequest) {
        return ResponseEntity.ok(userService.alterarUsuarioCompleto(id, createUserRequest));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> alterarUsuarioParcialmente (@PathVariable Long id, @RequestBody @Valid ChangeUserDataRequest changeUserDataRequest, Authentication authentication) {
        userService.alterarUsuarioParcialmente(id, changeUserDataRequest,authentication);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario (@PathVariable Long id) {
        userService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> login (@RequestBody @Valid UserLoginRequest userLoginRequest, HttpServletRequest request, HttpServletResponse response) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.logarUsuario(userLoginRequest, request, response));
    }

    @PutMapping("/{userId}/password")
    public ResponseEntity<Void> trocarSenha (@PathVariable Long userId,@RequestBody @Valid ChangePasswordRequest changePasswordRequest) {
        userService.trocarSenha(changePasswordRequest);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}