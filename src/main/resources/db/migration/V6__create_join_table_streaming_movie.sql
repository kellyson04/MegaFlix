CREATE TABLE streaming_movie (
    streaming_id BIGINT NOT NULL,
    movie_id BIGINT NOT NULL,

    PRIMARY KEY (streaming_id,movie_id),

    CONSTRAINT fk_streaming_movie_streaming
    FOREIGN KEY (streaming_id) references streaming(id),
    CONSTRAINT fk_streaming_movie_movie
    FOREIGN KEY (movie_id) references movie(id)
);