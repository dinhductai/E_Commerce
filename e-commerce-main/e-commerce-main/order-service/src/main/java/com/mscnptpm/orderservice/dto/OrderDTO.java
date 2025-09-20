package com.mscnptpm.orderservice.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class OrderDTO {
    private Long id;

    @NotNull(message = "UserId is required")
    private Long userId;

    private String status; // service sẽ set PENDING khi tạo mới

    @NotEmpty(message = "Order phải có ít nhất 1 sản phẩm")
    @Valid
    private List<OrderLineDTO> orderLines;
}
