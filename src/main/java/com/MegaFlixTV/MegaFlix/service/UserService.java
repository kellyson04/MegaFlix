package com.MegaFlixTV.MegaFlix.service;

import com.MegaFlixTV.MegaFlix.config.security.SecurityConfig;
import com.MegaFlixTV.MegaFlix.controller.request.UserLoginRequest;
import com.MegaFlixTV.MegaFlix.controller.request.UserRequest;
import com.MegaFlixTV.MegaFlix.controller.response.UserLoginResponse;
import com.MegaFlixTV.MegaFlix.controller.response.UserResponse;
import com.MegaFlixTV.MegaFlix.entity.User;
import com.MegaFlixTV.MegaFlix.exception.InvalidCredentialsException;
import com.MegaFlixTV.MegaFlix.exception.UserNotFoundException;
import com.MegaFlixTV.MegaFlix.mapper.UserMapper;
import com.MegaFlixTV.MegaFlix.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserResponse> listarUsuarios () {
        List <User> usuarios = userRepository.findAll();

        return usuarios.stream()
                .map(user -> UserMapper.mapToResponse(user))
                .toList();
    }

    public UserResponse listarUsuarioPorId (Long id) {
        User acharUsuario = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Este usuario não existe!"));

        return UserMapper.mapToResponse(acharUsuario);
    }

    public UserResponse criarUsuario (UserRequest userRequest) {
        User usuario = UserMapper.mapToEntity(userRequest);
        usuario.setPassword(passwordEncoder.encode(userRequest.password()));

        userRepository.save(usuario);

        return UserMapper.mapToResponse(usuario);
    }

    public UserResponse alterarUsuarioCompleto (Long id,UserRequest userRequest) {
        User acharUsuario = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Alteração impossivel devido a ID inexistente"));

        acharUsuario.setUser(userRequest.user());
        acharUsuario.setPassword(userRequest.password());
        acharUsuario.setEmail(userRequest.email());

        userRepository.save(acharUsuario);

        return UserMapper.mapToResponse(acharUsuario);
    }

    public void deletarUsuario (Long id) {
        User acharUsuario = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Não é possivel Deletar um usuario inexistente"));

        userRepository.deleteById(id);
    }

    public UserLoginResponse logarUsuario (UserLoginRequest userLoginRequest) {
        User user = userRepository.findUserByUser(userLoginRequest.user()).orElseThrow(() -> new InvalidCredentialsException("Dados invalidos"));

        if (!passwordEncoder.matches(userLoginRequest.password(),user.getPassword())) {
            throw new InvalidCredentialsException("Dados invalidos");
        }

        return new UserLoginResponse(userLoginRequest.user());
    }


}
