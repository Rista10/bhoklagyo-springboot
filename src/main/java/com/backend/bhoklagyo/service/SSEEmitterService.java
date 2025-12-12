package com.backend.bhoklagyo.service;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import java.util.UUID;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.lang.Exception;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import com.backend.bhoklagyo.model.Order;
import com.backend.bhoklagyo.repository.OrderRepository;
import com.backend.bhoklagyo.enums.OrderStatus;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SSEEmitterService {

    private final Map<UUID, List<SseEmitter>> emittersByUser = new ConcurrentHashMap<>();

    private final OrderRepository orderRepository;

    // Subscribe user to their active orders
    public SseEmitter subscribeByUser(UUID userId) {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        emittersByUser.computeIfAbsent(userId, id -> new CopyOnWriteArrayList<>()).add(emitter);

        emitter.onCompletion(() -> removeEmitter(userId, emitter));
        emitter.onTimeout(() -> removeEmitter(userId, emitter));
        emitter.onError((e) -> removeEmitter(userId, emitter));

        return emitter;
    }

    private void removeEmitter(UUID userId, SseEmitter emitter) {
        emittersByUser.getOrDefault(userId, Collections.emptyList()).remove(emitter);
    }


    public void sendUpdate(Order order) {
        UUID userId = order.getUser().getId(); // only this user
        List<SseEmitter> list = emittersByUser.getOrDefault(userId, Collections.emptyList());
        List<SseEmitter> deadEmitters = new ArrayList<>();

        for (SseEmitter emitter : list) {
            try {
                emitter.send(SseEmitter.event()
                        .name("order-status")
                        .data(Map.of(
                                "id", order.getId(),
                                "status", order.getStatus().name()
                        )));
            } catch (Exception e) {
                deadEmitters.add(emitter);
            }
        }

        list.removeAll(deadEmitters);
    }


}
