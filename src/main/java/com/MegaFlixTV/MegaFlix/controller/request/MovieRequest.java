package com.MegaFlixTV.MegaFlix.controller.request;

import jakarta.validation.constraints.*;

public record MovieRequest(
        Long id,
        @NotBlank(message = "O nome do filme não pode estar vazio")
        String movie,
        @NotBlank(message = "O genero não pode estar vazio")
        String genre,
        @NotNull(message = "A duração do filme não pode estar vazia")
        @DecimalMin(value = "60.0", inclusive = true,message = "O tamanho do filme deve ter no minimo 1 hora de duração.")
        Double duration
) {
}
