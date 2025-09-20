package com.mscnptpm.inventoryservice.listener;

import com.mscnptpm.inventoryservice.config.RabbitMQConfig;
import com.mscnptpm.inventoryservice.entity.Stock;
import com.mscnptpm.inventoryservice.event.InventoryRejectedEvent;
import com.mscnptpm.inventoryservice.event.InventoryReservedEvent;
import com.mscnptpm.inventoryservice.event.PaymentResultEvent;
import com.mscnptpm.inventoryservice.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentResultListener {

    private final StockRepository stockRepository;
    private final RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = RabbitMQConfig.PAYMENT_RESULT_QUEUE)
    public void handlePaymentResult(PaymentResultEvent event) {
        log.info("📥 Received PaymentResultEvent: {}", event);

        if ("AUTHORIZED".equals(event.getStatus())) {
            boolean allReserved = true;

            // ✅ Kiểm tra tồn kho cho từng sản phẩm
            for (PaymentResultEvent.OrderLineEvent line : event.getOrderLines()) {
                Stock stock = stockRepository.findByProductId(line.getProductId())
                        .orElseThrow(() -> new RuntimeException("Stock not found for product " + line.getProductId()));

                if (stock.getQuantity() >= line.getQuantity()) {
                    stock.setQuantity(stock.getQuantity() - line.getQuantity());
                    stockRepository.save(stock);
                } else {
                    allReserved = false;
                }
            }

            // ✅ Publish kết quả qua exchange INVENTORY
            if (allReserved) {
                InventoryReservedEvent reserved = InventoryReservedEvent.builder()
                        .orderId(event.getOrderId())
                        .userId(event.getUserId())
                        .build();

                rabbitTemplate.convertAndSend(
                        RabbitMQConfig.INVENTORY_EXCHANGE,
                        RabbitMQConfig.INVENTORY_RESULT_KEY,
                        reserved
                );

                log.info("📤 Published InventoryReservedEvent: {}", reserved);
            } else {
                InventoryRejectedEvent rejected = InventoryRejectedEvent.builder()
                        .orderId(event.getOrderId())
                        .userId(event.getUserId())
                        .reason("Insufficient stock")
                        .build();

                rabbitTemplate.convertAndSend(
                        RabbitMQConfig.INVENTORY_EXCHANGE,
                        RabbitMQConfig.INVENTORY_RESULT_KEY,
                        rejected
                );

                log.info("📤 Published InventoryRejectedEvent: {}", rejected);
            }
        } else {
            log.warn("❌ Payment FAILED for order {} → Skip inventory check", event.getOrderId());
        }
    }
}
