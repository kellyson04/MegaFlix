package com.MegaFlixTV.MegaFlix.controller.response;

import lombok.Builder;

@Builder
public record MovieResponse(
        Long id,
        String movie,
        String genre,
        Double duration,
        Integer releaseYear,
        Double rating
) {
}
