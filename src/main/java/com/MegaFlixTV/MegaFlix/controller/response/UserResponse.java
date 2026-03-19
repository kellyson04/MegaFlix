package com.MegaFlixTV.MegaFlix.controller.response;

import lombok.Builder;

@Builder
public record UserResponse(
        Long id,
        String user,
        String password,
        String email
) {
}
