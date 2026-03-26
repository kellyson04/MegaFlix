package com.MegaFlixTV.MegaFlix.controller.response;

import com.MegaFlixTV.MegaFlix.entity.Movie;
import com.MegaFlixTV.MegaFlix.entity.User;
import lombok.Builder;

@Builder
public record UserMovieResponse(
        Long relationId,
        String username,
        Long movieid,
        String movieTitle,
        String movieGenre,
        Double movieDuration,
        Integer movieReleaseYear,
        Double movieRating,
        boolean favorite,
        boolean watched
) {
}
