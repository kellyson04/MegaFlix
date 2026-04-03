package com.MegaFlixTV.MegaFlix.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record StreamingRequest (Long id,
                                @NotBlank
                                @Size(min = 2, max = 50, message = "O nome do streaming tem que ter entre 2 a 50 caracteres.")
                                String name){
}
