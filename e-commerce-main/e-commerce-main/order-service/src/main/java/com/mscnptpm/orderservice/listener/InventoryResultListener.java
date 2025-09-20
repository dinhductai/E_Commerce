package com.mscnptpm.orderservice.listener;

import com.mscnptpm.orderservice.config.RabbitMQConfig;
import com.mscnptpm.orderservice.event.InventoryResultEvent;
import com.mscnptpm.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class InventoryResultListener {
    private final OrderRepository orderRepository;

    @RabbitListener(queues = RabbitMQConfig.INVENTORY_RESULT_QUEUE)
    public void handleInventoryResult(InventoryResultEvent event) {
        log.info("📥 Received InventoryResultEvent: {}", event);

        orderRepository.findById(event.getOrderId()).ifPresent(order -> {
            if ("RESERVED".equals(event.getStatus())) {
                order.setStatus("COMPLETED");
            } else {
                order.setStatus("CANCELLED");
            }
            orderRepository.save(order);
            log.info("✅ Order {} updated to {}", order.getId(), order.getStatus());
        });
    }
}