package com.MegaFlixTV.MegaFlix.service;

import com.MegaFlixTV.MegaFlix.controller.request.userRequests.ChangePasswordRequest;
import com.MegaFlixTV.MegaFlix.controller.request.userRequests.ChangeUserDataRequest;
import com.MegaFlixTV.MegaFlix.controller.request.userRequests.UserLoginRequest;
import com.MegaFlixTV.MegaFlix.controller.request.userRequests.CreateUserRequest;
import com.MegaFlixTV.MegaFlix.controller.response.UserLoginResponse;
import com.MegaFlixTV.MegaFlix.controller.response.UserResponse;
import com.MegaFlixTV.MegaFlix.entity.User;
import com.MegaFlixTV.MegaFlix.exception.BusinessRuleException;
import com.MegaFlixTV.MegaFlix.exception.InvalidCredentialsException;
import com.MegaFlixTV.MegaFlix.exception.UserNotFoundException;
import com.MegaFlixTV.MegaFlix.mapper.UserMapper;
import com.MegaFlixTV.MegaFlix.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository,PasswordEncoder passwordEncoder,AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
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

    public UserResponse criarUsuario (CreateUserRequest createUserRequest) {
        User usuario = UserMapper.mapToEntity(createUserRequest);
        usuario.setPassword(passwordEncoder.encode(createUserRequest.password()));

        if (userRepository.existsUserByUsername(createUserRequest.username())) {
            throw new BusinessRuleException("Este Usuario não está disponivel");
        }

        if (userRepository.existsUserByEmail(createUserRequest.email())) {
            throw new BusinessRuleException("Este Email não está disponivel");
        }

        return UserMapper.mapToResponse(userRepository.save(usuario));
    }

    public UserResponse alterarUsuarioCompleto (Long id, CreateUserRequest createUserRequest) {
        User acharUsuario = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Alteração impossivel devido a ID inexistente"));

        if (userRepository.existsUserByUsername(createUserRequest.username())) {
            throw new BusinessRuleException("Este Usuario não está disponivel");
        }

        if (userRepository.existsUserByEmail(createUserRequest.email())) {
            throw new BusinessRuleException("Este Email não está disponivel");
        }

        if (createUserRequest.email().equals(acharUsuario.getEmail())) {
            throw new BusinessRuleException("Mude para um novo Email");
        }

        if (passwordEncoder.matches(createUserRequest.password(),acharUsuario.getPassword())) {
            throw new BusinessRuleException("Mude para uma nova Senha");
        }

        acharUsuario.setUsername(createUserRequest.username());
        acharUsuario.setPassword(passwordEncoder.encode(createUserRequest.password()));
        acharUsuario.setEmail(createUserRequest.email());

        userRepository.save(acharUsuario);

        return UserMapper.mapToResponse(acharUsuario);
    }

    public void alterarUsuarioParcialmente (Long id, ChangeUserDataRequest changeUserDataRequest, Authentication authentication) {

        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Usuario não encontrado."));

        if (!authentication.getName().equals(user.getUsername())) {
            throw new InvalidCredentialsException("Acesso invalido");
        }


        if (changeUserDataRequest.username() != null && !changeUserDataRequest.username().isBlank()) {
            user.setUsername(changeUserDataRequest.username());
        }
        if (changeUserDataRequest.email() != null && !changeUserDataRequest.email().isBlank()) {
            user.setEmail(changeUserDataRequest.email());
        }


        userRepository.save(user);


    }

    public void deletarUsuario (Long id) {
        User acharUsuario = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Não é possivel Deletar um usuario inexistente"));

        userRepository.deleteById(id);
    }

    public UserLoginResponse logarUsuario (UserLoginRequest userLoginRequest, HttpServletRequest request, HttpServletResponse response) {
        UsernamePasswordAuthenticationToken userAndPass = new UsernamePasswordAuthenticationToken(userLoginRequest.username(),userLoginRequest.password());
        Authentication authentication = authenticationManager.authenticate(userAndPass);

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);

        HttpSessionSecurityContextRepository securityContextSaver = new HttpSessionSecurityContextRepository();

        securityContextSaver.saveContext(context,request,response);

        return new UserLoginResponse(userLoginRequest.username());
    }

    public void trocarSenha (ChangePasswordRequest changePasswordRequest) {
        User user = userRepository.findUserByUsername(changePasswordRequest.user()).orElseThrow(() -> new InvalidCredentialsException("Dados invalidos."));


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
