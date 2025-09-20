package com.mscnptpm.paymentservice.listener;

import com.mscnptpm.paymentservice.config.RabbitMQConfig;
import com.mscnptpm.paymentservice.entity.Payment;
import com.mscnptpm.paymentservice.event.OrderCreatedEvent;
import com.mscnptpm.paymentservice.event.PaymentResultEvent;
import com.mscnptpm.paymentservice.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Random;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderCreatedListener {

    private final PaymentRepository paymentRepository;
    private final RabbitTemplate rabbitTemplate;
    private final Random random = new Random();

    @RabbitListener(queues = RabbitMQConfig.ORDER_CREATED_QUEUE)
    public void handleOrderCreated(OrderCreatedEvent event) {
        log.info("📥 Received OrderCreatedEvent: {}", event);

        boolean success = true;

        Payment payment = Payment.builder()
                .orderId(event.getOrderId())
                .userId(event.getUserId())
                .status(success ? "AUTHORIZED" : "FAILED")
                .createdAt(Instant.now())
                .build();
        paymentRepository.save(payment);

        // build PaymentResultEvent có orderLines
        PaymentResultEvent result = PaymentResultEvent.builder()
                .orderId(event.getOrderId())
                .userId(event.getUserId())
                .status(payment.getStatus())
                .orderLines(
                        event.getOrderLines().stream()
                                .map(l -> PaymentResultEvent.OrderLineEvent.builder()
                                        .productId(l.getProductId())
                                        .quantity(l.getQuantity())
                                        .build())
                                .toList()
                )
                .build();

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.PAYMENT_EXCHANGE,
                RabbitMQConfig.PAYMENT_RESULT_KEY,
                result
        );

        log.info("📤 Published PaymentResultEvent: {}", result);
    }
}
