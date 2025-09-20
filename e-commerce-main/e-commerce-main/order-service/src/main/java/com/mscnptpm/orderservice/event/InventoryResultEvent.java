package com.mscnptpm.orderservice.event;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class InventoryResultEvent {
    private Long orderId;
    private Long userId;
    private String status;
    private String reason;
}
