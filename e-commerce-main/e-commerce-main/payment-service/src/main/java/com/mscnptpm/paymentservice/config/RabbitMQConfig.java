package com.mscnptpm.paymentservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String ORDER_EXCHANGE = "order.exchange";
    public static final String ORDER_CREATED_QUEUE = "order.created.queue";
    public static final String ORDER_CREATED_KEY = "order.created";

    public static final String PAYMENT_EXCHANGE = "payment.exchange";
    public static final String PAYMENT_RESULT_QUEUE = "payment.result.queue";
    public static final String PAYMENT_RESULT_KEY = "payment.result";

    @Bean
    public DirectExchange orderExchange() { return new DirectExchange(ORDER_EXCHANGE); }

    @Bean
    public Queue orderCreatedQueue() { return new Queue(ORDER_CREATED_QUEUE, true); }

    @Bean
    public Binding orderCreatedBinding() {
        return BindingBuilder.bind(orderCreatedQueue()).to(orderExchange()).with(ORDER_CREATED_KEY);
    }

    @Bean
    public DirectExchange paymentExchange() { return new DirectExchange(PAYMENT_EXCHANGE); }

    @Bean
    public Queue paymentResultQueue() { return new Queue(PAYMENT_RESULT_QUEUE, true); }

    @Bean
    public Binding paymentResultBinding() {
        return BindingBuilder.bind(paymentResultQueue()).to(paymentExchange()).with(PAYMENT_RESULT_KEY);
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

