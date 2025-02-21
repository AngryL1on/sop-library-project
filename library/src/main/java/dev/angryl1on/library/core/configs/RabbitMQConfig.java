package dev.angryl1on.library.core.configs;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String AUDIT_LOGS_QUEUE = "audit_logs_queue";
    public static final String NEW_BOOKS_QUEUE = "new_books_queue";
    public static final String NEW_LIBRARIES_QUEUE = "new_libraries_queue";
    public static final String BILLING_REQUESTS_QUEUE = "billing_requests_queue";
    public static final String BILLING_RESPONSES_QUEUE = "billing_responses_queue";

    @Bean
    public Queue auditLogsQueue() {
        return new Queue(AUDIT_LOGS_QUEUE, true);
    }

    @Bean
    public Queue newBooksQueue() {
        return new Queue(NEW_BOOKS_QUEUE, true);
    }

    @Bean
    public Queue newLibrariesQueue() {
        return new Queue(NEW_LIBRARIES_QUEUE, true);
    }

    @Bean
    public Queue billingRequestsQueue() {
        return new Queue(BILLING_REQUESTS_QUEUE, true);
    }

    @Bean
    public Queue billingResponsesQueue() {
        return new Queue(BILLING_RESPONSES_QUEUE, true);
    }
}
