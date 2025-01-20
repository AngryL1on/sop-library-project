package dev.angryl1on.library.core.services;

import dev.angryl1on.library.core.configs.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public NotificationService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    /**
     * Слушаем очередь для новых книг. Как только RabbitMQ
     * присылает сообщение, отправляем его по WebSocket всем клиентам.
     */
    @RabbitListener(queues = RabbitMQConfig.NEW_BOOKS_QUEUE)
    public void handleNewBookMessage(String message) {
        // Например, отправляем на топик /topic/books
        messagingTemplate.convertAndSend("/topic/books", message);
    }

    /**
     * Слушаем очередь для новых библиотек. Аналогично отправляем
     * сообщение по WebSocket.
     */
    @RabbitListener(queues = RabbitMQConfig.NEW_LIBRARIES_QUEUE)
    public void handleNewLibraryMessage(String message) {
        // Например, отправляем на топик /topic/libraries
        messagingTemplate.convertAndSend("/topic/libraries", message);
    }
}

