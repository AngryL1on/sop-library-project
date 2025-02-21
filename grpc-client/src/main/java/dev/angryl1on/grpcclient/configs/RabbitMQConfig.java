package dev.angryl1on.grpcclient.configs;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String BILLING_REQUESTS_QUEUE = "billing_requests_queue";
    public static final String BILLING_RESPONSES_QUEUE = "billing_responses_queue";

    @Bean
    public Queue billingRequestsQueue() {
        return new Queue(BILLING_REQUESTS_QUEUE, true);
    }

    @Bean
    public Queue billingResponsesQueue() {
        return new Queue(BILLING_RESPONSES_QUEUE, true);
    }

    // При необходимости можно объявить Exchange и Binding
}
