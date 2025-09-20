package com.mscnptpm.paymentservice.event;

import lombok.*;

import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class OrderCreatedEvent {
    private Long orderId;
    private Long userId;
    private String status;
    private List<OrderLineEvent> orderLines;

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class OrderLineEvent {
        private Long productId;
        private int quantity;
    }
}
