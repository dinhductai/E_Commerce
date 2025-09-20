package com.mscnptpm.orderservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class OrderLineDTO {
    private Long id;

    @NotNull(message = "productId không được để trống")
    private Long productId;

    @Min(value = 1, message = "Số lượng phải >= 1")
    private int quantity;
}
