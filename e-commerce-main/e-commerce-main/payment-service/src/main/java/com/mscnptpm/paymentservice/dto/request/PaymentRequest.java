package com.mscnptpm.paymentservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class PaymentRequest {
    @NotNull(message = "OrderId is required")
    private Long orderId;

    @NotNull(message = "UserId is required")
    private Long userId;
}
