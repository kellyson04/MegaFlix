package com.MegaFlixTV.MegaFlix.config.security;

import com.MegaFlixTV.MegaFlix.exception.UserNotFoundException;
import com.MegaFlixTV.MegaFlix.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByUser(username).orElseThrow(() -> new UserNotFoundException("Usuario não encontrado"));
    }
}
