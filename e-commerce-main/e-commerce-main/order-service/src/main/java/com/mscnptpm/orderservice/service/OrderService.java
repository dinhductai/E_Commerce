package com.mscnptpm.orderservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mscnptpm.orderservice.dto.OrderDTO;
import com.mscnptpm.orderservice.entity.Order;
import com.mscnptpm.orderservice.entity.OutboxEvent;
import com.mscnptpm.orderservice.event.OrderCreatedEvent;
import com.mscnptpm.orderservice.mapper.OrderMapper;
import com.mscnptpm.orderservice.repository.OrderRepository;
import com.mscnptpm.orderservice.repository.OutboxEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OutboxEventRepository outboxEventRepository;
    private final OrderMapper orderMapper;
    private final ObjectMapper objectMapper;

    // Create Order
    public OrderDTO createOrder(OrderDTO dto) {
        Order order = orderMapper.toEntity(dto);
        order.setStatus("PENDING");
        order.getOrderLines().forEach(l -> l.setOrder(order));

        Order saved = orderRepository.save(order);

        try {
            OrderCreatedEvent event = OrderCreatedEvent.builder()
                    .orderId(saved.getId())
                    .userId(saved.getUserId())
                    .status(saved.getStatus())
                    .orderLines(saved.getOrderLines().stream()
                            .map(l -> new OrderCreatedEvent.OrderLineEvent(l.getProductId(), l.getQuantity()))
                            .toList())
                    .build();

            String payload = objectMapper.writeValueAsString(event);

            outboxEventRepository.save(OutboxEvent.builder()
                    .aggregateType("ORDER")
                    .aggregateId(String.valueOf(saved.getId()))
                    .type("OrderCreated")
                    .payload(payload)
                    .createdAt(Instant.now())
                    .published(false)
                    .build());
        } catch (Exception e) {
            throw new RuntimeException("Failed to create outbox event", e);
        }

        return orderMapper.toDTO(saved);
    }

    // Get all orders
    public List<OrderDTO> getAllOrders() {
        return orderMapper.toDTOs(orderRepository.findAll());
    }

    // Get by ID
    public OrderDTO getOrder(Long id) {
        return orderRepository.findById(id)
                .map(orderMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    // Delete
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}