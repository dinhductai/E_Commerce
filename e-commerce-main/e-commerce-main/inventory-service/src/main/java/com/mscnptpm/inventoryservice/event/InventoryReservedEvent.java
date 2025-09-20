package com.mscnptpm.inventoryservice.event;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryReservedEvent {
    private Long orderId;
    private Long userId;
}
