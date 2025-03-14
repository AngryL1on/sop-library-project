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

    @RabbitListener(queues = RabbitMQConfig.NEW_BOOKS_QUEUE)
    public void handleNewBookMessage(String message) {
        messagingTemplate.convertAndSend("/topic/books", message);
    }

    @RabbitListener(queues = RabbitMQConfig.NEW_LIBRARIES_QUEUE)
    public void handleNewLibraryMessage(String message) {
        messagingTemplate.convertAndSend("/topic/libraries", message);
    }
}
