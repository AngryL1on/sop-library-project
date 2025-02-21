package dev.angryl1on.library.core.listeners;

import dev.angryl1on.library.core.models.entity.Borrowing;
import dev.angryl1on.library.core.repositories.BorrowingRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

import static dev.angryl1on.library.core.configs.RabbitMQConfig.BILLING_RESPONSES_QUEUE;

@Component
public class BillingResponseHandler {

    private final BorrowingRepository borrowingRepository;

    public BillingResponseHandler(BorrowingRepository borrowingRepository) {
        this.borrowingRepository = borrowingRepository;
    }

    @RabbitListener(queues = BILLING_RESPONSES_QUEUE)
    public void handleBillingResponse(Map<String, Object> responseMessage) {
        // Пример структуры ответа (JSON -> Map):
        // {
        //   "requestId"  : "c08ea92f-47be-45d3-8b6b-368c6bdc32b5",
        //   "operation"  : "CALCULATE_OVERDUE_FEE",
        //   "borrowingId": "xxx-xxx-xxx",
        //   "fee"        : 20.0
        // }
        String operation = (String) responseMessage.get("operation");
        if ("CALCULATE_OVERDUE_FEE".equals(operation)) {
            String borrowingIdStr = (String) responseMessage.get("borrowingId");
            Double fee = (Double) responseMessage.get("fee");

            Borrowing borrowing = borrowingRepository.findById(UUID.fromString(borrowingIdStr))
                    .orElse(null);

            if (borrowing != null) {
                // Допустим, в Borrowing есть поле fee (штраф):
                borrowing.setFee(fee);
                borrowingRepository.save(borrowing);

                System.out.println("Обновили Borrowing " + borrowingIdStr + " -> fee=" + fee);
            } else {
                System.err.println("Borrowing " + borrowingIdStr + " не найден. Невозможно сохранить штраф.");
            }
        }
    }
}
