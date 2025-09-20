package com.mscnptpm.orderservice.mapper;

import com.mscnptpm.orderservice.dto.OrderDTO;
import com.mscnptpm.orderservice.entity.Order;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    Order toEntity(OrderDTO dto);
    OrderDTO toDTO(Order entity);

    List<OrderDTO> toDTOs(List<Order> entities);
}

