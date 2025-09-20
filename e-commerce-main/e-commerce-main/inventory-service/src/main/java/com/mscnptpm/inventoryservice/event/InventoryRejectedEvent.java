package com.mscnptpm.inventoryservice.event;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryRejectedEvent {
    private Long orderId;
    private Long userId;
    private String reason;
}