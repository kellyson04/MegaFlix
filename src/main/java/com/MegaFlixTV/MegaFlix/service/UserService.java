package com.MegaFlixTV.MegaFlix.service;

import com.MegaFlixTV.MegaFlix.controller.request.UserRequest;
import com.MegaFlixTV.MegaFlix.controller.response.UserResponse;
import com.MegaFlixTV.MegaFlix.entity.User;
import com.MegaFlixTV.MegaFlix.mapper.UserMapper;
import com.MegaFlixTV.MegaFlix.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserResponse> listarUsuarios () {
        List <User> usuarios = userRepository.findAll();

        return usuarios.stream()
                .map(user -> UserMapper.mapToResponse(user))
                .toList();
    }

    public UserResponse listarUsuarioPorId (Long id) {
        User acharUsuario = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Este usuario não existe!"));

        return UserMapper.mapToResponse(acharUsuario);
    }

    public UserResponse criarUsuario (UserRequest userRequest) {
        User salvarUsuario = userRepository.save(UserMapper.mapToEntity(userRequest));

        return UserMapper.mapToResponse(salvarUsuario);
    }

    public UserResponse alterarUsuarioCompleto (Long id,UserRequest userRequest) {
        User acharUsuario = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Esse ID não existe."));

        acharUsuario.setUser(userRequest.user());
        acharUsuario.setPassword(userRequest.password());
        acharUsuario.setEmail(userRequest.email());

        userRepository.save(acharUsuario);

        return UserMapper.mapToResponse(acharUsuario);
    }

    public void deletarUsuario (Long id) {
        User acharUsuario = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Esse ID não existe."));

        userRepository.deleteById(id);
    }
}
