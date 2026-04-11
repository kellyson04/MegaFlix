package com.MegaFlixTV.MegaFlix.controller.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRequest(
        @NotBlank(message = "Nome do usuario vazio")
        @Size(min = 2, max = 50, message = "O nome deve ter no minimo 2 caracteres e no maximo 50")
        String username,
        @NotBlank
        @Size(min = 8, max = 100)
        @Pattern(regexp = "^(?=.*[A-Z])(?=(?:.*\\d){2,})(?=.*[^a-zA-Z0-9]).{8,}$", message = "A senha deve ter no minimo 8 caracteres 1 letra maiuscula, 2 numeros e 1 simbolo.")
        String password,
        @NotBlank
        @Email
        String email
) {
}
