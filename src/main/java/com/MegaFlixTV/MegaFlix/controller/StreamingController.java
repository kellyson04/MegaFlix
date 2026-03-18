package com.MegaFlixTV.MegaFlix.controller;

import com.MegaFlixTV.MegaFlix.controller.request.StreamingRequest;
import com.MegaFlixTV.MegaFlix.controller.response.StreamingResponse;
import com.MegaFlixTV.MegaFlix.service.StreamingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/megaflix/streaming")
public class StreamingController {
    private final StreamingService streamingService;

    public StreamingController(StreamingService streamingService) {
        this.streamingService = streamingService;
    }

    @GetMapping()
    public ResponseEntity<List<StreamingResponse>> allStreamings () {
        return ResponseEntity.ok(streamingService.streamings());
    }

    @PostMapping()
    public ResponseEntity<StreamingResponse> saveStreaming (@RequestBody StreamingRequest streamingRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(streamingService.saveStreaming(streamingRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StreamingResponse> oneStreaming (@PathVariable Long id) {
        return ResponseEntity.ok(streamingService.streaming(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStreaming (@PathVariable Long id) {
        streamingService.deleteStreaming(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
