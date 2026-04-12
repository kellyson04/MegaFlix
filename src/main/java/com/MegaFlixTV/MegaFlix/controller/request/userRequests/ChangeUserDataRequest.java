package com.MegaFlixTV.MegaFlix.controller.request.userRequests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ChangeUserDataRequest(
        String username,
        @Email
        String email
) {
}
