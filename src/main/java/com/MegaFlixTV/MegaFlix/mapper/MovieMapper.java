package com.MegaFlixTV.MegaFlix.mapper;

import com.MegaFlixTV.MegaFlix.controller.request.MovieRequest;
import com.MegaFlixTV.MegaFlix.controller.response.MovieResponse;
import com.MegaFlixTV.MegaFlix.controller.response.StreamingResponse;
import com.MegaFlixTV.MegaFlix.entity.Movie;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MovieMapper {

    public static MovieResponse toResponse (Movie movie) {
        return MovieResponse.builder()
                .id(movie.getId())
                .movie(movie.getMovie())
                .genre(movie.getGenre())
                .duration(movie.getDuration())
                .build();
    }

    public static Movie toEntity (MovieRequest movieRequest) {
        return Movie.builder()
                .movie(movieRequest.movie())
                .genre(movieRequest.genre())
                .duration(movieRequest.duration())
                .build();
    }

}
