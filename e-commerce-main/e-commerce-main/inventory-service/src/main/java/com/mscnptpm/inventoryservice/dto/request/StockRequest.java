package com.mscnptpm.inventoryservice.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockRequest {
    @NotNull(message = "ProductId is required")
    private Long productId;

    @Min(value = 0, message = "Quantity must be >= 0")
    private int quantity;
}