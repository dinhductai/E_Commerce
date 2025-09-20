package com.mscnptpm.paymentservice.dto.response;

import lombok.*;

import java.time.Instant;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class PaymentResponse {
    private Long id;
    private Long orderId;
    private Long userId;
    private String status;
    private Instant createdAt;
}
