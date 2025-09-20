package com.mscnptpm.orderservice.controller;

import com.mscnptpm.orderservice.dto.OrderDTO;
import com.mscnptpm.orderservice.dto.OrderLineDTO;
import com.mscnptpm.orderservice.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@SpringBootTest
class OrderE2ETest {

    @Autowired
    private OrderService orderService;  // 🔑 inject vào đây

    @Test
    void testOrderFlow() {
        // 1. Gửi order mới
        OrderDTO request = OrderDTO.builder()
                .userId(Long.valueOf(1))
                .orderLines(List.of(
                        new OrderLineDTO(null, 1L, 2),
                        new OrderLineDTO(null, 2L, 1)
                ))
                .build();

        OrderDTO created = orderService.createOrder(request);
        assertThat(created).isNotNull();
        assertThat(created.getStatus()).isEqualTo("PENDING");

        Long orderId = created.getId();

        // 2. Chờ cho tới khi Payment + Inventory xử lý xong
        await().atMost(10, TimeUnit.SECONDS).untilAsserted(() -> {
            OrderDTO updated = orderService.getOrder(orderId);
            System.out.println("👉 Order status now: " + updated.getStatus());
            assertThat(updated).isNotNull();
            assertThat(List.of("COMPLETED", "CANCELLED"))
                    .contains(updated.getStatus());
        });
    }
}
