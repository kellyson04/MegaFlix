package com.MegaFlixTV.MegaFlix.controller.response;

import com.MegaFlixTV.MegaFlix.entity.Movie;
import com.MegaFlixTV.MegaFlix.entity.User;
import lombok.Builder;

@Builder
public record UserMovieResponse(
        Long userid,
        String username,
        Movie movie,
        boolean favorite,
        boolean watched
) {
}
