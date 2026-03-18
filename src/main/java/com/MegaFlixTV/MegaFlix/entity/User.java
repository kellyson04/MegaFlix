package com.MegaFlixTV.MegaFlix.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_users")
@Data
@NoArgsConstructor
@AllArgsConstructor
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
