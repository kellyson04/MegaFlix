package com.MegaFlixTV.MegaFlix.controller.response;

public record ErrorResponse(
        int errorStatus,
        String error,
        String message
) {
}
