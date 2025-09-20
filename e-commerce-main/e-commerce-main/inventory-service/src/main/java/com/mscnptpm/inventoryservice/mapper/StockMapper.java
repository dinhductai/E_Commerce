package com.mscnptpm.inventoryservice.mapper;

import com.mscnptpm.inventoryservice.dto.request.StockRequest;
import com.mscnptpm.inventoryservice.dto.response.StockResponse;
import com.mscnptpm.inventoryservice.entity.Stock;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StockMapper {
    Stock toEntity(StockRequest req);
    StockResponse toDTO(Stock entity);
    List<StockResponse> toDTOs(List<Stock> entities);
}

