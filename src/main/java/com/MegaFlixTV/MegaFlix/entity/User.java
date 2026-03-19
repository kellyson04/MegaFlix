package com.MegaFlixTV.MegaFlix.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user", length = 100, nullable = false)
    private String user;

    @Column(name = "password", length = 100, nullable = false)
    private String password;

    @Column(unique = true, name = "email", length = 100, nullable = false)
    private String email;

}
