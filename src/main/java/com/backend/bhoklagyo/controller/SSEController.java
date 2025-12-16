package com.backend.bhoklagyo.controller;
import com.backend.bhoklagyo.service.SSEEmitterService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/subscribe")
public class SSEController {

    private final SSEEmitterService sseService;

    @GetMapping("/{userId}")
    public ResponseEntity<SseEmitter> subscribeToUser(@PathVariable UUID userId) {
        return ResponseEntity.ok(sseService.subscribeByUser(userId));
    }
}

