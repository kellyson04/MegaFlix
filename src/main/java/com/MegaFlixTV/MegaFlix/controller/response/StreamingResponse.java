package com.MegaFlixTV.MegaFlix.controller.response;

import lombok.Builder;

@Builder
public record StreamingResponse (Long id, String name){
}
