package com.mscnptpm.inventoryservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // === Nhận từ Payment ===
    public static final String PAYMENT_EXCHANGE = "payment.exchange";
    public static final String PAYMENT_RESULT_QUEUE = "payment.result.queue";
    public static final String PAYMENT_RESULT_KEY = "payment.result";

    // === Publish từ Inventory ===
    public static final String INVENTORY_EXCHANGE = "inventory.exchange";
    public static final String INVENTORY_RESULT_QUEUE = "inventory.result.queue";
    public static final String INVENTORY_RESULT_KEY = "inventory.result";

    // ✅ Payment Exchange (phải giống PaymentService)
    @Bean
    public DirectExchange paymentExchange() {
        return new DirectExchange(PAYMENT_EXCHANGE);
    }

    // ✅ Queue để nhận PaymentResult
    @Bean
    public Queue paymentResultQueue() {
        return new Queue(PAYMENT_RESULT_QUEUE, true);
    }

    // ✅ Binding queue vào payment.exchange
    @Bean
    public Binding paymentResultBinding() {
        return BindingBuilder.bind(paymentResultQueue())
                .to(paymentExchange())
                .with(PAYMENT_RESULT_KEY);
    }

    // === Inventory Exchange riêng ===
    @Bean
    public DirectExchange inventoryExchange() {
        return new DirectExchange(INVENTORY_EXCHANGE);
    }

    @Bean
    public Queue inventoryResultQueue() {
        return new Queue(INVENTORY_RESULT_QUEUE, true);
    }

    @Bean
    public Binding inventoryResultBinding() {
        return BindingBuilder.bind(inventoryResultQueue())
                .to(inventoryExchange())
                .with(INVENTORY_RESULT_KEY);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }
}
