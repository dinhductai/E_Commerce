package com.mscnptpm.orderservice.mapper;

import com.mscnptpm.orderservice.dto.OrderLineDTO;
import com.mscnptpm.orderservice.entity.OrderLine;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OrderLineMapper {
    OrderLineMapper INSTANCE = Mappers.getMapper(OrderLineMapper.class);

    OrderLineDTO toDTO(OrderLine entity);

    OrderLine toEntity(OrderLineDTO dto);
}
