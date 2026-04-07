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
                                .requestMatchers(HttpMethod.PUT,"/megaflix/users/{id}").permitAll()
                                .requestMatchers(HttpMethod.DELETE,"/megaflix/users/{id}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST,"/megaflix/users/login").permitAll()
                                .requestMatchers(HttpMethod.PUT,"/megaflix/users/{userId}/password").authenticated()
                                .requestMatchers(HttpMethod.PATCH,"/megaflix/users/{id}").authenticated()
                                .requestMatchers(HttpMethod.POST,"/megaflix/movies").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET,"/megaflix/movies").permitAll()
                                .requestMatchers(HttpMethod.GET,"/megaflix/movies/{id}").permitAll()
                                .requestMatchers(HttpMethod.PUT,"/megaflix/movies/{id}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PATCH,"/megaflix/movies/{id}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE,"/megaflix/movies/{id}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST,"/megaflix/movies/{movieId}/streaming/{streamingId}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET,"/megaflix/movies/{movieId}/streamings").permitAll()
                                .requestMatchers(HttpMethod.POST,"/megaflix/playlist/{userId}/{movieId}").authenticated()
                                .requestMatchers(HttpMethod.GET,"/megaflix/playlist").permitAll()
                                .requestMatchers(HttpMethod.GET,"/megaflix/playlist/{id}").permitAll()
                                .requestMatchers(HttpMethod.DELETE,"/megaflix/playlist/{id}").permitAll()
                                .requestMatchers(HttpMethod.PATCH,"/megaflix/playlist/user/{userId}/movie/{movieId}/watched").authenticated()
                                .requestMatchers(HttpMethod.PATCH,"/megaflix/playlist/{relationId}/favorite").authenticated()
                                .requestMatchers(HttpMethod.PATCH,"/megaflix/playlist/{relationId}/unfavorite").authenticated()
                                .requestMatchers(HttpMethod.GET,"/megaflix/playlist/favorites").permitAll()
                                .requestMatchers(HttpMethod.GET,"/megaflix/playlist/user/{userId}/favorites").permitAll()
                                .requestMatchers(HttpMethod.POST,"/megaflix/streamings").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET,"/megaflix/streamings").permitAll()
                                .requestMatchers(HttpMethod.GET,"/megaflix/streamings/{id}").permitAll()
                                .requestMatchers(HttpMethod.PUT,"/megaflix/streamings/{id}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE,"/megaflix/streamings/{id}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET,"/megaflix/streamings/{streamingid}/movies").permitAll()
                                .requestMatchers(HttpMethod.DELETE,"/megaflix/streamings/{streamingId}/movies/{movieId}").hasRole("ADMIN")

                        ).httpBasic(Customizer.withDefaults());

        return httpSecurity.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder () {
        return new BCryptPasswordEncoder();
    }


}
