package com.mscnptpm.orderservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mscnptpm.orderservice.config.RabbitMQConfig;
import com.mscnptpm.orderservice.entity.OutboxEvent;
import com.mscnptpm.orderservice.event.OrderCreatedEvent;
import com.mscnptpm.orderservice.repository.OutboxEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OutboxPublisher {

    private final OutboxEventRepository outboxRepo;
    private final RabbitTemplate rabbitTemplate;

    @Scheduled(fixedRate = 5000) // mỗi 5 giây
    public void publishEvents() {
        List<OutboxEvent> events = outboxRepo.findByPublishedFalse();

        for (OutboxEvent event : events) {
            try {
                // ✅ parse payload thành object
                OrderCreatedEvent orderEvent = new ObjectMapper()
                        .readValue(event.getPayload(), OrderCreatedEvent.class);

                // ✅ gửi object thay vì string
                rabbitTemplate.convertAndSend(
                        RabbitMQConfig.ORDER_EXCHANGE,
                        RabbitMQConfig.ORDER_CREATED_KEY,
                        orderEvent
                );

                log.info("✅ Published event: {}", orderEvent);

                event.setPublished(true);
                outboxRepo.save(event);

            } catch (Exception e) {
                log.error("❌ Failed to publish event {}", event.getId(), e);
            }
        }
    }
}
