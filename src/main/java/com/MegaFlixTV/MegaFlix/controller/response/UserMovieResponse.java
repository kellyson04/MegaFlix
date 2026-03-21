package com.MegaFlixTV.MegaFlix.controller.response;

import com.MegaFlixTV.MegaFlix.entity.Movie;
import com.MegaFlixTV.MegaFlix.entity.User;
import lombok.Builder;

@Builder
public record UserMovieResponse(
        User user,
        Movie movie,
        boolean favorite,
        boolean watched
) {
}
