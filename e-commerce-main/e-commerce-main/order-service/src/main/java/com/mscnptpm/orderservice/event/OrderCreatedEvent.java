package com.mscnptpm.orderservice.event;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderCreatedEvent {
    private Long orderId;
    private Long userId;
    private String status;
    private List<OrderLineEvent> orderLines;

    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class OrderLineEvent {
        private Long productId;
        private int quantity;
    }
}
