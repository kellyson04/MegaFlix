package com.MegaFlixTV.MegaFlix.controller.request;

public record UserRequest(
        Long id,
        String user,
        String password,
        String email
) {
}
