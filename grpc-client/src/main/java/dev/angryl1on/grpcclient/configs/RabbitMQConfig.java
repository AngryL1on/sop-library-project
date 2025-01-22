package dev.angryl1on.grpcclient.configs;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String RETURN_BOOK_PENALTY_QUEUE = "return_book_penalty_queue";
    public static final String FEE_CALCULATION_QUEUE = "fee_calculation_queue";

    public static final String PENALTY_EXCHANGE = "penalty_exchange";

    @Bean
    public Queue returnBookPenaltyQueue() {
        return new Queue(RETURN_BOOK_PENALTY_QUEUE, true);
    }

    @Bean
    public Queue feeCalculationQueue() {
        return new Queue(FEE_CALCULATION_QUEUE, true);
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(PENALTY_EXCHANGE);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         Jackson2JsonMessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }
}
