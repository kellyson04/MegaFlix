package com.MegaFlixTV.MegaFlix.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain (HttpSecurity httpSecurity)   {
        httpSecurity
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers(HttpMethod.POST,"/megaflix/users").permitAll()
                                .requestMatchers(HttpMethod.GET,"/megaflix/users").permitAll()
                                .requestMatchers(HttpMethod.GET,"/megaflix/users/{id}").permitAll()
                        //esse request put abaixo tipo tava has role admin e tava impedindo de eu trocar a senha com 401 no postman, n sei pq isso, investigar amanhã
                                .requestMatchers(HttpMethod.PUT,"/megaflix/users/{id}").permitAll()
                                .requestMatchers(HttpMethod.DELETE,"/megaflix/users/{id}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST,"/megaflix/users/login").permitAll()
                                .requestMatchers(HttpMethod.PUT,"/megaflix/users/change-password").permitAll()
                                .requestMatchers(HttpMethod.POST,"/megaflix/movie").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET,"/megaflix/movie").permitAll()
                                .requestMatchers(HttpMethod.GET,"/megaflix/movie/{id}").permitAll()
                                .requestMatchers(HttpMethod.PUT,"/megaflix/movie/{id}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE,"/megaflix/movie/{id}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET,"/megaflix/movie/filtrar/por-genero").permitAll()
                                .requestMatchers(HttpMethod.GET,"/megaflix/movie/filtrar/por-duracao-maior").permitAll()
                                .requestMatchers(HttpMethod.GET,"/megaflix/movie/filtrar/por-duracao-menor").permitAll()
                                .requestMatchers(HttpMethod.GET,"/megaflix/movie/filtrar/por-titulo").permitAll()
                                .requestMatchers(HttpMethod.POST,"/megaflix/movie/streaming/{movieId}/{streamingId}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET,"/megaflix/movie/{movieId}/streamings").authenticated()
                                .requestMatchers(HttpMethod.POST,"/megaflix/playlist/{userId}/{movieId}").permitAll()
                                .requestMatchers(HttpMethod.GET,"/megaflix/playlist").permitAll()
                                .requestMatchers(HttpMethod.GET,"/megaflix/playlist/{id}").permitAll()
                                .requestMatchers(HttpMethod.DELETE,"/megaflix/playlist/{id}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST,"/megaflix/playlist/watch/user/{userId}/movie/{movieId}").authenticated()
                                .requestMatchers(HttpMethod.POST,"/megaflix/playlist/favorite/{relationId}").permitAll()
                                .requestMatchers(HttpMethod.POST,"/megaflix/playlist/unfavorite/{relationId}").permitAll()
                                .requestMatchers(HttpMethod.GET,"/megaflix/playlist/favorites").permitAll()
                                .requestMatchers(HttpMethod.GET,"/megaflix/playlist/user/{userId}/favorites").authenticated()
                                .requestMatchers(HttpMethod.POST,"/megaflix/streaming").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET,"/megaflix/streaming").permitAll()
                                .requestMatchers(HttpMethod.GET,"/megaflix/streaming/{id}").permitAll()
                                .requestMatchers(HttpMethod.PUT,"/megaflix/streaming/{id}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE,"/megaflix/streaming/{id}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET,"/megaflix/streaming/filmes/{id}").permitAll()
                                .requestMatchers(HttpMethod.DELETE,"/megaflix/streaming/{streamingId}/movie/{movieId}").hasRole("ADMIN")

                        ).httpBasic(Customizer.withDefaults());

        return httpSecurity.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder () {
        return new BCryptPasswordEncoder();
    }
}
