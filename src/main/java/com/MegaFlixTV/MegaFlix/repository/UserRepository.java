package com.MegaFlixTV.MegaFlix.repository;

import com.MegaFlixTV.MegaFlix.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findUserByUsername(String user);

    boolean existsUserByUsername(String user);
    boolean existsUserByEmail(String email);
}
