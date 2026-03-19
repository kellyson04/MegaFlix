CREATE TABLE users_movies (
    id BIGINT NOT NULL AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    movie_id BIGINT NOT NULL,
    favorite BOOLEAN NOT NULL DEFAULT FALSE,
    watched BOOLEAN NOT NULL DEFAULT FALSE,
    PRIMARY KEY (id),
    CONSTRAINT fk_users_movies_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_users_movies_movie FOREIGN KEY (movie_id) REFERENCES movie(id),
    CONSTRAINT uq_users_movies_ UNIQUE (user_id,movie_id)
);