package com.MegaFlixTV.MegaFlix.controller.request;

public record MovieRequest(
        Long id,
        String movie,
        String genre,
        double duration
) {
}
