package com.MegaFlixTV.MegaFlix.service;

import com.MegaFlixTV.MegaFlix.config.security.SecurityConfig;
import com.MegaFlixTV.MegaFlix.controller.request.ChangePasswordRequest;
import com.MegaFlixTV.MegaFlix.controller.request.ChangeUserDataRequest;
import com.MegaFlixTV.MegaFlix.controller.request.UserLoginRequest;
import com.MegaFlixTV.MegaFlix.controller.request.UserRequest;
import com.MegaFlixTV.MegaFlix.controller.response.UserLoginResponse;
import com.MegaFlixTV.MegaFlix.controller.response.UserResponse;
import com.MegaFlixTV.MegaFlix.entity.User;
import com.MegaFlixTV.MegaFlix.exception.BusinessRuleException;
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

        if (userRepository.existsUserByUser(userRequest.user())) {
            throw new BusinessRuleException("Este Usuario não está disponivel");
        }

        if (userRepository.existsUserByEmail(userRequest.email())) {
            throw new BusinessRuleException("Este Email não está disponivel");
        }

        return UserMapper.mapToResponse(userRepository.save(usuario));
    }

    public UserResponse alterarUsuarioCompleto (Long id,UserRequest userRequest) {
        User acharUsuario = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Alteração impossivel devido a ID inexistente"));

        if (userRepository.existsUserByUser(userRequest.user())) {
            throw new BusinessRuleException("Este Usuario não está disponivel");
        }

        if (userRepository.existsUserByEmail(userRequest.email())) {
            throw new BusinessRuleException("Este Email não está disponivel");
        }

        if (userRequest.email().equals(acharUsuario.getEmail())) {
            throw new BusinessRuleException("Mude para um novo Email");
        }

        if (passwordEncoder.matches(userRequest.password(),acharUsuario.getPassword())) {
            throw new BusinessRuleException("Mude para uma nova Senha");
        }

        acharUsuario.setUser(userRequest.user());
        acharUsuario.setPassword(passwordEncoder.encode(userRequest.password()));
        acharUsuario.setEmail(userRequest.email());

        userRepository.save(acharUsuario);

        return UserMapper.mapToResponse(acharUsuario);
    }

    public UserResponse alterarUsuarioParcialmente (Long id, ChangeUserDataRequest changeUserDataRequest) {
        if (changeUserDataRequest.fieldToChange() == null  || changeUserDataRequest.fieldToChange().isBlank() ) {
            throw new BusinessRuleException("Porfavor insira o campo que deseja alterar");
        }

        if (!changeUserDataRequest.fieldToChange().equalsIgnoreCase("EMAIL") && !changeUserDataRequest.fieldToChange().equalsIgnoreCase("SENHA")) {
            throw new BusinessRuleException("Esta alteração não existe.");
        }

        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Usuario não encontrado."));

        if (!passwordEncoder.matches(changeUserDataRequest.currentPassword(),user.getPassword())) {
            throw new InvalidCredentialsException("Dados invalidos.");
        }


        if (changeUserDataRequest.fieldToChange().equalsIgnoreCase("EMAIL")) {

            if (changeUserDataRequest.email() == null) {
                throw new BusinessRuleException("Voce não esta preenchendo o campo do novo email");
            }

            if (changeUserDataRequest.email().equals(user.getEmail())) {
                throw new BusinessRuleException("Voce precisa atualizar o email");
            }

            if (userRepository.existsUserByEmail(changeUserDataRequest.email())) {
                throw new BusinessRuleException("Este email não esta disponivel");
            }

            user.setEmail(changeUserDataRequest.email());
            userRepository.save(user);

        }else if (changeUserDataRequest.fieldToChange().equalsIgnoreCase("SENHA")) {

            if (changeUserDataRequest.newPassword() == null) {
                throw new BusinessRuleException("Voce não esta preenchendo o campo da nova senha");
            }

            if (passwordEncoder.matches(changeUserDataRequest.newPassword(),user.getPassword())) {
                throw new BusinessRuleException("Voce precisa atualizar para uma nova senha.");
            }

            user.setPassword(passwordEncoder.encode(changeUserDataRequest.newPassword()));
            userRepository.save(user);
        }

        return UserMapper.mapToResponse(user);
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

    public void trocarSenha (ChangePasswordRequest changePasswordRequest) {
        User user = userRepository.findUserByUser(changePasswordRequest.user()).orElseThrow(() -> new InvalidCredentialsException("Dados invalidos."));


        if (!passwordEncoder.matches(changePasswordRequest.currentPassword(),user.getPassword())) {
            throw new InvalidCredentialsException("Dados invalidos");
        }

        if (changePasswordRequest.currentPassword().equals(changePasswordRequest.newPassword())) {
            throw new BusinessRuleException("A nova senha tem que ser diferente da atual.");
        }

        user.setPassword(passwordEncoder.encode(changePasswordRequest.newPassword()));
        userRepository.save(user);
    }


}
