package com.base.game;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class MovementController {

    // Posición inicial del jugador (para simplificar, se usa una variable global)
    private Position position = new Position(0, 0);
    private static final int SPEED = 5; // Incremento en cada tick

    // Mapea los mensajes enviados a /app/move
    @MessageMapping("/move")
    // Envía la posición actualizada a /topic/position
    @SendTo("/topic/position")
    public Position processMovement(MovementState movement) throws Exception {
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
        // Se podría agregar un pequeño retardo simulado si es necesario:
        Thread.sleep(10);
        return position;
    }
}