package com.mscnptpm.orderservice.event;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResultEvent {
    private Long orderId;
    private Long userId;
    private String status;
}
