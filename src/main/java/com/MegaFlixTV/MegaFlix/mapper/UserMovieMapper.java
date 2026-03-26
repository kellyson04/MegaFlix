package com.MegaFlixTV.MegaFlix.mapper;

import com.MegaFlixTV.MegaFlix.controller.request.UserMovieRequest;
import com.MegaFlixTV.MegaFlix.controller.request.UserRequest;
import com.MegaFlixTV.MegaFlix.controller.response.UserMovieResponse;
import com.MegaFlixTV.MegaFlix.entity.UserMovie;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMovieMapper {

    public static UserMovie mapToEntity (UserMovieRequest userMovieRequest) {

        return UserMovie.builder()
                .user(userMovieRequest.user())
                .movie(userMovieRequest.movie())
                .favorite(userMovieRequest.favorite())
                .watched(userMovieRequest.watched())
                .build();
    }

    public static UserMovieResponse mapToResponse (UserMovie userMovie) {

        return UserMovieResponse.builder()
                .userid(userMovie.getId())
                .username(userMovie.getUser().getUser())
                .movie(userMovie.getMovie())
                .favorite(userMovie.isFavorite())
                .watched(userMovie.isWatched())
                .build();
    }
}
