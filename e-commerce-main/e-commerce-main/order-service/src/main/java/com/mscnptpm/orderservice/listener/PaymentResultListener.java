package com.mscnptpm.orderservice.listener;

import com.mscnptpm.orderservice.config.RabbitMQConfig;
import com.mscnptpm.orderservice.event.PaymentResultEvent;
import com.mscnptpm.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentResultListener {
    private final OrderRepository orderRepository;

    @RabbitListener(queues = RabbitMQConfig.PAYMENT_RESULT_QUEUE)
    public void handlePaymentResult(PaymentResultEvent event) {
        log.info("📥 Received PaymentResultEvent: {}", event);

        orderRepository.findById(event.getOrderId()).ifPresent(order -> {
            if ("AUTHORIZED".equals(event.getStatus())) {
                order.setStatus("PAYMENT_AUTHORIZED");
            } else {
                order.setStatus("CANCELLED");
            }
            orderRepository.save(order);
            log.info("✅ Order {} updated to {}", order.getId(), order.getStatus());
        });
    }
}
