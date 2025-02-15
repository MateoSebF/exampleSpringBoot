package com.base.game;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.WebSocketSession;

@Controller
public class MovementController {

    private final ConcurrentHashMap<String, Position> positions = new ConcurrentHashMap<>();

    private static final int SPEED = 5; // Incremento en cada tick

    // Mapea los mensajes enviados a /app/move
    @MessageMapping("/move")
    public void processMovement(MovementState movement, WebSocketSession session) throws Exception {
        Position position;
        if (positions.get(session.getId())== null){
            position = new Position(0,0);
            positions.put(session.getId(), position);
        }
        else{
            position = positions.get(session.getId());
        }
        // Actualiza la posición en función de las teclas presionadas
        if (movement.isUp()) {
            position.setY(position.getY() - SPEED);
        }
        if (movement.isDown()) {
            position.setY(position.getY() + SPEED);
        }
        if (movement.isLeft()) {
            position.setX(position.getX() - SPEED);
        }
        if (movement.isRight()) {
            position.setX(position.getX() + SPEED);
        }
        System.out.println(movement);
    }

    @Scheduled(fixedRate = 50) // Ejecuta cada 1 segundos
    @SendTo("/topic/positions")  // Envía las posiciones a todos los suscriptores
    public ConcurrentHashMap<String, Position> enviarPosiciones() {
        // Enviar todas las posiciones de los cuadrados
        return positions;
    }
}