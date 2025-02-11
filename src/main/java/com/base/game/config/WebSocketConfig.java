package com.base.game.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // El cliente se conectará a /game y se usará SockJS como fallback
        registry.addEndpoint("/game").setAllowedOrigins("*");
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // Los mensajes enviados a destinos que comiencen con /topic serán enviados a los clientes
        registry.enableSimpleBroker("/topic");
        // Los mensajes enviados por el cliente deberán tener el prefijo /app
        registry.setApplicationDestinationPrefixes("/app");
    }
}

