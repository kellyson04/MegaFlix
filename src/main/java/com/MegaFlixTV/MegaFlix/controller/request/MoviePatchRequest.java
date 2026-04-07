package com.MegaFlixTV.MegaFlix.controller.request;

import jakarta.validation.constraints.*;

public record MoviePatchRequest(

        String movie,
        String genre,
        @DecimalMin(value = "60.0", inclusive = true,message = "O tamanho do filme deve ter no minimo 1 hora de duração.")
        Double duration,
        @Min(1910)
        @Max(2026)
        Integer releaseYear,
        @DecimalMin(value = "0.0",inclusive = true, message = "A nota do filme não pode ser negativa")
        @DecimalMax(value = "10.0",inclusive = true,message = "A nota do filme não pode ultrapassar 10")
        Double rating
) {
}
