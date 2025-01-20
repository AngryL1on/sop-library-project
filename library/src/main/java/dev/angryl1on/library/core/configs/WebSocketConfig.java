package dev.angryl1on.library.core.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * We are registering the STOMP endpoint that clients will use to connect.
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }

    /**
     * Configuring the message broker and prefixes
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // The topics where the server will send messages start with /topic
        config.enableSimpleBroker("/topic");

        // The prefix for sending messages from the client to the server
        config.setApplicationDestinationPrefixes("/app");
    }
}

