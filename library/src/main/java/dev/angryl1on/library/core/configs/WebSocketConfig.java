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
     * Регистрируем эндпоинт STOMP, по которому клиенты будут подключаться.
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Допустим, клиент будет подключаться по /ws.
        // С SockJS добавляется резервный транспорт для клиентов без WebSocket.
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }

    /**
     * Настраиваем брокер сообщений и префиксы:
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Топики, куда сервер будет отправлять сообщения, начинаются с /topic
        config.enableSimpleBroker("/topic");

        // Префикс для отправки сообщений с клиента на сервер
        config.setApplicationDestinationPrefixes("/app");
    }
}

