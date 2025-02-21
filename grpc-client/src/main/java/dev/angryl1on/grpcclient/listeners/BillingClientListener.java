package dev.angryl1on.grpcclient.listeners;

import dev.angryl1on.grpcclient.services.BillingGrpcService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Микросервис, "слушающий" очередь billing_requests_queue.
 * Получает задание, вызывает gRPC-сервис, публикует результат в billing_responses_queue.
 */
@Component
public class BillingClientListener {

    private final RabbitTemplate rabbitTemplate;
    private final BillingGrpcService billingGrpcService;

    public BillingClientListener(RabbitTemplate rabbitTemplate, BillingGrpcService billingGrpcService) {
        this.rabbitTemplate = rabbitTemplate;
        this.billingGrpcService = billingGrpcService;
    }

    @RabbitListener(queues = "billing_requests_queue")
    public void receiveBillingRequest(Map<String, Object> requestMessage) {
        try {
            // Пример структуры входящего сообщения (JSON -> Map):
            // {
            //   "requestId" : "c08ea92f-47be-45d3-8b6b-368c6bdc32b5",
            //   "operation" : "CALCULATE_OVERDUE_FEE",
            //   "borrowingId" : "xxx-xxx-xxx",
            //   "borrowDate" : "2024-12-01",
            //   "dueDate" : "2025-01-05",
            //   "returnDate" : "2025-01-10"
            // }
            String operation = (String) requestMessage.get("operation");
            if ("CALCULATE_OVERDUE_FEE".equals(operation)) {
                String requestId = (String) requestMessage.get("requestId");
                String borrowingId = (String) requestMessage.get("borrowingId");
                String borrowDate = (String) requestMessage.getOrDefault("borrowDate", "");
                String dueDate = (String) requestMessage.getOrDefault("dueDate", "");
                String returnDate = (String) requestMessage.getOrDefault("returnDate", "");

                // Вызываем удалённый gRPC-метод
                double fee = billingGrpcService.calculateOverdueFee(borrowingId, borrowDate, dueDate, returnDate);

                // Формируем ответ
                Map<String, Object> responseMessage = new HashMap<>();
                responseMessage.put("requestId", requestId);
                responseMessage.put("operation", operation);
                responseMessage.put("borrowingId", borrowingId);
                responseMessage.put("fee", fee);

                // Публикуем результат
                rabbitTemplate.convertAndSend("billing_responses_queue", responseMessage);
            }
        } catch (Exception e) {
            // Логируем или обрабатываем ошибку
            e.printStackTrace();
        }
    }
}
