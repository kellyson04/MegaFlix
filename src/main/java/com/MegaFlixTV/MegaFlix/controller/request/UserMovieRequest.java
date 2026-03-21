package com.MegaFlixTV.MegaFlix.controller.request;

import com.MegaFlixTV.MegaFlix.entity.Movie;
import com.MegaFlixTV.MegaFlix.entity.User;

public record UserMovieRequest(
        User user,
        Movie movie,
        boolean favorite,
        boolean watched
) {
}
